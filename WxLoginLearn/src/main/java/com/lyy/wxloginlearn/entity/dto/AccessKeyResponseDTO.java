package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 18:33
 * @version 1.0
 * <p>
 * 用于接收获取手机号的access_token 这个一般两个小时内有效
 */
@Data
public class AccessKeyResponseDTO {
    /**
     * 会话密钥
     */
    private String access_token;

    /**
     * 会话密钥过期时间
     */
    private Integer expires_in;
}
