package com.lyy.wechat.config;

/**
 * @author YueYang
 * Created on 2025/10/9 15:15
 * @version 1.0
 * 微信支付的配置文件
 */
public class WxPayConfig {
    //商户号
    public static final String MCH_ID = "1728809107";
    //商户API私钥路径
    public static final String MCH_API_KEY_PATH = "F:\\cert\\apiclient_key.pem";
    //商户api证书序列号
    public static final String MCH_CERTIFICATE_SERIAL_NO = "60C9280695BF463C26B9352262DBF264C642630A";

    //微信支付公钥文件路径
    public static final String WX_PAY_PUBLIC_KEY_PATH = "F:\\cert\\apiclient_cert.pem";
    //微信支付公钥id
    public static final String WX_PAY_PUBLIC_KEY_ID = "PUB_KEY_ID_0117288091072025092900211845002005";
    //APIv3密钥
    public static final String API_V3_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAvYq7SNjH2QsJI3NgQS3j" +
            "xxl8OUEdlnjOeswbLslA///osDlgd0ajsBNvW0Ue94qg2u2dV+EwT1E7KXEXurIy" +
            "eDYFnQ8enT/cOpnKhWi+E+FhJanaaYyfWVwA7IBw5t0Xna//iZ8kAf3yKPadSPwu" +
            "APb3/v+zfIabnjYOMNekmrG9NcmdmA7H7J8emigrjTSVU+0Rph3hPDkgMTspoT5G" +
            "9lEo33AYEbFj0IEtmf9WMZ14U/4PQZYZBBpL7vdaA6xOKBWxsf/7z8S/9MzD5jfW" +
            "MaUs2QRt+hm3o54YEY/jbG8C39IRwnFXDXfD45kFopKQpuaCfHxJ7uMxhCBfYid2" +
            "3QIDAQAB";

    //小程序appID
    public static final String APP_ID = "wx18ac00cf04b30715";
    //小程序支付结果回调访问的网址
    public static final String NOTIFY_URL = "http://locahost:8080/pay/jsapi/callback";


}
