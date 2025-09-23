package com.lyy.security.service.impl;

import com.lyy.security.entity.pojo.Role;
import com.lyy.security.mapper.RoleMapper;
import com.lyy.security.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author YueYang
 * @since 2025-09-22
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

}
