package com.maxtropy.arch.openplatform.sdk.core.model;

import lombok.Data;

import java.util.Date;

@Data
public class StaffResponse extends ItemResponse{
    /**
     * 员工id
     */
    private Long id;
    /**
     * 统一组织id
     */
    private String mcid;
    /**
     * 统一用户id
     */
    private String muid;
    /**
     * 员工code
     */
    private String staffCode;
    /**
     * 状态 0.正常 1.禁用 2.冻结
     */
    private Integer status;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 组织简称
     */
    private String customerName;

    /**
     * 组织全称
     */
    private String customerFullName;

    /**
     * 租户mcid
     */
    private String tenantMcid;
    /**
     * 租户名称
     */
    private String tenantName;
}
