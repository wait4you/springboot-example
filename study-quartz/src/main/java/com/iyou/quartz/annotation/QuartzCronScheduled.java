package com.iyou.quartz.annotation;

import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-08-10 18:25
 * @modifyDate 2019-08-10 18:25
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
@Import(QuartzCronScheduledRegistrar.class)
public @interface QuartzCronScheduled {
    String cronExpression();
    String jobName();
    String jobGroup() default "com.iyou.quartz";
    String description() default "";
    int priority() default 5;
    boolean durability() default true;
    boolean requestsRecovery() default true;

    boolean isNonConcurrent() default true; // TODO: 2019-11-22
    boolean isUpdateData() default true;    // TODO: 2019-11-22
}
