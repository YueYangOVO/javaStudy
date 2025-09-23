package com.lyy.security.mapper;

import com.lyy.security.entity.pojo.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 角色表 Mapper 接口
 * </p>
 *
 * @author YueYang
 * @since 2025-09-22
 */
public interface RoleMapper extends BaseMapper<Role> {

    //查询用户的角色信息 根据userId
    List<String> selectByUserId(Long id);
}
