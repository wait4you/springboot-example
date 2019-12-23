package com.iyou.quartz.job;

import com.iyou.quartz.annotation.QuartzCronScheduled;
import lombok.extern.slf4j.Slf4j;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalTime;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-11-23 14:10
 * @modifyDate 2019-11-23 14:10
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@QuartzCronScheduled(
        jobName = "MyJob3",
        jobGroup = "com.iyou.quartz",
        cronExpression = "*/6 * * * * ?")
public class MyJob3 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("MyJob3 ----> {}", LocalTime.now());
    }
}
