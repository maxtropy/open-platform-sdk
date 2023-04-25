package com.maxtropy.arch.openplatform.sdk.core.api;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;
import com.maxtropy.arch.openplatform.sdk.core.exception.ApiFailException;
import com.maxtropy.arch.openplatform.sdk.core.exception.HttpStatusNoScOKException;
import com.maxtropy.arch.openplatform.sdk.core.exception.SdkException;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpDeleteRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpGetRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpPostRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpPutRequest;
import com.maxtropy.arch.openplatform.sdk.core.model.*;
import com.maxtropy.arch.openplatform.sdk.core.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @author luwang
 * @description
 * @date 2023/04/08
 */
public class Api<P extends ResponseModel> {

    private static final Logger logger = LoggerFactory.getLogger(Api.class);

    private URI endpoint;
    private CredentialsProvider credentialsProvider;
    private ServiceClient serviceClient;
    private String url;
    private String tenantMcid;
    private HttpMethodEnum httpMethod;
    private String[] pathVariables;
    private Class<P> responseModelJ;
    private RequestBody requestBody;
    private List<QueryParam> queryParams;

    private Class itemClazz;

    private Api(URI endpoint, CredentialsProvider credentialsProvider,
                ServiceClient serviceClient, String url, HttpMethodEnum httpMethod,
                Class<P> responseModel) {
        this.endpoint = endpoint;
        this.credentialsProvider = credentialsProvider;
        this.serviceClient = serviceClient;
        this.url = url;
        this.httpMethod = httpMethod;
        this.responseModelJ = responseModel;
    }

    private <I extends ItemResponse> Api(URI endpoint, CredentialsProvider credentialsProvider,
                                         ServiceClient serviceClient, String url, HttpMethodEnum httpMethod,
                                         Class<P> responseModel, Class<I> itemClazz) {
        this.endpoint = endpoint;
        this.credentialsProvider = credentialsProvider;
        this.serviceClient = serviceClient;
        this.url = url;
        this.httpMethod = httpMethod;
        this.responseModelJ = responseModel;
        this.itemClazz = itemClazz;
    }

    public Api<P> setTenantMcid(String tenantMcid) {
        this.tenantMcid = tenantMcid;
        return this;
    }

    public Api<P> setPathVariables(String... pathVariables) {
        this.pathVariables = pathVariables;
        return this;
    }

    public Api<P> addQueryParam(String name, String value) {
        if (queryParams == null) {
            queryParams = new ArrayList<>();
        }
        queryParams.add(new QueryParam(name, value));
        return this;
    }

    public <R extends RequestBody> Api<P> setRequestBody(R requestBody) {
        this.requestBody = requestBody;
        return this;
    }

    public P call() throws ApiFailException {
        return (P) this.rawCall().get(itemClazz);
    }

    public RawResponse<P> rawCall() {
        RawResponse<P> responseModel = new RawResponse<>(responseModelJ);
        String url = this.buildRequestUrl();
        String jwtToken = this.generateJwt();
        logger.info("请求接口:{}", url);
        logger.info("jwt Token:{}", jwtToken);
        String result = "";
        try {
            switch (httpMethod) {
                case GET:
                    HttpGetRequest httpGetRequest = new HttpGetRequest(url, requestBody, this.queryParams);
                    httpGetRequest.addHeader("Authorization", "Bearer " + jwtToken);
                    result = serviceClient.doGet(httpGetRequest);
                    break;
                case POST:
                    HttpPostRequest httpPostRequest = new HttpPostRequest(url, requestBody, this.queryParams);
                    httpPostRequest.addHeader("Authorization", " Bearer " + jwtToken);
                    result = serviceClient.doPost(httpPostRequest);
                    break;
                case DELETE:
                    HttpDeleteRequest httpDeleteRequest = new HttpDeleteRequest(url, requestBody, this.queryParams);
                    httpDeleteRequest.addHeader("Authorization", " Bearer " + jwtToken);
                    result = serviceClient.doDelete(httpDeleteRequest);
                    break;
                case PUT:
                    HttpPutRequest httpPutRequest = new HttpPutRequest(url, requestBody, this.queryParams);
                    httpPutRequest.addHeader("Authorization", " Bearer " + jwtToken);
                    result = serviceClient.doPut(httpPutRequest);
                    break;
            }
            logger.info("响应内容:{}", result);
        } catch (HttpStatusNoScOKException statusNoScOKException) {
            boolean failJson = false;
            try {
                FailResponse apiFailResponseModel = JsonUtil.jsonStr2Bean(statusNoScOKException.getResponse(), FailResponse.class);
                responseModel.setCode(apiFailResponseModel.getErrorCode() + "");
                responseModel.setErrorMsg(apiFailResponseModel.getErrorMessage());
                failJson = true;
            } catch (IOException e) {
                logger.warn("错误响应不是Json，响应内容:" + statusNoScOKException.getResponse());
            }
            if (!failJson) {
                responseModel.setCode(statusNoScOKException.getHttpStatus() + "");
                responseModel.setErrorMsg(statusNoScOKException.getResponse());
            }
            responseModel.setHttpStatus(statusNoScOKException.getHttpStatus() + "");
            return responseModel;
        }

        responseModel.setCode("1");
        responseModel.setHttpStatus("200");
        responseModel.setRawData(result);
        return responseModel;
    }

    private String buildRequestUrl() {
        String[] urlPaths = url.split("/");
        StringBuilder newUrl = new StringBuilder();
        int pathIndex = 0;
        boolean notMatch = false;
        for (int index = 0; index < urlPaths.length; index++) {
            String path = urlPaths[index];
            if (path.startsWith("{") && path.endsWith("}")) {
                if (pathVariables != null && pathIndex < pathVariables.length) {
                    path = pathVariables[pathIndex];
                } else {
                    notMatch = true;
                }
                pathIndex++;
            }
            newUrl.append(path);
            if (index < urlPaths.length - 1) {
                newUrl.append("/");
            }
        }

        if (notMatch) {
            throw new SdkException("PathVariables not match:" + url);
        }

        return endpoint.toString() + newUrl;
    }

    private String generateJwt() {
        Instant nbf = Instant.now().minus(5, ChronoUnit.MINUTES);
        Instant exp = Instant.now().plus(5, ChronoUnit.MINUTES);
        Algorithm algorithm = Algorithm.HMAC256(credentialsProvider.getCredentials().getAppSecret());
        return JWT.create()
                .withIssuer(credentialsProvider.getCredentials().getAppKey())
                .withNotBefore(Date.from(nbf))
                .withExpiresAt(Date.from(exp))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public static class Builder {

        public static <P extends ResponseModel> Api<P> build(URI endpoint,
                                                             CredentialsProvider credentialsProvider,
                                                             ServiceClient serviceClient,
                                                             String url, HttpMethodEnum httpMethod
                , Class<P> responseModelClazz
        ) {
            return new Api(endpoint, credentialsProvider, serviceClient, url, httpMethod, responseModelClazz);
        }

        public static <P extends ListResponse, T extends ItemResponse> Api<ListResponse<T>> build(URI endpoint,
                                                                                                  CredentialsProvider credentialsProvider,
                                                                                                  ServiceClient serviceClient,
                                                                                                  String url, HttpMethodEnum httpMethod
                , Class<P> responseModelClazz, Class<T> itemClazz
        ) {
            return new Api(endpoint, credentialsProvider, serviceClient, url, httpMethod,
                    responseModelClazz, itemClazz);
        }
    }
}
