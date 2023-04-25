package com.maxtropy.arch.openplatform.sdk.core.common;

import com.maxtropy.arch.openplatform.sdk.core.config.ClientConfiguration;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpDeleteRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpGetRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpPostRequest;
import com.maxtropy.arch.openplatform.sdk.core.http.HttpPutRequest;

/**
 * @author luwang
 * @description Http协议执行
 * @date 2023/04/07
 */
public abstract class ServiceClient {

    protected ClientConfiguration config;

    protected ServiceClient(ClientConfiguration config) {
        this.config = config;
    }

    public ClientConfiguration getClientConfiguration() {
        return this.config;
    }

    public abstract String doGet(HttpGetRequest httpGetRequest);

    public abstract String doPost(HttpPostRequest httpPostRequest);


    public abstract String doDelete(HttpDeleteRequest httpDeleteRequest);

    public abstract String doPut(HttpPutRequest httpPutRequest);

}
