package com.sky.quartzlearn.config;

import com.sky.quartzlearn.job.MyJob;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YueYang
 * Created on 2025/10/21 10:05
 * @version 1.0
 */
// @Configuration
public class QuartzConfig {

    /**
     * 配置JobDetail
     */
    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob(MyJob.class)
                .storeDurably()
                .withIdentity("myJob", "group1")
                .usingJobData("count", 1)
                .build();
    }

    /**
     * 配置trigger
     */
    @Bean
    public Trigger trigger() {
        //配置cron表达式
        String cronExpression = "0/3 * * * * ? *"; //每隔5秒执行一次
        return TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .forJob(jobDetail())
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

}
