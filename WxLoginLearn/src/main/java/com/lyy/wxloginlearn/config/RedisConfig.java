package com.lyy.wxloginlearn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author YueYang
 * Created on 2025/10/25 18:45
 * @version 1.0
 */
@Configuration
public class RedisConfig {

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //设置连接工厂
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置key的序列化器
        //这里设置大key用字符串序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //设置value的序列化器 这里我们用json序列化
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        //hash的值是键值对分开的，单独设置序列化
        //hash key的序列化器
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //hash value的序列化器
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());

        //初始化
        redisTemplate.afterPropertiesSet();
        return redisTemplate;

    }

}
