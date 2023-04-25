package com.maxtropy.arch.openplatform.sdk.core.model;



import java.io.Serializable;

/**
 * @author luwang
 * @description
 * @date 2023/04/19
 */

public class FailResponse implements Serializable {

    private String errorMessage;
    private String errorCode;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
