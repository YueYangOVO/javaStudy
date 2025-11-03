package com.lyy.listener;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author YueYang
 * Created on 2025/11/1 17:15
 * @version 1.0
 */
@Component
public class MqListener {

    //监听test.q1队列中的消息 用来测是普通的消息发送接收
    @RabbitListener(queues = "test.q1")
    public void listenTestQ1(String msg) {
        //注意测试类中发送的字符串消息会自动转化为object类型
        //我们这里接收的时候也会自动转化为字符串类型
        System.out.println("消费者1 收到test.q1队列中的消息：【" + msg + "】");
    }

    /**
     * 练习使用work queue模式
     * 一个队列 多个消费者
     * 监听work.queue队列中的消息
     * 虚拟主机是 hmall
     */
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue1(String msg) {
        System.out.println("消费者1 收到work.queue队列中的消息：【" + msg + "】");
        //让他休眠20毫秒
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 练习使用work queue模式
     * 一个队列 多个消费者
     * 监听work.queue队列中的消息
     * 虚拟主机是 hmall
     */
    @RabbitListener(queues = "work.queue")
    public void listenWorkQueue2(String msg) {
        System.err.println("消费者2 .......收到work.queue队列中的消息：【" + msg + "】");
        //让他休眠20毫秒
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
