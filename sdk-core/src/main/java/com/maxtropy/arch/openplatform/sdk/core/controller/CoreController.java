package com.maxtropy.arch.openplatform.sdk.core.controller;

import com.maxtropy.arch.openplatform.sdk.core.OpenPlatformSdkClient;
import com.maxtropy.arch.openplatform.sdk.core.api.Api;
import com.maxtropy.arch.openplatform.sdk.core.auth.GetUserInfo;
import com.maxtropy.arch.openplatform.sdk.core.auth.UserInfo;
import com.maxtropy.arch.openplatform.sdk.core.common.HttpMethodEnum;
import com.maxtropy.arch.openplatform.sdk.core.exception.ApiFailException;
import com.maxtropy.arch.openplatform.sdk.core.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
@Slf4j
public class CoreController  {

    @Resource
    private OpenPlatformSdkClient sdkClient;

    /**
     * 获取导航
     */
    @GetMapping("/api/getStaffNavigation")
    @ResponseBody
    public R<List<NavigationResponse>> getNavigation(@GetUserInfo UserInfo userInfo) {
        log.info("获取导航 --> userInfo: {}",userInfo);
        if(userInfo.getCurrentStaffId() == null) {
            return null;
        }
        try {
            Api<ListResponse<NavigationResponse>> myApi = sdkClient.rawApi("/base/staff/{staffId}/navigation", HttpMethodEnum.GET, ListResponse.class, NavigationResponse.class);
            myApi.setPathVariables(userInfo.getCurrentStaffId() + "");
            ListResponse<NavigationResponse> response = myApi.call();
            log.info("获取导航结果: {}",response.getItems());
            return R.success(response.getItems());
        } catch (ApiFailException e) {
            log.warn("获取导航失败:",e);
            return R.failure();
        }
    }


    /**
     * 查询staff列表
     * @param userInfo 用户信息
     * @return staff列表
     */
    @GetMapping("/api/getStaffList")
    @ResponseBody
    public R<List<StaffResponse>> getStaffList(@GetUserInfo UserInfo userInfo) {
        log.info("查询staff列表 --> {}",userInfo);
        Api<ListResponse<StaffResponse>> staffApi = sdkClient.rawApi("/base/staff/list", HttpMethodEnum.GET, ListResponse.class, StaffResponse.class);
        List<StaffResponse> staffList = null;
        staffApi.addQueryParam("muid",userInfo.getUserId());
        try {
            // 1. 查询staff
            staffList = staffApi.call().getItems();
            log.info("查询staff列表结果: {}",staffList);
        } catch (ApiFailException e) {
            log.info("查询staff列表失败",e);
            return R.failure();
        }
        return R.success(staffList);
    }

    /**
     * 查询用户信息
     * @param userInfo 用户信息
     * @return 用户信息
     */
    @GetMapping("/api/getUserInfo")
    @ResponseBody
    public R<UserInfo> getUserInfo(@GetUserInfo UserInfo userInfo) {
        log.info("查询用户信息 --> userInfo: {}",userInfo);
        return R.success(userInfo);
    }

    /**
     *
     * @param staffId 员工id
     * @param userInfo 用户信息
     * @return 用户信息
     */
    @GetMapping("/api/staffSwitch")
    @ResponseBody
    public R<UserInfoResponse> staffSwitch(@RequestParam("staffId") Long staffId, @GetUserInfo UserInfo userInfo) {
        log.info("切换员工开始 --> staffId: {}, userInfo: {}", staffId, userInfo);
        Api<UserInfoResponse> myApi = sdkClient.rawApi("/auth/staff/switch",HttpMethodEnum.PUT, UserInfoResponse.class);
        myApi.addQueryParam("sessionId",userInfo.getSessionId());
        myApi.addQueryParam("staffId",staffId+"");
        try {
            UserInfoResponse response = myApi.call();
            log.info("切换员工结果: {}",response);
            return R.success(response);
        } catch (ApiFailException e) {
            log.warn("切换员工失败",e);
            return R.failure();
        }
    }
}
