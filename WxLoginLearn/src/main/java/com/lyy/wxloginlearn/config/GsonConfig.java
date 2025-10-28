package com.lyy.wxloginlearn.config;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;

/**
 * @author YueYang
 * Created on 2025/10/25 16:10
 * @version 1.0
 * 谷歌处理json数据非常好用，但是jdk9之后
 * Java 9+ 模块系统限制了对 java.time 包的反射访问
 * Gson 尝试通过反射访问 LocalDateTime 的私有字段时被拒绝
 * 这里我们配置一下gson，使其能够访问私有日期时间字段
 */
@Configuration
public class GsonConfig {

    @Bean
    public Gson MyGson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
                        new JsonPrimitive(src.toString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, context) ->
                        LocalDateTime.parse(json.getAsString()))
                .create();
    }
}
