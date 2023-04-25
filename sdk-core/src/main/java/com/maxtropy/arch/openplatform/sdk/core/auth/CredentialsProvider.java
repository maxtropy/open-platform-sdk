package com.maxtropy.arch.openplatform.sdk.core.auth;

/**
 * @author luwang
 * @description 秘钥封装
 * @date 2023/04/06
 */
public interface CredentialsProvider {
    /**
     * 设置秘钥
     * @param creds
     */
    public void setCredentials(Credentials creds);

    /**
     * 获取秘钥
     * @return
     */
    public Credentials getCredentials();
}