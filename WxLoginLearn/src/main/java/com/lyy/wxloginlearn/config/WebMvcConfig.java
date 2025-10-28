package com.lyy.wxloginlearn.config;

import com.lyy.wxloginlearn.interceptor.RequestHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author YueYang
 * Created on 2025/10/25 17:12
 * @version 1.0
 * 在这里注册拦截器
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    //注入请求拦截器
    @Autowired
    private RequestHandler requestHandler;

    /**
     * 注册拦截器
     *
     * @param registry 拦截器注册器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册拦截器 放行登录请求 拦截其他所有请求
        registry.addInterceptor(requestHandler)
                .addPathPatterns("/**") //拦截所有请求
                .excludePathPatterns("/wx-user/login")//放行登录请求
                .excludePathPatterns("/upload/**")//放行上传请求
        ;
    }

    /**
     * 配置静态资源的访问路径
     *
     * @param registry 静态资源注册器
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("配置静态资源的访问路径 允许访问swagger静态资源");

        //配置doc.html的访问路径 这里是将所有 /doc.html 路径下的资源映射到 classpath:/META-INF/resources/ 目录下
        registry.addResourceHandler("/doc.html")
                .addResourceLocations("classpath:/META-INF/resources/");

        //配置webjars的访问路径 这里是将所有 /webjars/** 路径下的资源映射到 classpath:/META-INF/resources/webjars/ 目录下
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }


}
