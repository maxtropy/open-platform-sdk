package com.maxtropy.arch.openplatform.sdk.core.exception;

/**
 * @author willardwang
 * @description
 * @date 2019/03/13
 * */
public class HttpStatusNoScOKException extends RuntimeException {
    private int httpStatus;
    private String response;

    public HttpStatusNoScOKException(int httpStatus, String response, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.response = response;
    }

    public int getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
