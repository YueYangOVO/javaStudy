package com.lyy.wxloginlearn.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import java.time.LocalDateTime;
import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 微信小程序用户表
 * </p>
 *
 * @author YueYang
 * @since 2025-10-25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value = "WxUser对象", description = "微信小程序用户表")
@Builder
public class WxUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id值")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "微信小程序用户手机号")
    private String phone;

    @ApiModelProperty(value = "微信小程序用户access_token")
    private String accessToken;

    @ApiModelProperty(value = "微信小程序用户token")
    private String token;

    @ApiModelProperty(value = "微信小程序用户openid")
    private String openid;

    @ApiModelProperty(value = "微信小程序用户昵称")
    private String nickname;

    @ApiModelProperty(value = "微信小程序用户头像")
    private String avatar;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;


}
