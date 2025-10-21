package com.sky.quartzlearn.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author YueYang
 * Created on 2025/10/21 16:47
 * @version 1.0
 */
@Component
public class MyTask {

    @Scheduled(cron = "0/3 * * * * ?")
    public void task1() {
        System.out.println("----------------------------------------");
        System.out.println("hello task!");
        System.out.println("run time" + new Date().toLocaleString());
        //打印当前线程的名字
        System.out.println("Thread.currentThread() = " + Thread.currentThread());
        System.out.println("========================================");
    }

    @Scheduled(cron = "0/3 * * * * ?")
    public void task2() {
        System.out.println("----------------------------------------");
        System.out.println("你好 任务2");
        System.out.println("run time" + new Date().toLocaleString());
        //打印当前线程的名字
        System.out.println("Thread.currentThread() = " + Thread.currentThread());
        System.out.println("========================================");
    }
}
