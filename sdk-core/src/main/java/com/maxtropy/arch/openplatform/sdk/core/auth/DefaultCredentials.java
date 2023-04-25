package com.maxtropy.arch.openplatform.sdk.core.auth;

public class DefaultCredentials implements Credentials {

    private final String appKey;
    private final String appSecret;


    public DefaultCredentials(String appKey, String appSecret) {
        if (appKey == null || appKey.equals("")) {
            throw new IllegalArgumentException("appKey key id should not be null or empty.");
        }
        if (appSecret == null || appSecret.equals("")) {
            throw new IllegalArgumentException("appSecret access key should not be null or empty.");
        }

        this.appKey = appKey;
        this.appSecret = appSecret;

    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getAppSecret() {
        return appSecret;
    }


}