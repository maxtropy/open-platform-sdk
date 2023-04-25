package com.maxtropy.arch.openplatform.sdk.auth.controller;

import com.maxtropy.arch.openplatform.sdk.auth.constant.CommonConstant;
import com.maxtropy.arch.openplatform.sdk.core.auth.GetUserInfo;
import com.maxtropy.arch.openplatform.sdk.core.auth.UserInfo;
import com.maxtropy.arch.openplatform.sdk.core.config.OpenPlatformProperties;
import com.maxtropy.arch.openplatform.sdk.core.model.BooleanResponse;
import com.maxtropy.arch.openplatform.sdk.core.model.R;
import com.maxtropy.arch.openplatform.sdk.core.model.SessionResponse;
import com.maxtropy.arch.openplatform.sdk.core.api.Api;
import com.maxtropy.arch.openplatform.sdk.core.auth.SessionIdProvider;
import com.maxtropy.arch.openplatform.sdk.core.OpenPlatformSdkClient;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.exception.ApiFailException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

/**
 * @author qinbaohai
 */
@RestController
@RequestMapping("/server")
@Slf4j
public class AuthController {

    @Resource
    private OpenPlatformSdkClient sdkClient;

    @Resource
    private SessionIdProvider sessionIdProvider;

    @Resource
    private OpenPlatformProperties openPlatformProperties;

    /**
     * 前端业务接口调用失败后，调用这个接口告诉后端SDK当前前端页面地址
     * @param response 响应
     * @param to 当前前端地址
     */
    @GetMapping("/landing")
    public void landing(HttpServletResponse response, @RequestParam("to") String to) throws IOException {
        log.info("进入/server/landing...");
        log.info("当前前端地址:{}",to);
        String appKey = openPlatformProperties.getAppKey();
        String callBackUrl = openPlatformProperties.getCallBackUrl();
        String rawServiceUrl = callBackUrl+"?redirect="+to;
        String encodeServiceUrl = response.encodeRedirectURL(rawServiceUrl);
        String url = openPlatformProperties.getLoginUrl()+"?source=OPENPLATFORM&appKey="+appKey+"&service="+encodeServiceUrl;
        log.info("重定向去登录 --> url: {}",url);
        response.sendRedirect(url);
    }

    /**
     * 单点登录回调接口
     * @param redirect 前端路径
     * @param ticket ST
     */
    @GetMapping("/callback")
    public void callback(HttpServletResponse response, @RequestParam("redirect") String redirect, @RequestParam("ticket") String ticket) throws IOException {
        log.info("单点登录回调开始 --> ticket: {} redirect: {}", ticket, redirect);
        // ST换取sessionId
        Api<SessionResponse> myApi = sdkClient.rawApi("/auth/session", HttpMethodEnum.GET, SessionResponse.class);
        SessionResponse session = null;
        try {
            session =  myApi.addQueryParam("ST", ticket).call();
            log.info("ST: {} --> session: {}", ticket, session);
        } catch (ApiFailException e) {
            log.info("ST: {}换取session接口调用失败", ticket);
            log.warn("ST换取session接口调用失败",e);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write("{\"code\":401,\"msg\":\"ST换取sessionId失败！\"}");
            response.flushBuffer();
        }

        // 有效session
        if(session != null && StringUtils.hasText(session.getSessionId())) {
            // sessionId
            String sessionId = session.getSessionId();
            // 生成应用内token
            String token = UUID.randomUUID().toString();
            log.info("sessionId: {} 对应 token: {}",sessionId, token);
            // 应用内token关联sessionId
            sessionIdProvider.setSessionId(token,sessionId);
            // 重定向
            // Set Cookie 到浏览器
            Cookie cookie = new Cookie(CommonConstant.TOKEN_NAME,token);
            cookie.setDomain(openPlatformProperties.getCookieDomain());
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge((int)(session.getExpiredTime() / 1000));

            response.addCookie(cookie);

            // 跳转到前端页面，带回isvAppToken
            if(redirect.contains("?")) {
                redirect = redirect + "&" + CommonConstant.TOKEN_NAME + "=" + token;
            }else {
                redirect = redirect + "?" + CommonConstant.TOKEN_NAME + "=" + token;
            }
            response.sendRedirect(response.encodeRedirectURL(redirect));
        }
    }

    /**
     * 登出
     * @return 登出结果
     */
    @GetMapping("/logout")
    public R<Boolean> logout(@GetUserInfo UserInfo userInfo) {
        log.info("开始登录 --> sessionId: {}", userInfo.getSessionId());
        Api<BooleanResponse> myApi = sdkClient.rawApi("/auth/logout", HttpMethodEnum.GET, BooleanResponse.class);
        myApi.addQueryParam("sessionId",userInfo.getSessionId());
        try {
            return R.success(myApi.call().getSuccess());
        } catch (ApiFailException e) {
            log.warn("登出失败:", e);
            return R.failure();
        }
    }
}
