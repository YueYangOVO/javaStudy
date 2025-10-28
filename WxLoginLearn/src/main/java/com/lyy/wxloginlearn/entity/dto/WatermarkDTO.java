package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 14:37
 * @version 1.0
 * 这个是根据phoneCode和openId获取手机号的响应内容中的watermark字段
 * 包含了请求的时间戳和appid
 */
@Data
public class WatermarkDTO {

    /**
     * 时间戳
     */
    private Long timestamp;

    /**
     * appid
     */
    private String appid;
}
