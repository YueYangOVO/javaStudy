package com.lyy.security.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author YueYang
 * Created on 2025/9/22 10:26
 * @version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthUser implements UserDetails, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //获取用户角色的使用权限
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String role : this.roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }

        return authorities;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return this.loginPwd;
    }

    @Override
    public String getUsername() {
        return this.loginAct;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNoExpired != 0;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNoLocked != 0;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNoExpired != 0;
    }

    @Override
    public boolean isEnabled() {
        return this.accountEnabled != 0;
    }

    private Long id;

    @ApiModelProperty(value = "登录账号")
    private String loginAct;

    @JsonIgnore
    @ApiModelProperty(value = "登录密码")
    private String loginPwd;


    @ApiModelProperty(value = "账户是否没有过期，0已过期 1正常")
    private Integer accountNoExpired;

    @ApiModelProperty(value = "密码是否没有过期，0已过期 1正常")
    private Integer credentialsNoExpired;

    @ApiModelProperty(value = "账号是否没有锁定，0已锁定 1正常")
    private Integer accountNoLocked;

    @ApiModelProperty(value = "账号是否启用，0禁用 1启用")
    private Integer accountEnabled;

    private List<String> roles;

}
