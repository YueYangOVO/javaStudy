package com.lyy.security.service.impl;

import com.lyy.security.entity.pojo.Permission;
import com.lyy.security.mapper.PermissionMapper;
import com.lyy.security.service.IPermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author YueYang
 * @since 2025-09-22
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
