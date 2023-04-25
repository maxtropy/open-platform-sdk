package com.maxtropy.arch.openplatform.sdk.core.model;

import com.maxtropy.arch.openplatform.sdk.core.model.ResponseModel;

/**
 * @author luwang
 * @description
 * @date 2023/04/09
 */
public class StringResponse extends ResponseModel {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
