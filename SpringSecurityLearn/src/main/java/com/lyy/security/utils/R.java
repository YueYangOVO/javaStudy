package com.lyy.security.utils;

import lombok.Builder;
import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/9/22 10:12
 * @version 1.0
 * R类统一返回结果
 */
@Data
@Builder
public class R {

    private int code; //返回状态码

    private String msg; //结果信息

    private Object data; //返回数据

    public static R success(String msg, Object data) {
        return R.builder()
                .code(200)
                .msg(msg)
                .data(data)
                .build();
    }

    public static R success(Integer code, String msg, Object data) {
        return R.builder()
                .code(code)
                .msg(msg)
                .data(data)
                .build();
    }

    public static R success(String msg) {
        return R.builder()
                .code(200)
                .msg(msg)
                .data(null)
                .build();
    }

    public static R error(String msg) {
        return R.builder()
                .code(400)
                .msg(msg)
                .data(null)
                .build();
    }

    public static R error(Integer code, String msg) {
        return R.builder()
                .code(code)
                .msg(msg)
                .data(null)
                .build();
    }
}
