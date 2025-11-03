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

    //===================监听普通交换机和普通队列 控制台自带的=============
    //监听test.q1队列中的消息 用来测是普通的消息发送接收
    @RabbitListener(queues = "test.q1")
    public void listenTestQ1(String msg) {
        //注意测试类中发送的字符串消息会自动转化为object类型
        //我们这里接收的时候也会自动转化为字符串类型
        System.out.println("消费者1 收到test.q1队列中的消息：【" + msg + "】");
    }

    //==================工作队列模式=====================

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


    //==================fanout 发布订阅模式=====================

    /**
     * 练习使用fanout交换机
     * 这里创建两个队列 fanout.queue1  fanout.queue2
     * 用来检测fanout交换机的消息分发
     * 注意这里的队列要在mq服务器中存在，
     * 两种创建队列交换机方式，
     * 1通过控制台手动创建，这样我们就不用在生产者配置类中创建。
     * 2通过代码创建，在生产者配置类中创建交换机和队列，
     * 并绑定交换机和队列，设置路由键。
     */
    @RabbitListener(queues = "fanout.queue1")
    public void listenFanoutQueue1(String msg) {
        System.out.println("消费者1 收到fanout.queue1队列中的消息：【" + msg + "】");
    }

    /**
     * 练习使用fanout交换机
     * 这里创建两个队列 fanout.queue1  fanout.queue2
     * 用来检测fanout交换机的消息分发
     */
    @RabbitListener(queues = "fanout.queue2")
    public void listenFanoutQueue2(String msg) {
        System.out.println("消费者2 收到fanout.queue2队列中的消息：【" + msg + "】");
    }

    //==================direct 路由模式=====================

    /**
     * 监听direct.queue1 中的消息，路由键是red，blue
     */
    @RabbitListener(queues = "direct.queue1")
    public void listenDirectQueue1RedAndBlue(String msg) {
        System.out.println("消费者1 监听direct.queue1 队列中消息，路由键red和blue，消息是:" + msg);
    }

    /**
     * 监听direct.queue2 中的消息，路由键是blue
     */
    @RabbitListener(queues = "direct.queue2")
    public void listenDirectQueue1Blue(String msg) {
        System.out.println("消费者2 监听direct.queue2 队列中消息，路由键blue，消息是:" + msg);
    }


}
