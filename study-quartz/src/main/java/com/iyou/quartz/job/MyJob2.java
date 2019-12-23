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
 * @createDate 2019-08-11 07:12
 * @modifyDate 2019-08-11 07:12
 */
@Slf4j
@PersistJobDataAfterExecution
@DisallowConcurrentExecution
@QuartzCronScheduled(
        jobName = "MyJob2",
        jobGroup = "com.iyou.quartz",
        cronExpression = "*/6 * * * * ?")
public class MyJob2 extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("MyJob2 ----> {}", LocalTime.now());
    }
}
