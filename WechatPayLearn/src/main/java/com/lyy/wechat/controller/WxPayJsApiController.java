package com.lyy.wechat.controller;

import com.lyy.wechat.service.IWxPayJsApiService;
import com.lyy.wechat.util.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/9 15:31
 * @version 1.0
 */
@RestController
@RequestMapping("/pay/jsapi")
public class WxPayJsApiController {
    @Autowired
    private IWxPayJsApiService wxPayJsApiService;

    //    @PostMapping("/createOrder") //Map<String, String> requestParams Post请求参数
    @GetMapping("/createOrder")//这里我用老师微信小程序前端
    public AjaxResult createOrder() {
        Map<String, String> requestParams = new HashMap<>();
        //老师的微信openid:oo_om7fncbSwMmL8YiTR4a-Ezw1g
        //我的微信小程序openid:oE9pL5Xi4IyQoUAzcHphTzSXRXPU
        requestParams.put("openid", "oo_om7fncbSwMmL8YiTR4a-Ezw1g");
        requestParams.put("totalFee", "0.01");
        requestParams.put("name", "测试商品");


        System.out.println("有人调用了jsApi的createOrder方法-》请求参数：" + requestParams);
        Map<String, String> result = wxPayJsApiService.jsApiPay(requestParams);
        return AjaxResult.success(result);
    }


    @GetMapping("/test")
    public String welcome() {
        return "success";
    }


}
