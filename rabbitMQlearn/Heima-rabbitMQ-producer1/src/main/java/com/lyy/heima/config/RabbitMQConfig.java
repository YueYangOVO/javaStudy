package com.lyy.heima.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
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

    @Autowired
    private RabbitTemplate rabbitTemplate;

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


}
