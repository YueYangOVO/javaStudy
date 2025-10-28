package com.lyy.wxloginlearn.exception;

/**
 * @author YueYang
 * Created on 2025/10/25 15:38
 * @version 1.0
 * token解析异常
 */
public class ParseTokenException extends RuntimeException {
    /**
     * 构造方法
     *
     * @param message 异常信息
     */
    public ParseTokenException(String message) {
        super(message);

    }
}
