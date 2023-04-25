package com.maxtropy.arch.openplatform.sdk.core.auth;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {

    private String userId;
    private Long currentStaffId;
    private String username;
    private String name;
    private String headPic;
    private String tenantUuid;

    private String sessionId;
}
