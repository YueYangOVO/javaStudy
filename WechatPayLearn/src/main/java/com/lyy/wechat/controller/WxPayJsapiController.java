package com.lyy.wechat.controller;

import com.lyy.wechat.service.IWxPayJsapiService;
import com.lyy.wechat.util.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/21 22:27
 * @version 1.0
 */
@Slf4j
@RestController
@RequestMapping("/wxpay/jsapi")
public class WxPayJsapiController {

    @Autowired
    private IWxPayJsapiService wxPayJsapiService;

    /**
     * 用户端微信小程序发起的jsapi支付请求
     *
     * @param requestParams 用户小程序发请求带来的参数
     * @return 响应给用户小程序前端支付所必须的参数
     */
    @RequestMapping("/createOrder")
    public AjaxResult createOrder(@RequestBody Map<String, String> requestParams) {
        log.info("用户端微信小程序发起的jsapi支付请求，参数为：{}", requestParams);
        Map<String, String> map = wxPayJsapiService.jsapiPay(requestParams);
        return AjaxResult.success(map);
    }


}