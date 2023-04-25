package com.maxtropy.arch.openplatform.sdk.api.model.response;

import com.maxtropy.arch.openplatform.sdk.core.model.ItemResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CustomerItemResponse extends ItemResponse {
    private String mcid;
    private String code;
    private Date createTime;
    private String fullName;
    private String logoUrl;
    private String name;
    private Date updateTime;
}
