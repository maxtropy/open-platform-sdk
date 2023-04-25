package com.maxtropy.arch.openplatform.sdk.core.auth;

/**
 * @author luwang
 * @description 秘钥封装
 * @date 2023/04/07
 */
public class DefaultCredentialProvider implements CredentialsProvider {

    private volatile Credentials creds;

    public DefaultCredentialProvider(Credentials creds) {
        setCredentials(creds);
    }

    public DefaultCredentialProvider(String appKey, String appSecret) {
        setCredentials(new DefaultCredentials(appKey, appSecret));
    }

    @Override
    public synchronized void setCredentials(Credentials creds) {
        if (creds == null) {
            throw new IllegalArgumentException("creds should not be null.");
        }

        this.creds = creds;
    }

    @Override
    public Credentials getCredentials() {
        if (this.creds == null) {
            throw new IllegalArgumentException("Invalid credentials");
        }
        return this.creds;
    }
}
