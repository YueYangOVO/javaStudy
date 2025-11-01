package com.lyy.config;


import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author YueYang
 * Created on 2025/10/28 20:00
 * @version 1.0
 * 在当前文件中创建交换机和队列 以及交换机和队列之间的绑定
 */
@Configuration
public class RabbitMQConfig {


    //配置 RabbitTemplate 让消息默认持久化
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        //通过构造方法设置消息转换器
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

        //设置消息默认持久化
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        //移除事务模式，避免和消息确认机制冲突
        // rabbitTemplate.setChannelTransacted(true);

        //开启发布确认
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                System.out.println("消息发送成功");
            } else {
                System.err.println("消息发送失败 --" + ", 原因: " + cause);
            }
        });

        //开启返回确认
        rabbitTemplate.setReturnsCallback(returned -> {
            System.err.println("消息返回: " + returned.getMessage() +
                    ", 回复码: " + returned.getReplyCode() +
                    ", 回复文本: " + returned.getReplyText() +
                    ", 交换机: " + returned.getExchange() +
                    ", 路由键: " + returned.getRoutingKey());
        });

        //设置默认消息属性持久化
        rabbitTemplate.setBeforePublishPostProcessors(message -> {
            message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            return message;
        });

        return rabbitTemplate;
    }


    //普通模式练习
    /*//交换机名字
    public static final String EXCHANGE_NAME = "adminExchange";
    //队列名字
    public static final String QUEUE_NAME = "adminQueue";

    //1. 配置交换机 如果不配置使用默认交换机
    @Bean()
    public Exchange topicExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NAME).durable(true).build();
    }

    //2. 配置队列
    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME).build();
    }


    //3.绑定队列和交换机
    @Bean
    public Binding binding(@Qualifier("queue") Queue queue, @Qualifier("topicExchange") Exchange topicExchange) {
        return BindingBuilder.bind(queue).to(topicExchange).with(QUEUE_NAME).noargs();
    }*/

    //发布订阅模式练习
/*    //发布订阅使用的时 FanoutExchange 配置交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanoutExchange");
    }

    //绑定队列1和交换机
    @Bean
    public Binding bindingPbQ1(@Qualifier("pbQ1") Queue pbQ1, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(pbQ1).to(fanoutExchange);
    }


    //绑定队列2和交换机
    @Bean
    public Binding bindingPbQ2(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("fanoutExchange") FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(pbQ2).to(fanoutExchange);
    }*/

/*    //路由模式交换机和队列绑定
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("directExchange");
    }

    //绑定队列1和direct路由交换机 路由键为 routeKey1
    @Bean
    public Binding bindingPbQ1ToRouteKey1(@Qualifier("pbQ1") Queue pbQ1, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(pbQ1).to(directExchange).with("routingKey1");
    }

    //绑定队列2和direct路由交换机 路由键为 routingKey1
    @Bean
    public Binding bindingPbQ2ToRouteKey1(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(pbQ2).to(directExchange).with("routingKey1");
    }

    //绑定队列2和direct路由交换机 路由键为 routingKey2
    @Bean
    public Binding bindingPbQ2ToRouteKey2(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(pbQ2).to(directExchange).with("routingKey2");
    }

    //绑定队列2和direct路由交换机 路由键为 routingKey3
    @Bean
    public Binding bindingPbQ2ToRouteKey3(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("directExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(pbQ2).to(directExchange).with("routingKey3");
    }*/


    //topics模式
    //声明交换机 交换机持久化
    @Bean
    public TopicExchange topicExchange() {
        return ExchangeBuilder.topicExchange("topicExchange").durable(true).build();
    }

    //绑定队列pbQ1 使用通配符
    @Bean
    public Binding bindingPbQ1ToTopicExchange1(@Qualifier("pbQ1") Queue pbQ1, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(pbQ1).to(topicExchange).with("*.key.*");
    }

    //绑定队列pbQ1使用通配符 #匹配0个或多个单词 .匹配一个单词
    @Bean
    public Binding bindingPbQ1ToTopicExchange2(@Qualifier("pbQ1") Queue pbQ1, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(pbQ1).to(topicExchange).with("#.key.a.*");
    }

    //绑定队列pbQ2使用通配符 #匹配0个或多个单词 .匹配一个单词
    @Bean
    public Binding bindingPbQ2ToTopicExchange(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(pbQ2).to(topicExchange).with("*.key.b.#");
    }

    //绑定队列pbQ2使用通配符 #匹配0个或多个单词 .匹配一个单词
    @Bean
    public Binding bindingPbQ2ToTopicExchange2(@Qualifier("pbQ2") Queue pbQ2, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(pbQ2).to(topicExchange).with("#.key.b.*");
    }


    //生成队列1
    @Bean
    public Queue pbQ1() {
        return QueueBuilder.durable("pbq1").build();
    }

    //生成队列2
 /*   @Bean
    public Queue pbQ2() {
        return QueueBuilder.durable("pbq2").build();
    }*/

    //配置死信交换机和死信队列
    //1.死信交换机
    @Bean
    public DirectExchange dlxExchange() {
        return new DirectExchange("dlxExchange", true, false);
    }

    //2.死信队列
    @Bean
    public Queue dlxQueue() {
        return QueueBuilder.durable("dlxQueue").build();
    }

    //3.死信交换机绑定死信队列
    @Bean
    public Binding bindingDlxQueueToDlxExchange(@Qualifier("dlxQueue") Queue dlxQueue, @Qualifier("dlxExchange") DirectExchange dlxExchange) {
        return BindingBuilder.bind(dlxQueue).to(dlxExchange).with("dlxRoutingKey");
    }

    //4. 队列pbq2绑定死信交换机 也就是新的pbq2队列 之前呢个注释掉了
    //还有就是这里声明pbq2队列时，当pbq2消息被循环消费时让这些消息进入死信队列当中。
    @Bean
    public Queue pbQ2() {
        //注意死信交换机这里我使用的是路由交换机 直接绑定路由键即可。
        Map<String, Object> args = new HashMap<>();
        //设置队列的死信交换机
        args.put("x-dead-letter-exchange", "dlxExchange");
        //设置队列的死信路由键
        args.put("x-dead-letter-routing-key", "dlxRoutingKey");

        return new Queue("pbq2", true, false, false, args);
    }

    //创建队列q3 用来测试队列过期时间
    @Bean
    public Queue pbQ3() {
        //设置该队列中的消息十秒后过期
        Map<String, Object> args = new HashMap<>();
        args.put("x-message-ttl", 10000);
        //设置队列的死信交换机
        args.put("x-dead-letter-exchange", "dlxExchange");
        //设置队列的死信路由键
        args.put("x-dead-letter-routing-key", "dlxRoutingKey");
        return new Queue("pbq3", true, false, false, args);
    }

    //pbq3队列绑定业务交换机topicExchange 路由键为 key.a.#
    @Bean
    public Binding bindingPbQ3ToTopicExchange(@Qualifier("pbQ3") Queue pbQ3, @Qualifier("topicExchange") TopicExchange topicExchange) {
        return BindingBuilder.bind(pbQ3).to(topicExchange).with("key.a.#");
    }


}