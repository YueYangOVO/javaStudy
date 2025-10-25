package com.lyy.wechat.config;

/**
 * @author YueYang
 * Created on 2025/10/9 15:15
 * @version 1.0
 * 微信支付的配置文件
 */
public interface WxPayConfig {
    //商户号
    String mchId = "1728809107";
    //商户api私钥路径
    String mchApiPrivateKeyPath = "F:\\newProjects\\javaStudy\\WechatPayLearn\\src\\main\\resources\\cert\\apiclient_key.pem";
    //商户api证书序列号
    String mchSerialNumber = "60C9280695BF463C26B9352262DBF264C642630A";

    //微信支付公钥文件路径
    String wxPayPublicKeyPath = "F:\\newProjects\\javaStudy\\WechatPayLearn\\src\\main\\resources\\cert\\pub_key.pem";
    //微信支付公钥id
    String wxPayPublicKeyId = "PUB_KEY_ID_0117288091072025092900211845002005";
    //apiV3密钥
    String apiV3Key = "PUB_KEY_ID_0117288091072025092900211845002005";

    //小程序的appId
    String appId = "wx18ac00cf04b30715";
    //支付结果回调url地址
    String notifyUrl = "https://www.greenold.com/wxpay/notify";


}
