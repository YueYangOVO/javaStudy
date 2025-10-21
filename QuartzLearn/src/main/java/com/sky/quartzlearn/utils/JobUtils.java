package com.sky.quartzlearn.utils;

import com.sky.quartzlearn.entity.JobBean;
import org.quartz.*;

import java.net.Socket;

/**
 * @author YueYang
 * Created on 2025/10/21 12:23
 * @version 1.0
 */
public class JobUtils {

    /**
     * 创建一个定时任务
     *
     * @param scheduler 定时任务调度器
     * @param jobBean   任务类的bean对象
     */
    public static void createJob(Scheduler scheduler, JobBean jobBean) {
        Class<? extends Job> jobClass = null;
        JobDetail jobDetail = null;
        Trigger trigger = null;

        try {
            //获取当前任务类的class对象
            jobClass = (Class<? extends Job>) Class.forName(jobBean.getJobClass());
            //创建任务详情对象
            jobDetail = JobBuilder.newJob(jobClass)
                    .storeDurably()
                    .withIdentity(jobBean.getJobName())
                    .usingJobData("month", "January")
                    .build();

            //创建触发器对象
            trigger = TriggerBuilder.newTrigger()
                    .forJob(jobDetail)
                    .withSchedule(CronScheduleBuilder.cronSchedule(jobBean.getCronExpression()))
                    .withIdentity(jobBean.getJobName() + "_trigger")
                    .build();

            //通过调度器 创建任务
            try {
                scheduler.scheduleJob(jobDetail, trigger);
            } catch (SchedulerException e) {
                throw new RuntimeException(e);
            }


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停任务
     *
     * @param scheduler 调度器对象
     * @param jobName   任务类唯一标识
     */
    public static void pauseJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.pauseJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 恢复任务
     *
     * @param scheduler 调度器对象
     * @param jobName   任务类唯一标识
     */
    public static void resumeJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.resumeJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除任务
     *
     * @param scheduler 调度器对象
     * @param jobName   任务类唯一标识
     */
    public static void deleteJob(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.deleteJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 任务执行一次
     */
    public static void runJobOnce(Scheduler scheduler, String jobName) {
        JobKey jobKey = JobKey.jobKey(jobName);

        try {
            scheduler.triggerJob(jobKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * 修改定时任务 主要是修改定时任务的执行时间，执行频率在触发器当中
     */
    public static void modifyJob(Scheduler scheduler, JobBean jobBean) {
        //获取触发器的唯一标识
        TriggerKey triggerKey = TriggerKey.triggerKey(jobBean.getJobName() + "_trigger");

        try {
            //通过触发器的唯一标识获取触发器对象
            CronTrigger oldTrigger = (CronTrigger) scheduler.getTrigger(triggerKey);
            //使用新的触发cron表达式
            String cronExpression = jobBean.getCronExpression();
            //通过老的触发器构建新的触发器, jobDetail还是老的
            CronTrigger newTrigger = oldTrigger.getTriggerBuilder()
                    //withMisfireHandlingInstructionDoNothing()方法不在执行错过的定时任务
                    //默认会执行一次错过的定时任务
                    .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing())
                    .build();

            //调度器更新任务的触发器
            scheduler.rescheduleJob(triggerKey, newTrigger);

        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }


    }


}
