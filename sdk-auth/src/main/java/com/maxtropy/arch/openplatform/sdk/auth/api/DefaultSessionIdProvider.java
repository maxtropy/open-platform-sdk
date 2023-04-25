package com.maxtropy.arch.openplatform.sdk.auth.api;

import com.maxtropy.arch.openplatform.sdk.core.auth.SessionIdProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
@ConditionalOnSingleCandidate(SessionIdProvider.class)
public class DefaultSessionIdProvider implements SessionIdProvider {

    private static final ConcurrentHashMap<String, String> sessionMap = new ConcurrentHashMap<>();

    @Override
    public void setSessionId(String token, String sessionId) {
        sessionMap.put(token, sessionId);
    }

    @Override
    public String getSessionId(String token) {
        return sessionMap.get(token);
    }
}
