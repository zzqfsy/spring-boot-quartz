package com.zzqfsy.service.task;

import com.zzqfsy.config.quartz.QuartzRecordType;
import com.zzqfsy.service.task.domain.TaskInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by john on 16-12-22.
 */
@Service
public class TaskService {
    protected static final Logger logger = LoggerFactory.getLogger(TaskService.class);
    @Autowired
    private Scheduler scheduler;

    /**
     * 所有任务列表
     */
    public List<TaskInfo> list(String jobGroup){
        List<TaskInfo> list = new ArrayList<>();

        try {
            List<String> jobGroupNames = scheduler.getJobGroupNames();

            //不根据分组查询
            if (StringUtils.isEmpty(jobGroup)){
                for(String groupJob: jobGroupNames){
                    getTaskInfosByJobGroup(list, groupJob);
                }
                return list;
            }

            //根据分组查询
            if (!jobGroupNames.contains(jobGroup)){
                return new ArrayList<>();
            }
            getTaskInfosByJobGroup(list, jobGroup);
        } catch (SchedulerException e) {
            logger.error("list exception", e);
        }

        return list;
    }

    private void getTaskInfosByJobGroup(List<TaskInfo> taskInfos, final String jobGroup){
        try {
            for(JobKey jobKey: scheduler.getJobKeys(GroupMatcher.<JobKey>groupEquals(jobGroup))){
                List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
                for (Trigger trigger: triggers) {
                    Trigger.TriggerState triggerState = scheduler.getTriggerState(trigger.getKey());
                    JobDetail jobDetail = scheduler.getJobDetail(jobKey);

                    String cronExpression = "", createTime = "";

                    if (trigger instanceof CronTrigger) {
                        CronTrigger cronTrigger = (CronTrigger) trigger;
                        cronExpression = cronTrigger.getCronExpression();
                        createTime = cronTrigger.getDescription();
                    }
                    TaskInfo info = new TaskInfo();
                    info.setJobName(jobKey.getName());
                    info.setJobGroup(jobKey.getGroup());
                    info.setJobClassName(jobDetail.getJobClass().getName());
                    info.setJobDescription(jobDetail.getDescription());
                    info.setJobStatus(triggerState.name());
                    info.setCronExpression(cronExpression);
                    info.setCreateTime(createTime);
                    JobDataMap jobDataMap = jobDetail.getJobDataMap();
                    info.setIpAddress(jobDataMap.getString("ipAddress"));
                    info.setMethod(jobDataMap.getString("method"));
                    info.setParam(jobDataMap.getString("param"));
                    info.setJobDateMap(jobDataMap);
                    taskInfos.add(info);
                }
            }
        } catch (SchedulerException e) {
            logger.error("list exception", e);
        }
    }

    /**
     * 指定任务
     */
    public TaskInfo detail(String jobName, String jobGroup){
        try {
            if (!checkExists(jobName, jobGroup)) {
                throw new RuntimeException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);

            List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
            if (triggers.isEmpty()) return null;

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);
            Trigger.TriggerState triggerState = scheduler.getTriggerState(triggerKey);

            TaskInfo info = new TaskInfo();
            info.setJobName(jobKey.getName());
            info.setJobGroup(jobKey.getGroup());
            info.setJobClassName(jobDetail.getJobClass().getName());
            info.setJobDescription(jobDetail.getDescription());
            info.setJobStatus(triggerState.name());
            if (triggers.get(0) instanceof CronTrigger){
                CronTrigger cronTrigger = (CronTrigger) triggers.get(0);
                info.setCronExpression(cronTrigger.getCronExpression());
                info.setCreateTime(cronTrigger.getDescription());
            }
            JobDataMap jobDataMap = jobDetail.getJobDataMap();
            info.setIpAddress(jobDataMap.getString("ipAddress"));
            info.setMethod(jobDataMap.getString("method"));
            info.setParam(jobDataMap.getString("param"));
            return info;
        } catch (SchedulerException e) {
            logger.error("detail exception", e);
        }

