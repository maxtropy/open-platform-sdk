package com.maxtropy.arch.openplatform.sdk.core.common;

import com.maxtropy.arch.openplatform.sdk.core.api.QueryParam;
import com.maxtropy.arch.openplatform.sdk.core.config.ClientConfiguration;
import com.maxtropy.arch.openplatform.sdk.core.http.*;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/07
 */
public class DefaultServiceClient extends ServiceClient {


    public DefaultServiceClient(ClientConfiguration config) {
        super(config);
    }


    private List<NameValuePair> getNameValueParis(HttpRequest httpRequest) {
        List<NameValuePair> pairs = new ArrayList<>();
        if (httpRequest.getQueryParams() != null && httpRequest.getQueryParams().size() > 0) {
            for (QueryParam queryParam: httpRequest.getQueryParams()) {
                pairs.add(new BasicNameValuePair(queryParam.getName(), queryParam.getValue()));
            }
        }
        return pairs;
    }
    @Override
    public String doGet(HttpGetRequest httpGetRequest) {
        return  HttpClientUtil.doGetByEntity(httpGetRequest.getUrl(), getNameValueParis(httpGetRequest), httpGetRequest.getHeaderMap(),
                config.getRequestTimeout());
    }

    @Override
    public String doPost(HttpPostRequest httpPostRequest) {
        return HttpClientUtil.doPostByJson(httpPostRequest.getUrl(), httpPostRequest.getHeaderMap(),
                getNameValueParis(httpPostRequest), httpPostRequest.getRequestBody(), config.getRequestTimeout());

    }

    @Override
    public String doDelete(HttpDeleteRequest httpDeleteRequest) {
        return HttpClientUtil.doDeleteByJson(httpDeleteRequest.getUrl(), httpDeleteRequest.getHeaderMap(),
                getNameValueParis(httpDeleteRequest), httpDeleteRequest.getRequestBody(), config.getRequestTimeout());
    }

    @Override
    public String doPut(HttpPutRequest httpPutRequest) {

        return HttpClientUtil.doPutByJson(httpPutRequest.getUrl(), httpPutRequest.getHeaderMap(), getNameValueParis(httpPutRequest),
                httpPutRequest.getRequestBody(), config.getRequestTimeout());
    }

}
