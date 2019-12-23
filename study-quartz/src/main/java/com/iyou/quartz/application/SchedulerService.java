package com.iyou.quartz.application;

import com.iyou.quartz.controller.request.SchedulerRequest;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.*;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-08-10 09:34
 * @modifyDate 2019-08-10 09:34
 */
@Slf4j
@Service
public class SchedulerService {
    private final Scheduler scheduler;

    public SchedulerService(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public boolean addOrUpdate(SchedulerRequest schedulerRequest) throws SchedulerException, ClassNotFoundException, ParseException {
        // 设置 JobDetail
        JobDetail jobDetail = createJobDetail(schedulerRequest);
        // 设置 Trigger
        Trigger trigger = createTrigger(schedulerRequest);
        // 更新或添加
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerRequest.getJobName(), schedulerRequest.getJobGroup());
        JobKey jobKey = JobKey.jobKey(schedulerRequest.getJobName(), schedulerRequest.getJobGroup());
        if (null == scheduler.getTrigger(triggerKey)) { // 添加
            scheduler.scheduleJob(jobDetail, trigger);
        } else { // 更新
            scheduler.rescheduleJob(triggerKey, trigger);
        }
        return true;
    }

    public boolean addOrUpdates(List<SchedulerRequest> schedulerRequests) throws SchedulerException {
        Map<JobDetail, Set<? extends Trigger>> triggersAndJobs = new HashMap<>();
        schedulerRequests.forEach(schedulerRequest -> {
            try {
                JobDetail jobDetail = createJobDetail(schedulerRequest);

                Set<Trigger> triggers = new LinkedHashSet<>();
                triggers.add(createTrigger(schedulerRequest));

                triggersAndJobs.put(jobDetail, triggers);
            } catch (ClassNotFoundException | ParseException e) {
                log.error("批量添加triggersAndJobs异常", e);
            }
        });
        scheduler.scheduleJobs(triggersAndJobs, true);
        return true;
    }

    @SuppressWarnings("unchecked") // 确保 jobClass 实现了 QuartzJobBean 接口
    private JobDetail createJobDetail(SchedulerRequest schedulerRequest) throws ClassNotFoundException {
        JobKey jobKey = JobKey.jobKey(schedulerRequest.getJobName(), schedulerRequest.getJobGroup());
        Class jobClass = Class.forName(schedulerRequest.getJobClassName());
        if (!QuartzJobBean.class.isAssignableFrom(jobClass)) { // 判断jobClass是否是QuartzJobBean的子类
            throw new ClassCastException("Job Class " + jobClass.getName() + "没有继承QuartzJobBean类");
        }

        return JobBuilder.newJob()
                .ofType(jobClass)
                .withIdentity(jobKey)
                .withDescription(schedulerRequest.getDescription())
                .storeDurably(schedulerRequest.isDurability()) // default true
                .requestRecovery(schedulerRequest.isRequestsRecovery())  // default true
                .build();
                // TODO: 2019-11-23 isNonConcurrent
                // TODO: 2019-11-23 isUpdateData
    }

    private Trigger createTrigger(SchedulerRequest schedulerRequest) throws ParseException, ClassNotFoundException {
        // CronExpression
        String cronExpression = schedulerRequest.getCronExpression();
        CronExpression.validateExpression(cronExpression);
        CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);
        // Trigger
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerRequest.getJobName(), schedulerRequest.getJobGroup());

        return TriggerBuilder.newTrigger()
                .withIdentity(triggerKey)
                .withSchedule(cronScheduleBuilder)
                .withDescription(schedulerRequest.getDescription())
                .withPriority(schedulerRequest.getPriority())
                .build();
    }

    private JobDataMap getJobDataMap(JobDetail jobDetail) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobDetail", jobDetail);
        return jobDataMap;
    }
}
