package com.maxtropy.arch.openplatform.sdk.core.exception;

/**
 * @author luwang
 * @description
 * @date 2023/04/18
 */
public class SdkException extends RuntimeException {
    public SdkException(String message, Throwable cause) {
        super(message, cause);
    }

    public SdkException(String message) {
        super(message);
    }
}
