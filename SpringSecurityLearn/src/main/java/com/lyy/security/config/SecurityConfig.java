package com.lyy.security.config;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lyy.security.common.AuthUser;
import com.lyy.security.entity.pojo.User;
import com.lyy.security.mapper.RoleMapper;
import com.lyy.security.mapper.UserMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/22 10:10
 * @version 1.0
 */
@Configuration
@EnableWebSecurity //开启spring security自动配置功能
public class SecurityConfig implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //根据传递过来的username拿取数据库中的信息
        User dbUser = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getLoginAct, username));
        if (dbUser == null) throw new UsernameNotFoundException("账号不存在");

        //这里返回我们自定义UserDetails的实现类
        AuthUser authUser = new AuthUser();
        BeanUtils.copyProperties(dbUser, authUser);

        //根据userId 查询角色信息
        Long id = dbUser.getId();
        List<String> roles = roleMapper.selectByUserId(id);
        authUser.setRoles(roles);
        // 封装返回结果
        return authUser;
    }
}
