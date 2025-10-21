package com.sky.quartzlearn.job;

import org.quartz.*;

/**
 * @author YueYang
 * Created on 2025/10/21 12:48
 * @version 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class ScheduleJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        System.out.println("schedule Job start .........................");

        //获取任务的上下文环境
        JobDetail jobDetail = context.getJobDetail();

        System.out.println("jobDetail.getKey().getName() = " + jobDetail.getKey().getName());
        System.out.println("jobDetail.getJobClass().getName() = " + jobDetail.getJobClass().getName());

        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        String month = (String) jobDataMap.get("month");
        System.out.println("周" + month + "执行");
        jobDataMap.put("month", "-" + month + "-");

        System.out.println("schedule Job end =====================================");
    }
}
