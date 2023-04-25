package com.maxtropy.arch.openplatform.sdk.core.auth;

/**
 * @author luwang
 * @description HTTP 秘钥
 * @date 2023/04/07
 */
public interface Credentials {

    public String getAppKey();

    public String getAppSecret();
}