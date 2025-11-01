package com.lyy.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author YueYang
 * Created on 2025/10/28 20:23
 * @version 1.0
 */
@Component
public class RabbitMqListener {

    /**
     * 消费者1 监听 pdq1队列
     */
    @RabbitListener(queues = "pbq1")
    public void listenQueue(String msg) {
        System.out.println("消费者1接收到的消息：" + msg);
    }
}
