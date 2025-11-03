package com.lyy.config;

import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 消息转换器配置
 * 确保与生产者使用相同的序列化方式
 * 
 * @author YueYang
 * Created on 2025/11/1 17:15
 * @version 1.0
 */
@Configuration
public class RabbitMQConfig {

    /**
     * 配置消息转换器
     * 使用 Jackson2JsonMessageConverter 进行 JSON 序列化和反序列化
     * 必须与生产者端保持一致
     */
    @Bean
    public MessageConverter messageConverter() {
        // 1. 定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();
        
        // 2. 配置自动创建消息id，用于识别不同的消息，也可以在业务中基于id判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        
        return jackson2JsonMessageConverter;
    }
}