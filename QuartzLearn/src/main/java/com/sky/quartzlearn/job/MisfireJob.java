package com.sky.quartzlearn.job;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author YueYang
 * Created on 2025/10/21 15:02
 * @version 1.0
 * <p>
 * 这个主要用来测试错过任务的补偿机制
 */
public class MisfireJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        //获取任务执行的上下文详情
        JobDetail jobDetail = context.getJobDetail();

        //获取任务的名字
        String jobName = jobDetail.getKey().getName();
        //获取任务本次执行时间
        Date fireTime = context.getFireTime();
        String formatFireTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(fireTime);

        //输出任务的相关信息
        System.out.println("--------------------------" + jobName + "--------------------------");
        System.out.println("\t\t本次任务执行时间: " + formatFireTime);

    }
}
