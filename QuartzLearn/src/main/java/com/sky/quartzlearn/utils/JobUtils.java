package com.sky.quartzlearn.utils;

import com.sky.quartzlearn.entity.JobBean;
import org.quartz.*;

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


}
