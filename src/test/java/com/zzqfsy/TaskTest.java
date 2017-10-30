package com.zzqfsy;

import com.zzqfsy.service.task.domain.TaskInfo;
import com.zzqfsy.utils.HttpUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by john on 16-12-23.
 */
public class TaskTest {
    private static final String ipaddress = "http://127.0.0.1:18700/";

    HttpUtils.ParamMap paramMap = new HttpUtils.ParamMap();

    @Test
    public void list() throws IOException {
        String result= HttpUtils.post(ipaddress+"list", paramMap.putParam("page", "1").putParam("rows", "10").putParam("jobGroup", "timesendCoupon"));
        System.out.println(result);
    }

    @Test
    public void list2() throws IOException {
        String result= HttpUtils.get(ipaddress+"tasks/",
                paramMap.putParam("start", "0").putParam("length", "10")
//                        .putParam("search[value]", "jobGroup")
                        .putParam("columns[0][name]", "jobGroup")
                        .putParam("columns[0][data]", "timesendCoupon")
                        );
        System.out.println(result);
    }

    @Test
    public void detail() throws IOException {
        String result= HttpUtils.post(ipaddress+"detail", paramMap);
        System.out.println(result);
    }

    @Test
    public void save() throws IOException {
        Map<String, Object> dataMap = new HashMap<>();
        dataMap.put("ipAddress", "http://127.0.0.1:18700/list");
        dataMap.put("method", "post");
        dataMap.put("param", "");

        TaskInfo taskInfo = new TaskInfo();
        taskInfo.setId(0);
        taskInfo.setJobName("com.zzqfsy.task.job.HttpRPCJob1");
        taskInfo.setJobGroup("HTTP_RPC_Group");
        taskInfo.setCronExpression("0/30 * * * * ?");
        taskInfo.setIpAddress((String) dataMap.get("ipAddress"));
        taskInfo.setMethod((String) dataMap.get("method"));
        taskInfo.setParam((String) dataMap.get("param"));
        taskInfo.setJobClassName("com.zzqfsy.task.job.HttpRPCJob");

        paramMap.putParam("id", taskInfo.getId() + "")
                .putParam("jobName", taskInfo.getJobName())
                .putParam("jobGroup", taskInfo.getJobGroup())
                .putParam("jobDescription", taskInfo.getJobDescription())
                .putParam("cronExpression", taskInfo.getCronExpression())
                .putParam("jobClassName", taskInfo.getJobClassName())
                .putParam("ipAddress", taskInfo.getIpAddress())
                .putParam("method", taskInfo.getMethod())
                .putParam("param", taskInfo.getParam());

        String result= HttpUtils.post(ipaddress+"save", paramMap);
        //String result= HttpUtils.postJson(ipaddress+"save", JSONObject.toJSONString(taskInfo));
        System.out.println(result);
    }

}
