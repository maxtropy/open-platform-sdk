package com.maxtropy.arch.openplatform.sdk.core.auth;

public interface SessionIdProvider {
    void setSessionId(String token, String sessionId);
    String getSessionId(String token);
}
