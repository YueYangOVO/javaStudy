package com.sky.quartzlearn.controller;

import com.sky.quartzlearn.entity.JobBean;
import com.sky.quartzlearn.job.MyJob;
import com.sky.quartzlearn.job.ScheduleJob;
import com.sky.quartzlearn.utils.JobUtils;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author YueYang
 * Created on 2025/10/21 12:38
 * @version 1.0
 */
@RestController
@RequestMapping("/quartz")
public class QuartzController {
    @Autowired
    private Scheduler scheduler;

    private static final String jobName = "myJob";

    /**
     * 创建定时任务
     *
     * @return 返回响应结果
     */
    @GetMapping("/create")
    public String createJob() {
        String cron = "0/3 * * * * ? *";
        JobBean jobBean = new JobBean(jobName, ScheduleJob.class.getName(), cron);
        JobUtils.createJob(scheduler, jobBean);
        return "任务创建成功";
    }

    /**
     * 暂停定时任务
     */
    @GetMapping("/pause")
    public String pauseJob() {
        JobUtils.pauseJob(scheduler, jobName);
        return "暂停任务成功";
    }


    /**
     * 恢复任务
     */
    @GetMapping("/resume")
    public String resumeJob() {
        JobUtils.resumeJob(scheduler, jobName);
        return "恢复任务成功";
    }

    /**
     * 删除定时任务
     */
    @GetMapping("delete")
    public String deleteJob() {
        JobUtils.deleteJob(scheduler, jobName);
        return "删除任务成功";
    }

    /**
     * 任务执行一次
     */
    @GetMapping("runOnce")
    public String runJobOnce() {
        JobUtils.runJobOnce(scheduler, jobName);
        return "执行一次任务成功";
    }

    /**
     * 修改任务执行频次
     */
    @GetMapping("/modify")
    public String modifyJob() {
        JobBean jobBean = new JobBean(jobName, ScheduleJob.class.getName(), "0/5 * * * * ? *");

        JobUtils.modifyJob(scheduler, jobBean);
        return "任务修改成功";
    }
}
