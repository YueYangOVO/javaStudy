package com.lyy.security.service.impl;

import com.lyy.security.entity.pojo.User;
import com.lyy.security.mapper.UserMapper;
import com.lyy.security.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author YueYang
 * @since 2025-09-22
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
