package com.sky.quartzlearn.timer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author YueYang
 * Created on 2025/10/21 16:37
 * @version 1.0
 * 练习java的timer定时器
 */
public class MyTimer {

    public static void main(String[] args) throws ParseException {

        //创建timer对象
        Timer timer = new Timer();

        //实现定时任务
        String str = "2025-10-21 16:40:00";
        Date start = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(str);

//        //在指定时间内开启定时任务
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                System.out.println("hello timer!");
//                System.out.println("run time：" + new Date().toLocaleString());
//            }
//        }, start);

        //开启定时任务 每隔2秒执行一次 延迟时间0秒 每隔2秒执行一次
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("hello timer!");
                System.out.println("run time：" + new Date().toLocaleString());
            }
        }, 0, 2000);


    }

}
