package com.maxtropy.arch.openplatform.sdk.core.api;

import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;

import java.net.URI;

/**
 * @author luwang
 * @description
 * @date 2023/04/06
 */
public abstract class ApiMapper {

    protected URI endpoint;
    protected ServiceClient serviceClient;
    protected CredentialsProvider credentialsProvider;


    public ApiMapper(URI endpoint, ServiceClient serviceClient,
                     CredentialsProvider credentialsProvider) {
        this.endpoint = endpoint;
        this.serviceClient = serviceClient;
        this.credentialsProvider = credentialsProvider;
    }



}
