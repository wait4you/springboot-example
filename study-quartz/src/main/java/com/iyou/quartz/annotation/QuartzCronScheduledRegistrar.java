package com.iyou.quartz.annotation;

import lombok.extern.slf4j.Slf4j;
import org.quartz.CronExpression;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.scheduling.SchedulingException;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;

/**
 * @author zhang.hp
 * @version 1.0
 * @description
 * @createDate 2019-08-12 14:28
 * @modifyDate 2019-08-12 14:28
 */
@Slf4j
public class QuartzCronScheduledRegistrar implements EnvironmentAware, ImportBeanDefinitionRegistrar {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        if (!(registry instanceof BeanFactory)) {
            throw new SchedulingException("registry未实现BeanFactory接口，无法转换成BeanFactory");
        }

        // QuartzCronScheduled 注解的 JobBean 类信息
        String jobClassName = importingClassMetadata.getClassName();
        Class jobClass = getJobClass(jobClassName);
        if (!QuartzJobBean.class.isAssignableFrom(jobClass)) { // 判断jobClass是否是QuartzJobBean的子类
            throw new SecurityException("Job Class " + jobClass.getName() + "没有继承QuartzJobBean类");
        }

        // 获取 QuartzCronScheduled 实例
        QuartzCronScheduled cronScheduled = (QuartzCronScheduled) jobClass.getAnnotation(QuartzCronScheduled.class);
        checkQuartzCronScheduled(cronScheduled);

        // 设置JobDetail相关属性，并将JobDetail注册到Spring容器中
        registry.registerBeanDefinition(
                getJobDetailBeanName(jobClassName),
                getJobDetailBeanDefinition(cronScheduled, jobClass)
        );

        // 设置Trigger
        registry.registerBeanDefinition(
                getTriggerBeanName(jobClassName),
                getTriggerBeanDefinition(registry, jobClassName, cronScheduled)
        );
    }

    private String getJobDetailBeanName(String className) {
        return className;
    }

    private BeanDefinition getJobDetailBeanDefinition(QuartzCronScheduled cronScheduled, Class jobClass) {
        return BeanDefinitionBuilder.genericBeanDefinition(JobDetailFactoryBean.class)
                .addPropertyValue("name", cronScheduled.jobName())
                .addPropertyValue("group", cronScheduled.jobGroup())
                .addPropertyValue("jobClass", jobClass)
                .addPropertyValue("durability", cronScheduled.durability())
                .addPropertyValue("requestsRecovery", cronScheduled.requestsRecovery())
                .setScope(BeanDefinition.SCOPE_SINGLETON)
                .getBeanDefinition();
    }

    private String getTriggerBeanName(String className) {
        return className + ".trigger";
    }

    private GenericBeanDefinition getTriggerBeanDefinition(
            BeanDefinitionRegistry registry,
            String className,
            QuartzCronScheduled cronScheduled
    ) {
        JobDetail jobDetail = getJobDetail(registry, className);
        JobKey jobKey = jobDetail.getKey();
        JobDataMap jobDataMap = getJobDataMap(jobDetail);

        GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
        beanDefinition.setBeanClass(CronTriggerImpl.class);
        beanDefinition.getPropertyValues()
                .add("name", cronScheduled.jobName())
                .add("group", cronScheduled.jobGroup())
                .add("jobKey", jobKey)
                .add("cronExpression", cronScheduled.cronExpression())
                .add("priority", cronScheduled.priority())
                .add("description", cronScheduled.description())
                .add("jobDataMap", jobDataMap);
        beanDefinition.setScope(BeanDefinition.SCOPE_SINGLETON);
        return beanDefinition;
    }

    private JobDetail getJobDetail(BeanDefinitionRegistry registry, String className) {
        BeanFactory beanFactory = (BeanFactory) registry;
        return beanFactory.getBean(className, JobDetail.class);
    }

    private JobDataMap getJobDataMap(JobDetail jobDetail) {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put("jobDetail", jobDetail);
        return jobDataMap;
    }

    private Class getJobClass(String className) {
        try {
            return ClassUtils.forName(className, null);
        } catch (ClassNotFoundException e) {
            log.error("jobClass", e);
            throw new SchedulingException("jobClass == null");
        }
    }

    private String getJobName(AnnotationAttributes attributes) {
        String jobName = attributes.getString("jobName");
        if (StringUtils.hasText(jobName)) {
            return this.environment.resolvePlaceholders(jobName);
        } else {
            throw new SchedulingException("定时任务名称不能不设置或者为空");
        }
    }

    private void checkQuartzCronScheduled(QuartzCronScheduled cronScheduled) {
        if (cronScheduled == null) {
            throw new SecurityException("QuartzCronScheduled == null");
        }

        if (StringUtils.isEmpty(cronScheduled.jobName())) {
            throw new SecurityException("jobName 不能为空");
        }

        if (StringUtils.isEmpty(cronScheduled.jobGroup())) {
            throw new SecurityException("jobGroup 不能为空");
        }

        try {
            CronExpression.validateExpression(cronScheduled.cronExpression());
        } catch (ParseException e) {
            log.error("cron表达式不正确", e);
            throw new SecurityException("cron表达式不正确: {}" + e.getMessage());
        }
    }
}
