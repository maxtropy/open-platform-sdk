package com.maxtropy.arch.openplatform.sdk.core.model;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class CountResponse extends ResponseModel {
    private Integer count;

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }
}
