package com.iyou.quartz.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author zhang.hp
 * @version 1.0
 * @description 使注解@QuartzCronSchedule生效（现在@QuartzCronSchedule默认生效）
 * @createDate 2019-08-11 08:04
 * @modifyDate 2019-08-11 08:04
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(QuartzCronScheduled.class)
public @interface EnableQuartzCronScheduled {
}
