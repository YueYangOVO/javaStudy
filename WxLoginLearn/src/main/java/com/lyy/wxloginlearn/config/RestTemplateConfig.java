package com.lyy.wxloginlearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author YueYang
 * Created on 2025/10/25 16:29
 * @version 1.0
 * <p>
 * RestTemplate 是 Spring 提供的用于发送 HTTP 请求的客户端工具
 * 可以向其他后端服务器发送请求（如微信 API、第三方服务等）
 * 支持 GET、POST、PUT、DELETE 等 HTTP 方法
 * <p>
 * 不能直接注入：RestTemplate 不是 Spring Boot 自动配置的 Bean
 * 需要手动配置：必须通过 @Bean 注解在配置类中创建 RestTemplate 实例
 * <p>
 * 前提是我们导入了spring web模块
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}
