package com.lyy.heima.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;


/**
 * @author YueYang
 * Created on 2025/11/1 17:15
 * @version 1.0
 */
@Slf4j
@Configuration
public class RabbitMQConfig {

    // =================== 练习使用 fanout交换机 ===================

    /**
     * 创建fanout交换机
     * 交换机名称 hmall.fanout
     * 绑定队列 fanout.queue1  fanout.queue2
     */

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("hmall.fanout");
    }

    /**
     * 创建fanout.queue1队列
     */
    @Bean
    public Queue fanoutQueue1() {
        return new Queue("fanout.queue1");
    }

    /**
     * 创建fanout.queue2队列
     */
    @Bean
    public Queue fanoutQueue2() {
        return new Queue("fanout.queue2");
    }

    /**
     * 绑定fanout.queue1队列到hmall.fanout交换机
     */
    @Bean
    public Binding bindingFanoutQueue1() {
        return BindingBuilder.bind(fanoutQueue1()).to(fanoutExchange());
    }

    /**
     * 绑定fanout.queue2队列到hmall.fanout交换机
     */
    @Bean
    public Binding bindingFanoutQueue2() {
        return BindingBuilder.bind(fanoutQueue2()).to(fanoutExchange());
    }

    // =================== 练习使用 direct交换机 ===================

    /**
     * 创建direct交换机
     * 交换机名称 hmall.direct
     * 绑定队列 direct.queue1  direct.queue2
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("hmall.direct");
    }

    /**
     * 创建direct.queue1队列
     */
    @Bean
    public Queue directQueue1() {
        return new Queue("direct.queue1");
    }

    /**
     * 创建direct.queue2队列
     */
    @Bean
    public Queue directQueue2() {
        return new Queue("direct.queue2");
    }

    /**
     * 绑定direct.queue1队列到hmall.direct交换机
     * 路由键 red
     */
    @Bean
    public Binding bindingDirectQueue1() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("red");
    }

    /**
     * 绑定direct.queue1队列到hmall.direct交换机
     * 路由键 blue
     */
    @Bean
    public Binding bindingDirectQueue2() {
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("blue");
    }

    /**
     * 绑定direct.queue2队列到hmall.direct交换机
     * 路由键 blue
     */
    @Bean
    public Binding bindingDirectQueue3() {
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("blue");
    }

    // ----------------------练习使用topic交换机---------------------------

    /**
     * 创建topic交换机
     */
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("hmall.topic");
    }

    /**
     * 创建队列1
     */
    @Bean
    public Queue topicQueue1() {
        return new Queue("topic.queue1");
    }

    /**
     * 创建队列2
     */
    @Bean
    public Queue topicQueue2() {
        return new Queue("topic.queue2");
    }

    /**
     * 使用通配符路由键绑定队列1和topic交换机
     */
    @Bean
    public Binding bindingTopicQueue1() {
        return BindingBuilder.bind(topicQueue1()).to(topicExchange()).with("*.red.#");
    }

    /**
     * 使用通配符路由键绑定队列2和topic交换机
     */
    @Bean
    public Binding bindingTopicQueue2() {
        return BindingBuilder.bind(topicQueue2()).to(topicExchange()).with("*.blue");
    }

    //=================配置消息转化器 用来序列化反序列化json格式消息======================

    /**
     * 配置消息转化器，不使用默认的jdk消息转化器
     * jdk消息转化器会先将消息序列话字节，然后反序列化对象。
     * 使用我们自己配置的转化器 以json形式 序列化和反序列化
     */
    @Bean
    public MessageConverter messageConverter() {
        //1.定义消息转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

        //2.配置自动创建消息id，用于识别不同的消息，也可以在业务中基于id判断是否是重复消息
        jackson2JsonMessageConverter.setCreateMessageIds(true);
        return jackson2JsonMessageConverter;
    }

    /**
     * 创建序列化队列
     */
    @Bean
    public Queue objectQueue() {
        return new Queue("objectQueue");
    }


}
