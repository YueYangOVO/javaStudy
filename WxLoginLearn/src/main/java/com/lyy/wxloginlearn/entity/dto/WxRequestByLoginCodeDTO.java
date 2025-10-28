package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 13:45
 * @version 1.0
 * 当前类用来接收通过wx.login获取code,像微信后端发送请求接收的参数
 * 注意请求成功后 微信只返回openid和session_key
 * 请求失败后微信会返回 errcode和errmsg 不返回openid和session_key
 */
@Data
public class WxRequestByLoginCodeDTO {

    /**
     * 会话密钥
     */
    private String session_key;

    /**
     * unionid 一个用户在一家公司appid下的
     * 所有程序中unionid值相同
     */
    private String unionid;

    /**
     * 错误信息
     */
    private String errmsg;


    /**
     * openid 用户唯一标识
     */
    private String openid;


    /**
     * 错误码 微信后端返回0表示获取openid成功
     * 其他码都是失败的，具体可以看官方文档
     */
    private Integer errcode;
}
