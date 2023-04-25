package com.maxtropy.arch.openplatform.sdk.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UserInfoResponse extends ResponseModel {
    private String userId;
    private Long currentStaffId;
    private String username;
    private String name;
    private String headPic;
    private String tenantUuid;

}
