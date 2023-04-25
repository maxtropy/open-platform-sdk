package com.maxtropy.arch.openplatform.sdk.core.common;


/**
 * @author luwang
 * @description HTTP 类型
 * @date 2023/04/07
 */
public enum HttpMethodEnum {


    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE");


    private final String method;

    HttpMethodEnum(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}