package com.sky.quartzlearn.job;

import org.quartz.*;


/**
 * @author YueYang
 * Created on 2025/10/21 10:02
 * @version 1.0
 */
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
public class MyJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        //获取任务详情对象
        JobDetail jobDetail = context.getJobDetail();

        //输出内容
        System.out.println("jobDetail.getKey().getName() = " + jobDetail.getKey().getName()); //任务名字
        System.out.println("jobDetail.getKey().getGroup() = " + jobDetail.getKey().getGroup());//任务组名字
        System.out.println("jobDetail.getJobClass().getName() = " + jobDetail.getJobClass().getName());//当前任务类的包名字
        System.out.println("context.getFireTime() = " + context.getFireTime());//当前任务的执行时间
        System.out.println("context.getNextFireTime() = " + context.getNextFireTime());//下次任务的执行时间


        //输出count的值
        JobDataMap jobDataMap = jobDetail.getJobDataMap();
        Integer count = (Integer) jobDataMap.get("count");
        System.out.println("第" + count + "次执行");
        jobDataMap.put("count", ++count);

        //休眠3秒
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("===================================");
    }
}
