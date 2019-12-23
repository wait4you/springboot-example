package com.iyou.quartz.controller;

import com.iyou.quartz.application.SchedulerService;
import com.iyou.quartz.controller.request.SchedulerRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.matchers.GroupMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-08-09 20:16
 * @modifyDate 2019-08-09 20:16
 */
@Slf4j
@Api(tags = "定时任务调度", value = "操作API")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@RestController
@RequestMapping("/scheduler")
public class SchedulerController {
    private final Scheduler scheduler;
    private final SchedulerService schedulerService;

    @ApiOperation("添加｜更新任务")
    @PostMapping("/job")
    public boolean addOrUpdate(SchedulerRequest schedulerRequest) throws ParseException, SchedulerException, ClassNotFoundException {
        log.debug("添加｜更新任务: {}", schedulerRequest);
        return schedulerService.addOrUpdate(schedulerRequest);
    }

    @ApiOperation("添加｜更新任务（多个）")
    @PostMapping("/jobs")
    public boolean addOrUpdates(List<SchedulerRequest> schedulerRequests) throws SchedulerException {
        log.debug("添加｜更新任务: {}", schedulerRequests);
        return schedulerService.addOrUpdates(schedulerRequests);
    }


    @ApiOperation("删除任务")
    @DeleteMapping("/job-key")
    public Object deleteJob(String group, String name) throws SchedulerException {
        JobKey jobKey = JobKey.jobKey(name, group);
        log.debug("删除任务: {}", jobKey);
        return scheduler.deleteJob(jobKey);
    }

    @ApiOperation("删除任务（全部）")
    @DeleteMapping("/job-keys")
    public boolean deleteJobs() throws SchedulerException {
        log.debug("删除任务（全部）");
        scheduler.clear();
        return true;
    }

    @ApiOperation("恢复任务")
    @PutMapping("/resume-job")
    public boolean resumeJob(JobKey jobKey) throws SchedulerException {
        log.debug("恢复任务: {}", jobKey);
        scheduler.resumeJob(jobKey);
        return true;
    }

    @ApiOperation("恢复任务（多个）")
    @PutMapping("/resume-jobs")
    public boolean resumeJobs(GroupMatcher<JobKey> jobKeys) throws SchedulerException {
        log.debug("恢复任务: {}", jobKeys);
        scheduler.resumeJobs(jobKeys);
        return true;
    }

    @ApiOperation("恢复任务（全部）")
    @PutMapping("/resume-all")
    public boolean resumeAll() throws SchedulerException {
        log.debug("恢复任务（全部）");
        scheduler.resumeAll();
        return true;
    }


    @ApiOperation("暂停任务（某个）")
    @PutMapping("/pause-job")
    public boolean pauseJob(JobKey jobKey) throws SchedulerException {
        log.debug("暂停任务: {}", jobKey);
        scheduler.pauseJob(jobKey);
        return true;
    }

    @ApiOperation("暂停任务（多个）")
    @PutMapping("/pause-jobs")
    public boolean pauseJobs(GroupMatcher<JobKey> jobKeys) throws SchedulerException {
        log.debug("暂停任务：{}", jobKeys);
        scheduler.pauseJobs(jobKeys);
        return true;
    }

    @ApiOperation("暂停任务（全部）")
    @PutMapping("/pause-all")
    public boolean pauseAll() throws SchedulerException {
        log.debug("暂停任务（全部）");
        scheduler.pauseAll();
        return true;
    }

    @ApiOperation("挂起调度")
    @PutMapping("/standby")
    public boolean standby() throws SchedulerException {
        log.debug("挂起调度");
        scheduler.standby();
        return true;
    }

    @ApiOperation("开始调度")
    @PutMapping("/start")
    public boolean start() throws SchedulerException {
        log.debug("开始调度");
        scheduler.start();
        return true;
    }

    @ApiOperation("关闭调度")
    @PutMapping("/shutdown")
    public boolean shutdown() throws SchedulerException {
        log.debug("关闭调度");
        scheduler.shutdown();
        return true;
    }
}