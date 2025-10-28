package com.lyy.wxloginlearn.controller;


import com.lyy.wxloginlearn.entity.dto.WxLoginDTO;
import com.lyy.wxloginlearn.entity.pojo.WxUser;
import com.lyy.wxloginlearn.service.IWxUserService;
import com.lyy.wxloginlearn.utils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 微信小程序用户表 前端控制器
 * </p>
 *
 * @author YueYang
 * @since 2025-10-25
 */
@Api("小程序登录注册控制器")
@RestController
@RequestMapping("/wx-user")
public class WxUserController {

    @Autowired
    private IWxUserService wxUserService;


    /**
     * 微信小程序用户登录
     *
     * @param wxLoginDTO 微信小程序用户登录DTO
     * @return 微信小程序用户登录结果
     */
    @ApiOperation("小程序用户登录注册接口")
    @PostMapping("/login")
    public R wxLogin(@RequestBody WxLoginDTO wxLoginDTO) {

        WxUser wxUser = wxUserService.handleWxLogin(wxLoginDTO);

        if (wxUser != null) {
            return R.success("登录成功", wxUser);
        }
        return R.error("登录失败");
    }

    /**
     * 获取微信小程序用户信息
     *
     * @param id 微信小程序用户id
     * @return 微信小程序用户信息
     */
    @ApiOperation("获取用户信息接口")
    @GetMapping("/userinfo/{id}")
    public R getUserInfo(@PathVariable Long id) {
        WxUser wxUser = wxUserService.getUserInfo(id);
        if (wxUser != null) {
            return R.success("获取用户信息成功", wxUser);
        }
        return R.error("获取用户信息失败");
    }

}
