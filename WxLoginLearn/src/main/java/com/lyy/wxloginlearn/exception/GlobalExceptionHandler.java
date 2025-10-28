package com.lyy.wxloginlearn.exception;

import com.lyy.wxloginlearn.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author YueYang
 * Created on 2025/10/25 11:09
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 处理全局异常
     * 最大异常 接收各种异常
     *
     * @param e 异常对象
     * @return 异常结果
     */
    @ExceptionHandler
    public R handleException(Exception e) {
        log.error("全局异常捕获", e);
        return R.error(500, e.getMessage());
    }

    /**
     * 处理 IllegalArgumentException 异常
     *
     * @param e 异常对象
     * @return 异常结果
     */
    @ExceptionHandler
    public R handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(" IllegalArgumentException 异常捕获", e);
        return R.error(400, e.getMessage());
    }

    /**
     * 处理 LoginCodeErrorException 异常
     * 这个异常是通过loginCode向微信服务器发信息请求失败时抛出的异常
     *
     * @param e 异常对象
     * @return 异常结果
     */
    @ExceptionHandler
    public R handleLoginCodeErrorException(LoginCodeErrorException e) {
        log.error(" LoginCodeErrorException 异常捕获", e);
        return R.error(400, e.getMessage());
    }

}
