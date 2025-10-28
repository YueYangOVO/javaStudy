package com.lyy.wxloginlearn.config;

import io.swagger.annotations.Api;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author YueYang
 * Created on 2025/10/25 17:21
 * @version 1.0
 */
@Configuration
public class SwaggerConfig {

    /**
     * 配置Swagger的相关信息
     */
    @Bean
    public Docket docket() {
        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("微信登录学习接口文档")
                .description("这是一个用于学习微信登录的接口文档")
                .version("1.0")
                .build();

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                //指定生成接口的位置 这里是controller包下的所有类
                .apis(RequestHandlerSelectors.basePackage("com.lyy.wxloginlearn.controller"))
                .paths(PathSelectors.any())
                .build();
    }
}
