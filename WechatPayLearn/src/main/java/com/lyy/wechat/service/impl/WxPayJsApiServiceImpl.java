package com.lyy.wechat.service.impl;

import com.lyy.wechat.config.WxPayConfig;
import com.lyy.wechat.service.IWxPayJsApiService;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/9 15:30
 * @version 1.0
 */
@Service
public class WxPayJsApiServiceImpl implements IWxPayJsApiService {

    /**
     * 微信支付回调
     * 微信支付后返回给后端支付情况
     *
     * @param requestParams 返回结果 用户小程序发请求时带来的参数
     * @return 响应结果 响应给用户小程序前端支付所必须的参数
     */
    @Override
    public Map<String, String> jsApiPay(Map<String, String> requestParams) {
        String openId = requestParams.get("openid"); //微信小程序的openId
        String totalFee = requestParams.get("totalFee"); //要付款的金额
        String desc = requestParams.get("name"); //要买的名称

        //生成商家订单编号 与微信的订单号不同
        String orderId = "wx-lyy-" + System.currentTimeMillis();
        System.out.println("在自己的数据库中创建一个未支付的订单: " + orderId);

        //1. 配置商户相关的数据
        Config config = new RSAPublicKeyConfig.Builder()
                .merchantId(WxPayConfig.MCH_ID)
                .privateKeyFromPath(WxPayConfig.MCH_API_KEY_PATH)
//                .publicKeyFromPath(WxPayConfig.WX_PAY_PUBLIC_KEY_PATH)
//                .publicKeyId(WxPayConfig.WX_PAY_PUBLIC_KEY_ID)
                .merchantSerialNumber(WxPayConfig.MCH_CERTIFICATE_SERIAL_NO)
                .apiV3Key(WxPayConfig.API_V3_KEY)
                .build();

        //2. 配置商品相关的数据 微信官方文档有这些必填项(5个字符串+2个对象)
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(WxPayConfig.APP_ID);
        prepayRequest.setMchid(WxPayConfig.MCH_ID); //配置商户号
        prepayRequest.setOutTradeNo(orderId);
        prepayRequest.setDescription(desc);
        prepayRequest.setNotifyUrl(WxPayConfig.NOTIFY_URL);
        //两个对象
        Payer payer = new Payer();//付费对象
        payer.setOpenid(openId);
        Amount amount = new Amount();//付费金额对象
        amount.setCurrency("CNY"); //货币类型
        amount.setTotal(Integer.parseInt(totalFee));

        //将对象放置到请求对象中
        prepayRequest.setPayer(payer);
        prepayRequest.setAmount(amount);

        //3. 向微信服务器发送请求 这里有两种下面的类，Extension是扩展类
        JsapiServiceExtension jsapiService = new JsapiServiceExtension.Builder().config(config).build();
        //发送请求，拿到预支付的响应体（里面有重要的4个参数， 预支付id，签名，应答时间戳，32位随机字符串）
        PrepayWithRequestPaymentResponse prepayResponse = jsapiService.prepayWithRequestPayment(prepayRequest);
        System.out.println("微信服务器返回的响应体: " + prepayResponse);


        //4. 将微信服务器的响应参数进行解析 响应给前端
        Map<String, String> map = new HashMap<>();
        map.put("timeStamp", prepayResponse.getTimeStamp());
        map.put("nonceStr", prepayResponse.getNonceStr());
        map.put("package", prepayResponse.getPackageVal());
        map.put("signType", prepayResponse.getSignType());
        System.out.println("响应给前端的参数: " + map);
        return map;
    }
}
