package com.lyy.wxloginlearn.service;

import com.lyy.wxloginlearn.entity.dto.WxLoginDTO;
import com.lyy.wxloginlearn.entity.pojo.WxUser;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 微信小程序用户表 服务类
 * </p>
 *
 * @author YueYang
 * @since 2025-10-25
 */
public interface IWxUserService extends IService<WxUser> {

    /**
     * 处理微信小程序用户登录
     *
     * @param wxLoginDTO 微信小程序用户登录DTO
     * @return 微信小程序用户登录结果
     */
    WxUser handleWxLogin(WxLoginDTO wxLoginDTO);

    /**
     * 获取微信小程序用户信息
     *
     * @param id 微信小程序用户id
     * @return 微信小程序用户信息
     */
    WxUser getUserInfo(Long id);
}
