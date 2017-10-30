package com.zzqfsy.config.quartz;

import com.zzqfsy.config.mysql.MyBatisMapperScannerConfig;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 配置任务调度中心
 * [QRTZ_JOB_DETAILS], [QRTZ_TRIGGERS] and [QRTZ_CRON_TRIGGERS]
 * @author zzqfsy
 */
@Configuration
@AutoConfigureAfter(MyBatisMapperScannerConfig.class)
public class QuartzConfig {

	@Autowired
	@Qualifier("quartzDataSource")
	DataSource dataSource;

	@Autowired
	PlatformTransactionManager platformTransactionManager;

	@Bean
	public JobFactory jobFactory(ApplicationContext applicationContext) {
		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		return jobFactory;
	}

	@Bean(name = "quartzProperties")
	public Properties quartzProperties() throws IOException {
		PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
		propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
		propertiesFactoryBean.afterPropertiesSet();
		return propertiesFactoryBean.getObject();
	}

	@Bean
	public SchedulerFactoryBean zzqTaskSchedule(JobFactory jobFactory) throws IOException {
		SchedulerFactoryBean scheduler = new SchedulerFactoryBean();
		//scheduler.setTriggers(cronTriggerFactoryBean().getObject());
		scheduler.setDataSource(dataSource);
		scheduler.setTransactionManager(platformTransactionManager);
		Properties properties = quartzProperties();
		scheduler.setQuartzProperties(properties);
		scheduler.setSchedulerName(properties.getProperty("org.quartz.scheduler.instanceName"));
		scheduler.setAutoStartup(true);
		scheduler.setOverwriteExistingJobs(true);

		scheduler.setJobFactory(jobFactory);
		return scheduler;
	}

	/*@Bean
	public Scheduler scheduler() throws IOException, SchedulerException {
		SchedulerFactory schedulerFactory = new StdSchedulerFactory(quartzMySQLProperties());
		Scheduler scheduler = schedulerFactory.getScheduler();
		scheduler.start();
		return scheduler;
	}*/

	/**
	 * 设置quartz属性
	 * @throws IOException
	 */
	/*public Properties quartzMySQLProperties() throws IOException {
		Properties prop = new Properties();
		prop.put("quartz.scheduler.instanceName", "ServerScheduler");
		prop.put("org.quartz.scheduler.instanceId", "AUTO");
		prop.put("org.quartz.scheduler.skipUpdateCheck", "true");
		prop.put("org.quartz.scheduler.instanceId", "NON_CLUSTERED");
		prop.put("org.quartz.scheduler.jobFactory.class", "org.quartz.simpl.SimpleJobFactory");
		prop.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");
		prop.put("org.quartz.jobStore.driverDelegateClass", "org.quartz.impl.jdbcjobstore.StdJDBCDelegate");
		prop.put("org.quartz.jobStore.dataSource", "quartzDataSource");
		prop.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
		prop.put("org.quartz.jobStore.isClustered", "true");
		prop.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        prop.put("org.quartz.threadPool.threadCount", "5");

		prop.put("org.quartz.dataSource.quartzDataSource.driver", "com.mysql.jdbc.Driver");
		prop.put("org.quartz.dataSource.quartzDataSource.URL", "jdbc:mysql://localhost:3306/schedule_job");
		prop.put("org.quartz.dataSource.quartzDataSource.user", "root");
		prop.put("org.quartz.dataSource.quartzDataSource.password", "root");
		prop.put("org.quartz.dataSource.quartzDataSource.maxConnections", "10");
		return prop;
	}*/
}