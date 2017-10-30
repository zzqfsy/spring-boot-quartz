package com.zzqfsy.service.core;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zzqfsy.config.quartz.QuartzRecordType;
import com.zzqfsy.service.core.domain.QrtzRecordExecute;
import com.zzqfsy.service.core.domain.QrtzRecordJob;
import com.zzqfsy.service.task.domain.TaskInfo;
import com.zzqfsy.utils.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.impl.JobExecutionContextImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Properties;

/**
 * Created by john on 17-1-3.
 */
@Service
public class TaskRecordService {
    protected static final Logger logger = LoggerFactory.getLogger(TaskRecordService.class);

    @Autowired
    @Qualifier("quartzProperties")
    private Properties quartzProperties;
    @Autowired
    private QrtzRecordJobService qrtzRecordJobService;
    @Autowired
    private QrtzRecordExecuteService qrtzRecordExecuteService;

    public void createRecord(QuartzRecordType quartzRecordType, Class<?> clazz, Method method, Object[] args, Object result) {
        try {
            if (quartzRecordType.type().equals(QuartzRecordType.execute)) {
                JobExecutionContextImpl jobExecutionContext = (JobExecutionContextImpl) args[0];
                QrtzRecordExecute qrtzRecordExecute = new QrtzRecordExecute();
                qrtzRecordExecute.setSchedName(jobExecutionContext.getScheduler().getSchedulerName());
                qrtzRecordExecute.setInstanceName(jobExecutionContext.getScheduler().getSchedulerInstanceId());
                qrtzRecordExecute.setJobName(jobExecutionContext.getJobDetail().getKey().getName());
                qrtzRecordExecute.setJobGroup(jobExecutionContext.getJobDetail().getKey().getGroup());
                qrtzRecordExecute.setJobClassName(jobExecutionContext.getJobDetail().getJobClass().getName());
                qrtzRecordExecute.setExecuteParam(JSON.toJSONString(jobExecutionContext.getJobDetail().getJobDataMap()));
                qrtzRecordExecute.setExecuteTime(jobExecutionContext.getFireTime());
                if (result != null) {
                    String executeResult = result.toString();
                    qrtzRecordExecute.setExecuteResult(executeResult);
                }
                qrtzRecordExecute.setAddTime(new Date());
                qrtzRecordExecuteService.create(qrtzRecordExecute);
            } else if (quartzRecordType.type().equals(QuartzRecordType.job)) {
                Object obj = args[0];
                if (obj instanceof TaskInfo) {
                    TaskInfo taskInfo = (TaskInfo) obj;
                    QrtzRecordJob qrtzRecordJob = new QrtzRecordJob();
                    qrtzRecordJob.setSchedName(quartzProperties.getProperty("org.quartz.scheduler.instanceName", ""));
                    qrtzRecordJob.setJobName(taskInfo.getJobName());
                    qrtzRecordJob.setJobGroup(taskInfo.getJobGroup());
                    qrtzRecordJob.setDescription(taskInfo.getJobDescription());
                    qrtzRecordJob.setJobClassName(taskInfo.getJobClassName());
                    qrtzRecordJob.setJobData(JSON.toJSONString(taskInfo));
                    qrtzRecordJob.setTriggerName(taskInfo.getJobName());
                    qrtzRecordJob.setTriggerGroup(taskInfo.getJobGroup());
                    qrtzRecordJob.setCronExpression(taskInfo.getCronExpression());
                    qrtzRecordJob.setAction(quartzRecordType.action());
                    qrtzRecordJob.setAddTime(new Date());
                    qrtzRecordJobService.create(qrtzRecordJob);
                }else {

                }
            }
        } catch (Exception e) {
            logger.error("createRecord exception", e);
        }
    }
}
