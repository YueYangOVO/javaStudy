package com.lyy.wxloginlearn.mapper;

import com.lyy.wxloginlearn.entity.pojo.WxUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 微信小程序用户表 Mapper 接口
 * </p>
 *
 * @author YueYang
 * @since 2025-10-25
 */
@Mapper
public interface WxUserMapper extends BaseMapper<WxUser> {

}
