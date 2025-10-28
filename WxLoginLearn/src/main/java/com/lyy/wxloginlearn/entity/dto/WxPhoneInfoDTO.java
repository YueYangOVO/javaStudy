package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 14:35
 * @version 1.0
 * 这个是根据phoneCode和openId获取手机号的响应内容中的phoneInfo字段
 */
@Data
public class WxPhoneInfoDTO {

    /**
     * 用户绑定的手机号（国外手机号会有区号）
     * 例如：+8613512345678
     */
    private String phoneNumber;

    /**
     * 没有区号的手机号 例如：13512345678
     */
    private String purePhoneNumber;

    /**
     * 区号（如中国为86）
     */
    private Integer countryCode;

    /**
     * 水印信息
     */
    private WatermarkDTO watermark;

}
