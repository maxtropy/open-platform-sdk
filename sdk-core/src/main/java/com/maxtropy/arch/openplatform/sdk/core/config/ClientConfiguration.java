package com.maxtropy.arch.openplatform.sdk.core.config;

import com.maxtropy.arch.openplatform.sdk.core.common.Protocol;

/**
 * @author luwang
 * @description HTTP 连接配置
 * @date 2023/04/07
 */
public class ClientConfiguration {

    public static final int DEFAULT_REQUEST_TIMEOUT = 5 * 60 * 1000;

    protected int requestTimeout = DEFAULT_REQUEST_TIMEOUT;

    protected Protocol protocol = Protocol.HTTPS;

    public ClientConfiguration() {
        super();

    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    /**
     * Sets the timeout value in millisecond. By default it's 5 min.
     *
     * @param requestTimeout The timeout value in millisecond.
     */
    public void setRequestTimeout(int requestTimeout) {
        this.requestTimeout = requestTimeout;
    }

    /**
     * Gets the timeout value in millisecond.
     *
     * @return the tiemout value.
     */
    public int getRequestTimeout() {
        return requestTimeout;
    }

}
