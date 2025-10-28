package com.lyy.wxloginlearn.exception;

/**
 * @author YueYang
 * Created on 2025/10/25 13:59
 * @version 1.0
 * 这里显示用户登录
 */
public class LoginCodeErrorException extends RuntimeException {
    public LoginCodeErrorException(String message) {
        super(message);
    }
}
