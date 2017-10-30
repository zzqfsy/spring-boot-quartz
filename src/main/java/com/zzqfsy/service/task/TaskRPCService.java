package com.zzqfsy.service.task;

import com.zzqfsy.config.quartz.QuartzRecordType;
import com.zzqfsy.utils.HttpUtils;
import com.zzqfsy.utils.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by john on 17-1-3.
 */
@Service
public class TaskRPCService {
    protected static final Logger logger = LoggerFactory.getLogger(TaskRPCService.class);

    @QuartzRecordType(type = QuartzRecordType.execute)
    public String executeHttpRPC(JobExecutionContext context){
        String result = "";

        // job 的名字
        String jobName = context.getJobDetail().getKey().getName();
        String groupName = context.getJobDetail().getKey().getGroup();
        logger.info("JobName: {}, GroupName: {}.", jobName, groupName);

        // 任务执行的时间
        SimpleDateFormat dateFormat = new SimpleDateFormat(
            "yyyy 年 MM 月 dd 日  HH 时 mm 分 ss 秒");
        String jobStartTime = dateFormat.format(Calendar.getInstance().getTime());
        logger.info("jobStartTime: {}", jobStartTime);

        // 获取 JobDataMap , 并从中取出参数
        JobDataMap jobDataMap = context.getJobDetail().getJobDataMap();
        String ipAddress = (String) jobDataMap.get("ipAddress");
        String method = (String) jobDataMap.get("method");
        String param = (String) jobDataMap.get("param");
        if (!StringUtils.isEmpty(ipAddress)) {
            try {
                List<Header> headers = new ArrayList<Header>();
                if (method.equals("get")){
                    result = HttpUtils.get(ipAddress, headers, param);
                }else {
                    if (TypeUtils.isJson(param)) {
                        headers.add(new BasicHeader("Content-Type", "application/json; charset=UTF-8"));
                    }else {
                        headers.add(new BasicHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8"));
                    }
                    result = HttpUtils.post(ipAddress, headers, param);
                }
                logger.info("result: {}", result);
            } catch (IOException e) {
                logger.error("Call faill", e);
                result = e.getMessage();

            }
        }

        String jobEndTime = dateFormat.format(Calendar.getInstance().getTime());
        logger.info("jobEndTime: {}", jobEndTime);

        return result;
    }
}
