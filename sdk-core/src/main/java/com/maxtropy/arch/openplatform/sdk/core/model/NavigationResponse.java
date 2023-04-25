package com.maxtropy.arch.openplatform.sdk.core.model;

import lombok.Data;

@Data
public class NavigationResponse extends ItemResponse{
    /**
     * 当前id
     */
    private String currentId;
    /**
     * 等级 1:一级 2:二级 3:三级
     */
    private Integer level;
    /**
     * 名称
     */
    private String name;
    /**
     * 打开方式(0:当前页面打开，1:新建页面打开，2:强制url跳转)
     */
    private Integer openType;
    /**
     * 上级id
     */
    private String parentId;
    /**
     * 根id
     */
    private String rootId;
    /**
     * 跳转路径
     */
    private String transitPagePath;
    /**
     * 权重(权重越大，排序越前)
     */
    private Integer weighting;
}
