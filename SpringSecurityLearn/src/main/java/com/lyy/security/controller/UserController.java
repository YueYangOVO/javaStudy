package com.lyy.security.controller;


import com.lyy.security.utils.R;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author YueYang
 * @since 2025-09-22
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("test")
    public R test() {
        return R.success("hello 月阳");
    }

    @GetMapping("/welcome")
    public R admin(Principal principal) {
        return R.success("获取成功", principal);
    }

}
