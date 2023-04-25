package com.maxtropy.arch.openplatform.sdk.auth.filter;

import com.maxtropy.arch.openplatform.sdk.auth.constant.CommonConstant;
import com.maxtropy.arch.openplatform.sdk.core.auth.RequestContextUtil;
import com.maxtropy.arch.openplatform.sdk.core.auth.UserInfo;
import com.maxtropy.arch.openplatform.sdk.core.config.OpenPlatformProperties;
import com.maxtropy.arch.openplatform.sdk.core.model.UserInfoResponse;
import com.maxtropy.arch.openplatform.sdk.core.OpenPlatformSdkClient;
import com.maxtropy.arch.openplatform.sdk.core.exception.ApiFailException;
import com.maxtropy.arch.openplatform.sdk.core.util.SdkUtils;
import com.maxtropy.arch.openplatform.sdk.core.api.Api;
import com.maxtropy.arch.openplatform.sdk.core.auth.SessionIdProvider;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebFilter(urlPatterns = "/*")
@Slf4j
public class LoginStatusFilter implements Filter {

    @Resource
    private OpenPlatformSdkClient sdkClient;

    @Resource
    private SessionIdProvider sessionIdProvider;

    @Resource
    private OpenPlatformProperties openPlatformProperties;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try{
            HttpServletRequest servletRequest = (HttpServletRequest) request;

            // 放行单点登录相关接口
            String path = servletRequest.getServletPath();
            // 接口白名单
            List<String> whiteList = Optional.ofNullable(openPlatformProperties.getWhiteList()).orElse(new ArrayList<>());
            whiteList.add("/server/landing");
            whiteList.add("/server/callback");
            for (String p : whiteList) {
                if(path.startsWith(p.trim())) {
                    log.info("白名单: {}",p);
                    chain.doFilter(request,response);
                    return;
                }
            }


            // 判断token有效性
            Cookie[] cookies = servletRequest.getCookies();
            String token = SdkUtils.getCookie(cookies, CommonConstant.TOKEN_NAME);
            // token不为空
            if(StringUtils.hasText(token)) {
                // token换sessionId
                String sessionId = sessionIdProvider.getSessionId(token);
                // sessionId存在
                if(sessionId != null) {
                    // 查询用户信息
                    Api<UserInfoResponse> myApi = sdkClient.rawApi("/auth/userInfo", HttpMethodEnum.GET, UserInfoResponse.class);
                    try {
                        UserInfoResponse userInfo = myApi.addQueryParam("sessionId", sessionId).call();

                        UserInfo info = new UserInfo();
                        info.setCurrentStaffId(userInfo.getCurrentStaffId());
                        info.setSessionId(sessionId);
                        info.setUserId(userInfo.getUserId());
                        info.setName(userInfo.getName());
                        info.setUsername(userInfo.getUsername());
                        info.setHeadPic(userInfo.getHeadPic());
                        info.setTenantUuid(userInfo.getTenantUuid());

                        RequestContextUtil.setUserInfo(info);

                        chain.doFilter(request,response);
                        return;
                    } catch (ApiFailException e) {
                        response.setContentType("application/json;charset=utf-8");
                        response.getWriter().write("{\"code\":401,\"msg\":\"请走登录流程！\"}");
                        response.flushBuffer();
                        return;
                    }
                }
            }
            // 其他情况认为需要重新登录 - 响应相应的错误码
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"请走登录流程！\"}");
            response.flushBuffer();
        }  finally {
            RequestContextUtil.clear();
        }
    }
}
