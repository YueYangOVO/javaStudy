package com.lyy.wxloginlearn.entity.dto;

import lombok.Data;

/**
 * @author YueYang
 * Created on 2025/10/25 10:59
 * @version 1.0
 */
@Data
public class WxLoginDTO {

    /**
     * 登录凭证码
     */
    private String loginCode;


    /**
     * 获取手机号凭证码
     */
    private String phoneCode;

    /**
     * 用户头像地址
     */
    private String avatarUrl;

    /**
     * 用户昵称
     */
    private String nickname;
}
