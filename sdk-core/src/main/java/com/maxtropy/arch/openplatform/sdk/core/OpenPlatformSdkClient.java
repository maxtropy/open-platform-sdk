package com.maxtropy.arch.openplatform.sdk.core;

import com.maxtropy.arch.openplatform.sdk.core.api.*;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.auth.DefaultCredentialProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.DefaultServiceClient;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;
import com.maxtropy.arch.openplatform.sdk.core.config.ClientBuilderConfiguration;
import com.maxtropy.arch.openplatform.sdk.core.config.ClientConfiguration;
import com.maxtropy.arch.openplatform.sdk.core.model.ItemResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.ListResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.ResponseModel;
import com.maxtropy.arch.openplatform.sdk.core.util.SdkUtils;

import java.lang.reflect.Constructor;
import java.net.URI;

/**
 * @author luwang
 * @description
 * @date 2023/04/06
 */
public class OpenPlatformSdkClient {

    private CredentialsProvider credsProvider;
    private URI endpoint;

    private ServiceClient serviceClient;

    private OpenPlatformSdkClient(String endpoint, CredentialsProvider credsProvider,
                                  ClientConfiguration config) {
        this.credsProvider = credsProvider;
        this.serviceClient = new DefaultServiceClient(config);
        setEndpoint(endpoint);
    }

    private OpenPlatformSdkClient(String endpoint, CredentialsProvider credsProvider,
                                  ServiceClient serviceClient) {
        this.credsProvider = credsProvider;
        this.serviceClient = serviceClient;
        setEndpoint(endpoint);
    }

    private synchronized void setEndpoint(String endpoint) {
        URI uri = SdkUtils.toEndpointURI(endpoint,
                this.serviceClient.getClientConfiguration().getProtocol().toString());
        this.endpoint = uri;
    }

    public <T extends ApiMapper> T getApiMapper(Class<T> clazz) {
        try {
            Constructor<T> declaredConstructor = clazz.getDeclaredConstructor(URI.class,
                    ServiceClient.class, CredentialsProvider.class);
            return declaredConstructor.newInstance(endpoint, serviceClient, credsProvider);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <P extends ResponseModel> Api<P> rawApi(String url, HttpMethodEnum httpMethodEnum, Class<P> responseModelClazz) {
        return Api.Builder.build(
                endpoint,
                credsProvider, serviceClient,
                url, httpMethodEnum, responseModelClazz);
    }

    public <P extends ListResponse, I extends ItemResponse> Api<ListResponse<I>> rawApi(String url, HttpMethodEnum httpMethodEnum, Class<P> responseModelClazz, Class<I> itemClazz) {
        return Api.Builder.build(
                endpoint,
                credsProvider, serviceClient,
                url, httpMethodEnum, responseModelClazz, itemClazz);
    }

    public static class Builder {

        public static OpenPlatformSdkClient build(String endpoint, String appKey, String appSecret) {
            return new OpenPlatformSdkClient(endpoint, new DefaultCredentialProvider(appKey, appSecret), new ClientConfiguration());
        }

        public static OpenPlatformSdkClient build(String endpoint, String appKey, String appSecret, ClientBuilderConfiguration config) {
            return new OpenPlatformSdkClient(endpoint, new DefaultCredentialProvider(appKey, appSecret), config);
        }

        public static OpenPlatformSdkClient build(String endpoint, CredentialsProvider credentialsProvider) {
            return new OpenPlatformSdkClient(endpoint, credentialsProvider, new ClientConfiguration());
        }

        public static OpenPlatformSdkClient build(String endpoint, CredentialsProvider credentialsProvider, ClientBuilderConfiguration config) {
            return new OpenPlatformSdkClient(endpoint, credentialsProvider, config);
        }

        public static OpenPlatformSdkClient build(String endpoint, String appKey, String appSecret, ServiceClient serviceClient) {
            return new OpenPlatformSdkClient(endpoint, new DefaultCredentialProvider(appKey, appSecret), serviceClient);
        }

    }
}
