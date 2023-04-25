package com.maxtropy.arch.openplatform.sdk.core.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class SessionResponse extends ResponseModel {
    private String sessionId;
    private Long expiredTime;


}
