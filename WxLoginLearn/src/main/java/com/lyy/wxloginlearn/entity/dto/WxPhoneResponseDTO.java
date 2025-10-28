package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 14:34
 * @version 1.0
 * 这个类用来接收根据phoneCode和openId获取手机号的响应内容
 */
@Data
public class WxPhoneResponseDTO {

    /**
     * 错误码
     */
    private Integer errcode;

    /**
     * 错误信息
     */
    private String errmsg;

    /**
     * 手机号信息
     */
    private WxPhoneInfoDTO phone_info;

}
