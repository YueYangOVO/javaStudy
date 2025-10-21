package com.sky.quartzlearn.config;

import com.sky.quartzlearn.job.MisfireJob;
import com.sky.quartzlearn.job.MyJob;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author YueYang
 * Created on 2025/10/21 10:05
 * @version 1.0
 */
@Configuration
public class QuartzConfig {

    private static final String jobName = "misfireJob";

    /**
     * 配置JobDetail
     */
    @Bean(name = "misfireJob")
    public JobDetail jobDetail() {
        return JobBuilder.newJob(MisfireJob.class)
                .storeDurably()
                .withIdentity("MisfireJob", "group1")
                .usingJobData("count", 1)
                .build();
    }

    /**
     * 配置trigger
     */
    @Bean
    public Trigger trigger(@Qualifier(value = "misfireJob") JobDetail jobDetail) {
        //配置cron表达式
        String cronExpression = "0/5 * * * * ? *"; //每隔2分钟执行一次

        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();

        return TriggerBuilder.newTrigger()
                //让任务从今天的 15:15:00开始执行
//                .startAt(DateBuilder.todayAt(15, 15, 0))
                .withIdentity(jobName + "_trigger")
                .forJob(jobDetail())
                .withSchedule(cronScheduleBuilder)
                .build();
    }

}
