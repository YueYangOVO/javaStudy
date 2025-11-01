package com.lyy.rabbitMQ.listener;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author YueYang
 * Created on 2025/10/29 11:10
 * @version 1.0
 */
@Component
public class RabbitMqListener2 {
    /**
     * 消费者2 监听 pbq2队列
     */
//    @RabbitListener(queues = "pbq2")
//    public void listenQueue(String msg) {
//        System.out.println("消费者2接收到的消息：" + msg);
//    }

    /**
     * 消费者2 监听 pbq2队列 手动确认
     */
    @RabbitListener(queues = "pbq2")
    public void listenQueue2(Message message, Channel channel) {

        try {
            //打印接收到的消息】
            String msg = new String(message.getBody());
            System.out.println("消费者2接收到的消息：" + msg);
            //打印消息的属性
            //打印路由键
            System.out.println("路由键：" + message.getMessageProperties().getReceivedRoutingKey());
            //打印交换机的名字
            System.out.println("交换机的名字：" + message.getMessageProperties().getReceivedExchange());


            //1. 接受完消息后进行业务处理
            System.out.println("消息接收完毕 开始进行业务处理。。。。");
            //模拟业务异常
            int i = 1 / 0;

            // 2. 手动确认消息 确认成功接收到了消息
            // 注意basicAck方法这个方法能够批量确认消息，需要我们再第二个参数位置设置为true
            //第一个参数：消息的唯一标识，在channel.basicConsume时设置为true
            //第二个参数：是否批量确认，false表示确认当前单条消息，true表示确认所有小于等于当前消息的未确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) { //接收更大的异常
            //打印异常信息
            System.out.println("消费者2处理消息异常：" + e.getMessage());

            // 3. 如果确认消息失败，我们可以选择拒绝消息，第二个参数表示是否批量拒绝消息
            //第三个参数：是否重新入队，true表示重新入队，false表示丢弃消息
            try {
                //消息接收失败，拒绝消息，重新入队
                // channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                // System.out.println("消息已拒绝并重新入队");

                //上面这里不让消息重新入队，不然会陷入消息循环消费，导致死循环。
                //我们选择拒绝消息，让消息进入死信队列当中。
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                System.out.println("消息已拒绝并进入死信队列");

                //直接拒绝消息，不重新入队 这种方式 只能拒绝单条消息,并丢弃消息
                // channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException ex) {
                System.out.println("拒绝消息失败：" + ex.getMessage());
                throw new RuntimeException(ex);
            }
        }
    }


    /**
     * 监听死信队列
     */
    @RabbitListener(queues = "dlxQueue")
    public void listenDlxQueue(Message message, Channel channel) {
        try {
            //打印接收到的消息】
            String msg = new String(message.getBody());
            System.out.println("消费者2接收到的死信消息：" + msg);
            //打印消息的属性
            //打印路由键
            System.out.println("路由键：" + message.getMessageProperties().getReceivedRoutingKey());
            //打印交换机的名字
            System.out.println("交换机的名字：" + message.getMessageProperties().getReceivedExchange());

            //手动确认死信消息 确认成功接收到了消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);

        } catch (Exception e) {
            //打印异常信息
            System.out.println("消费者2处理死信消息异常：" + e.getMessage());
            //死信队列监听器中也出现异常，直接丢弃消息
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }

    }


}


