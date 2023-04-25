package com.maxtropy.arch.openplatform.sdk.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> success(T data) {
        return new R<>(200,"接口调用成功",data);
    }

    public static <T> R<T> failure() {
        return new R<>(500,"接口调用失败",null);
    }

    public static <T> R<T> build(Integer code, String msg, T data) {
        return new R<>(code,msg,data);
    }
}
