package com.maxtropy.arch.openplatform.sdk.core.model;

import java.util.List;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class ListResponse<T> extends ResponseModel {

    private List<T> items;

    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }
}
