package com.maxtropy.arch.openplatform.sdk.core.exception;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
public class  ApiFailException extends Exception {

    private String httpCode;
    private String message;
    private String code;

    public ApiFailException(String httpCode, String code, String message) {
        super(message);
        this.httpCode = httpCode;
        this.message = message;
        this.code = code;
    }

    @Override
    public String toString() {
        return "ApiFailException{" +
                "httpCode='" + httpCode + '\'' +
                ", message='" + message + '\'' +
                ", code='" + code + '\'' +
                '}';
    }

    public String getHttpCode() {
        return httpCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }
}
