package com.lyy.wechat.service;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/9 15:26
 * @version 1.0
 * 接收前端返回的支付结果
 */
public interface IWxPayJsApiService {

    /**
     * 微信支付回调
     * 微信支付后返回给后端支付情况
     * @param requestParams 返回结果 用户小程序发请求时带来的参数
     * @return 响应结果 响应给用户小程序前端支付所必须的参数
     */
    Map<String, String> jsApiPay(@RequestBody Map<String, String> requestParams);

}
