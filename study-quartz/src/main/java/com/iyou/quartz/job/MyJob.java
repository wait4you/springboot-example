package com.iyou.quartz.job;

import com.iyou.quartz.annotation.QuartzCronScheduled;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalTime;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-08-08 02:11
 * @modifyDate 2019-08-08 02:11
 */
@Slf4j
// @PersistJobDataAfterExecution
// @DisallowConcurrentExecution
@QuartzCronScheduled(
        jobName = "MyJob",
        jobGroup = "com.iyou.quartz",
        cronExpression = "*/3 * * * * ?")
public class MyJob extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("MyJob ----> {}", LocalTime.now());
    }
}
