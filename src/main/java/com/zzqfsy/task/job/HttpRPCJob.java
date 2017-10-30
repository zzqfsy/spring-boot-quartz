package com.zzqfsy.task.job;

import com.zzqfsy.service.task.TaskRPCService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 该方法仅仅用来测试每分钟执行
 * @author zzqfsy
 */
@Component
public class HttpRPCJob implements Job{
	protected static final Logger logger = LoggerFactory.getLogger(HttpRPCJob.class);

	@Autowired
	private TaskRPCService taskRPCService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		try {
			taskRPCService.executeHttpRPC(context);
		}catch (Exception ex){
			logger.error("HttpRPCJob exception", ex);
		}
	}
}