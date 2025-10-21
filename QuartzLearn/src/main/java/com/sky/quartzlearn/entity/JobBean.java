package com.sky.quartzlearn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author YueYang
 * Created on 2025/10/21 12:19
 * @version 1.0
 * 存放任务类的相关信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobBean {

    /**
     * 任务类名字，也是任务的唯一标识
     */
    private String jobName;


    /**
     * 任务类 class对象
     */
    private String jobClass;

    /**
     * cron表达式
     */
    private String cronExpression;


}
