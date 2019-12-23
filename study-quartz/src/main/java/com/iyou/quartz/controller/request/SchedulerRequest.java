package com.iyou.quartz.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.annotation.WebInitParam;
import javax.websocket.server.PathParam;

/**
 * @author zhang.hp
 * @version 1.0
 * @description quartz DetailJobAndTrigger请求实体类
 * @createDate 2019-08-08 02:52
 * @modifyDate 2019-08-08 02:52
 */
@ApiModel(value = "定时任务请求接口", description = "添加｜更新")
@Data
@Accessors(chain = true)
public class SchedulerRequest {
    // 任务作用的类名
    @ApiParam(defaultValue = "com.iyou.quartz.job.")
    private String jobClassName;

    // cron表达式
    private String cronExpression;

    // 任务名称
    private String jobName;

    // 任务分组
    @ApiParam(defaultValue = "com.iyou.quartz")
    private String jobGroup = "com.iyou.quartz";

    // 任务描述
    @ApiParam(defaultValue = "Quartz-Demo任务调度")
    private String description = "";

    // 优先级
    @ApiParam(defaultValue = "5")
    private int priority = 5;

    // 是否持久化
    @ApiParam(defaultValue = "true")
    private boolean durability = true;

    // 是否要求唤醒
    @ApiParam(defaultValue = "true")
    private boolean requestsRecovery = true;

    @ApiParam(defaultValue = "true")
    private boolean isNonConcurrent = true; // TODO: 2019-11-23

    @ApiParam(defaultValue = "true")
    private boolean isUpdateData = true;    // TODO: 2019-11-23
}