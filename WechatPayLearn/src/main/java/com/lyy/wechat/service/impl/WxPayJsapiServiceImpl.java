package com.lyy.wechat.service.impl;

import com.lyy.wechat.config.WxPayConfig;
import com.lyy.wechat.service.IWxPayJsapiService;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAPublicKeyConfig;
import com.wechat.pay.java.service.payments.jsapi.JsapiServiceExtension;
import com.wechat.pay.java.service.payments.jsapi.model.Amount;
import com.wechat.pay.java.service.payments.jsapi.model.Payer;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayRequest;
import com.wechat.pay.java.service.payments.jsapi.model.PrepayWithRequestPaymentResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/21 22:26
 * @version 1.0
 */
@Slf4j
@Service
public class WxPayJsapiServiceImpl implements IWxPayJsapiService {


    /**
     * 用户端微信小程序发起的jsapi支付请求
     *
     * @param requestParams 用户小程序发请求带来的参数
     * @return 响应给用户小程序前端支付所必须的参数
     */
    @Override
    public Map<String, String> jsapiPay(Map<String, String> requestParams) {
        //解析参数
        String openID = requestParams.get("openid"); //获取用户的openID 付款人的openId
        String totalFee = requestParams.get("totalFee"); //花费多少钱
        String desc = requestParams.get("name");  //买的商品名字 也就是商品描述信息

        //生成商家订单号
        String orderId = "My-Order-" + System.currentTimeMillis();
        //TODO 将这个订单号保存到数据库中

        //1. 配置商户相关数据
        Config config = new RSAPublicKeyConfig.Builder()
                .merchantId(WxPayConfig.mchId) //商户号
                .privateKeyFromPath(WxPayConfig.mchApiPrivateKeyPath) //商户api私钥路径
                .publicKeyFromPath(WxPayConfig.wxPayPublicKeyPath) //微信支付公钥文件路径
                .publicKeyId(WxPayConfig.wxPayPublicKeyId) //微信支付公钥id
                .merchantSerialNumber(WxPayConfig.mchSerialNumber) //商户api证书序列号
                .apiV3Key(WxPayConfig.apiV3Key) //apiV3密钥
                .build();


        //2. 配置商品相关信息 (5个字符串 加上两个对象)
        PrepayRequest prepayRequest = new PrepayRequest();
        prepayRequest.setAppid(WxPayConfig.appId); //这个必须是与商户号绑定的api Id
        prepayRequest.setMchid(WxPayConfig.mchId); //商户号
        prepayRequest.setDescription(desc); //商品描述
        prepayRequest.setOutTradeNo(orderId); //商户订单号
        prepayRequest.setNotifyUrl(WxPayConfig.notifyUrl); //支付结果回调url地址
        Payer payer = new Payer();
        //封装付款人的信息
        payer.setOpenid(openID); //付款人的openId
        prepayRequest.setPayer(payer); //付款人信息
        //封装付款金额对象
        Amount amount = new Amount();
        amount.setTotal(Integer.parseInt(totalFee)); //付款金额 单位分
        amount.setCurrency("CNY"); //付款金额 单位 人民币
        prepayRequest.setAmount(amount); //付款金额对象


        //向微信服务器发请求
        JsapiServiceExtension jsapiService = new JsapiServiceExtension.Builder()
                .config(config) //配置对象
                .build();
        PrepayWithRequestPaymentResponse prepayResponse = jsapiService.prepayWithRequestPayment(prepayRequest); //拿到预支付响应体
        log.info("预支付响应体为：{}", prepayResponse);


        //3. 将预支付的响应体响应给前端
        Map<String, String> map = new HashMap<>();
        map.put("appId", WxPayConfig.appId);
        map.put("timeStamp", prepayResponse.getTimeStamp());
        map.put("nonceStr", prepayResponse.getNonceStr());
        map.put("package", prepayResponse.getPackageVal());
        map.put("signType", prepayResponse.getSignType());
        map.put("paySign", prepayResponse.getPaySign());

        return map;
    }
}
