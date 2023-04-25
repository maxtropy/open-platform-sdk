package com.maxtropy.arch.openplatform.sdk.core.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/10
 */
@ConfigurationProperties(
        prefix = "maxtropy.openplatform"
)
public class OpenPlatformProperties {

    private String endpoint;

    private String appKey;

    private String appSecret;

    private String callBackUrl;

    private String cookieDomain;

    private String loginUrl;

    private List<String> whiteList;

    public boolean isIntegratedCoreUri() {
        return integratedCoreUri;
    }

    public void setIntegratedCoreUri(boolean integratedCoreUri) {
        this.integratedCoreUri = integratedCoreUri;
    }

    private boolean integratedCoreUri;


    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }

    public String getCookieDomain() {
        return cookieDomain;
    }

    public void setCookieDomain(String cookieDomain) {
        this.cookieDomain = cookieDomain;
    }

    public String getLoginUrl() {
        return loginUrl;
    }

    public void setLoginUrl(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public List<String> getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(List<String> whiteList) {
        this.whiteList = whiteList;
    }
}