        return null;
    }

    /**
     * 保存定时任务
     * @param info
     */
    @SuppressWarnings("unchecked")
    @QuartzRecordType(type = QuartzRecordType.job, action = "add")
    public void add(TaskInfo info) {
        String jobName = info.getJobName(),
            jobGroup = info.getJobGroup(),
            cronExpression = info.getCronExpression(),
            jobDescription = info.getJobDescription(),
            jobClassName = info.getJobClassName(),
            createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
            ipAddress = info.getIpAddress(),
            method = info.getMethod(),
            param = info.getParam();
        try {
            if (checkExists(jobName, jobGroup)) {
                logger.info("===> AddJob fail, job already exist, jobGroup:{}, jobName:{}", jobGroup, jobName);
                throw new RuntimeException(String.format("Job已经存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }

            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = JobKey.jobKey(jobName, jobGroup);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            Class<? extends Job> clazz = (Class<? extends Job>)Class.forName(jobClassName);
            JobBuilder jobBuilder = JobBuilder.newJob(clazz).withIdentity(jobKey).withDescription(jobDescription);
            if (!StringUtils.isEmpty(ipAddress)) jobBuilder.usingJobData("ipAddress", ipAddress);
            if (!StringUtils.isEmpty(method)) jobBuilder.usingJobData("method", method);
            if (!StringUtils.isEmpty(param)) jobBuilder.usingJobData("param", param);
            JobDetail jobDetail = jobBuilder.build();
            scheduler.scheduleJob(jobDetail, cronTrigger);
        } catch (SchedulerException | ClassNotFoundException e) {
            logger.error("add exception", e);
            throw new RuntimeException("类名不存在或执行表达式错误");
        }
    }

    /**
     * 修改定时任务
     * @param info
     */
    @QuartzRecordType(type = QuartzRecordType.job, action = "edit")
    public void edit(TaskInfo info) {
        String jobName = info.getJobName(),
            jobGroup = info.getJobGroup(),
            jobClassName = info.getJobClassName(),
            cronExpression = info.getCronExpression(),
            jobDescription = info.getJobDescription(),
            createTime = DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"),
            ipAddress = info.getIpAddress(),
            method = info.getMethod(),
            param = info.getParam();
        try {
            if (!checkExists(jobName, jobGroup)) {
                throw new RuntimeException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }
            TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
            JobKey jobKey = new JobKey(jobName, jobGroup);

            CronScheduleBuilder cronScheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression).withMisfireHandlingInstructionDoNothing();
            CronTrigger cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withDescription(createTime).withSchedule(cronScheduleBuilder).build();

            JobDetail jobDetail = scheduler.getJobDetail(jobKey);

            if (!jobDetail.getJobClass().getName().equals(jobClassName)){
                throw new RuntimeException(String.format("Job不存在, jobName:{%s},jobGroup:{%s}", jobName, jobGroup));
            }

            JobBuilder jobBuilder = jobDetail.getJobBuilder();
            if (!StringUtils.isEmpty(jobDescription)) jobBuilder.withDescription(jobDescription);
            if (!StringUtils.isEmpty(ipAddress)) jobBuilder.usingJobData("ipAddress", ipAddress);
            if (!StringUtils.isEmpty(method)) jobBuilder.usingJobData("method", method);
            if (!StringUtils.isEmpty(param)) jobBuilder.usingJobData("param", param);
            jobDetail = jobBuilder.build();
            HashSet<Trigger> triggerSet = new HashSet<>();
            triggerSet.add(cronTrigger);

            scheduler.scheduleJob(jobDetail, triggerSet, true);
        } catch (SchedulerException e) {
            logger.error("edit exception", e);
            throw new RuntimeException("类名不存在或执行表达式错误");
        }
    }

    /**
     * 删除定时任务
     * @param taskInfo
     */
    @QuartzRecordType(type = QuartzRecordType.job, action = "delete")
    public void delete(TaskInfo taskInfo){
        TriggerKey triggerKey = TriggerKey.triggerKey(taskInfo.getJobName(), taskInfo.getJobGroup());
        try {
            if (checkExists(taskInfo.getJobName(), taskInfo.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey);
                scheduler.unscheduleJob(triggerKey);
                logger.info("===> delete, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            logger.error("delete exception", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 暂停定时任务
     * @param taskInfo
     */
    @QuartzRecordType(type = QuartzRecordType.job, action = "pause")
    public void pause(TaskInfo taskInfo){
        TriggerKey triggerKey = TriggerKey.triggerKey(taskInfo.getJobName(), taskInfo.getJobGroup());
        try {
            if (checkExists(taskInfo.getJobName(), taskInfo.getJobGroup())) {
                scheduler.pauseTrigger(triggerKey);
                logger.info("===> Pause success, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            logger.error("pause exception", e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**
     * 重新开始任务
     * @param taskInfo
     */
    @QuartzRecordType(type = QuartzRecordType.job, action = "resume")
    public void resume(TaskInfo taskInfo){
        TriggerKey triggerKey = TriggerKey.triggerKey(taskInfo.getJobName(), taskInfo.getJobGroup());

        try {
            if (checkExists(taskInfo.getJobName(), taskInfo.getJobGroup())) {
                scheduler.resumeTrigger(triggerKey);
                logger.info("===> Resume success, triggerKey:{}", triggerKey);
            }
        } catch (SchedulerException e) {
            logger.error("resume exception", e);
        }
    }

    /**
     * 验证是否存在
     * @param jobName
     * @param jobGroup
     * @throws SchedulerException
     */
    private boolean checkExists(String jobName, String jobGroup) throws SchedulerException{
        if (StringUtils.isEmpty(jobName) || StringUtils.isEmpty(jobGroup)){
            return false;
        }

        TriggerKey triggerKey = TriggerKey.triggerKey(jobName, jobGroup);
        return scheduler.checkExists(triggerKey);
    }
}
