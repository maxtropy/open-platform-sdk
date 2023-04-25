package com.maxtropy.arch.openplatform.sdk.api.auth;

import com.maxtropy.arch.openplatform.sdk.core.api.Api;
import com.maxtropy.arch.openplatform.sdk.core.api.ApiMapper;
import com.maxtropy.arch.openplatform.sdk.core.auth.CredentialsProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.common.ServiceClient;
import com.maxtropy.arch.openplatform.sdk.core.model.BooleanResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.SessionResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.UserInfoResponse;

import java.net.URI;

/**
 * @author luwang
 * @description 登录授权相关
 * @date 2023/04/06
 */
public class AuthApiMapper extends ApiMapper {

    public AuthApiMapper(URI uri, ServiceClient serviceClient,
                         CredentialsProvider credentialsProvider) {
        super(uri, serviceClient, credentialsProvider);
    }

    public Api<UserInfoResponse> staffSwitchApi(String sessionId, Integer staffId) {

        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/auth/staff/switch", HttpMethodEnum.GET, UserInfoResponse.class)
                .addQueryParam("sessionId", sessionId)
                .addQueryParam("staffId", staffId + "");

    }

    public Api<UserInfoResponse> userInfoApi(String sessionId) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/auth/userInfo", HttpMethodEnum.GET, UserInfoResponse.class)
                .addQueryParam("sessionId", sessionId);
    }

    public Api<SessionResponse> sessionApi(String sessionId) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/auth/session", HttpMethodEnum.GET, SessionResponse.class)
                .addQueryParam("sessionId", sessionId);
    }

    public Api<BooleanResponse> logoutApi(String sessionId) {
        return Api.Builder.build(endpoint,
                credentialsProvider,
                serviceClient,
                "/auth/session", HttpMethodEnum.GET, BooleanResponse.class)
                .addQueryParam("sessionId", sessionId);
    }

}
