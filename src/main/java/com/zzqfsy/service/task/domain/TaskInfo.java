package com.zzqfsy.service.task.domain;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

/**
 * 管理定时任务
 * @author Administrator
 */
public class TaskInfo implements Serializable{
	/**任务ID,0标识新增,!=0标识更新*/
	private int id = 0;

	/**任务名称*/
	private String jobName;
	
	/**任务分组*/
	private String jobGroup;

	/**任务执行类*/
	private String jobClassName;
	
	/**任务描述*/
	private String jobDescription;
	
	/**任务状态*/
	private String jobStatus;
	
	/**任务表达式*/
	private String cronExpression;

	/**任务创建时间*/
	private String createTime;

	/**任务参数*/
	@ApiModelProperty(value = "任务参数",dataType = "Map[String,Object]")
	private Map<String, Object> jobDateMap;

	private String ipAddress;
	private String method;
	private String param;

	public TaskInfo() {
	}

	public TaskInfo(
		int id,
		String jobName,
		String jobGroup,
		String jobClassName,
		String jobDescription,
		String jobStatus,
		String cronExpression,
		String createTime,
		String ipAddress,
		String method,
		String param
	) {
		this.id = id;
		this.jobName = jobName;
		this.jobGroup = jobGroup;
		this.jobClassName = jobClassName;
		this.jobDescription = jobDescription;
		this.jobStatus = jobStatus;
		this.cronExpression = cronExpression;
		this.createTime = createTime;
		this.ipAddress = ipAddress;
		this.method = method;
		this.param = param;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getJobName() {
		return jobName;
	}

	public void setJobName(String jobName) {
		this.jobName = jobName;
	}

	public String getJobGroup() {
		return jobGroup;
	}

	public void setJobGroup(String jobGroup) {
		this.jobGroup = jobGroup;
	}

	public String getJobClassName() {
		return jobClassName;
	}

	public void setJobClassName(String jobClassName) {
		this.jobClassName = jobClassName;
	}

	public String getJobDescription() {
		return jobDescription;
	}

	public void setJobDescription(String jobDescription) {
		this.jobDescription = jobDescription;
	}

	public String getJobStatus() {
		return jobStatus;
	}

	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}

	public String getCronExpression() {
		return cronExpression;
	}

	public void setCronExpression(String cronExpression) {
		this.cronExpression = cronExpression;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public Map<String, Object> getJobDateMap() {
		return jobDateMap;
	}

	public void setJobDateMap(Map<String, Object> jobDateMap) {
		this.jobDateMap = jobDateMap;
	}
}