package com.maxtropy.arch.openplatform.sdk.core.auth;

public class RequestContextUtil {
    private static final ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    public static void setUserInfo(UserInfo userInfo) {
        RequestContextUtil.userInfoThreadLocal.set(userInfo);
    }

    public static UserInfo getUserInfo() {
        return RequestContextUtil.userInfoThreadLocal.get();
    }

    public static void clear() {
        userInfoThreadLocal.remove();
    }
}
