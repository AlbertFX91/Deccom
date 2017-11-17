package com.deccom.config;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.deccom.service.impl.DeccomSchedulingService;

@Configuration
@EnableScheduling
public class DeccomSchedulingConfigurer implements SchedulingConfigurer {

	private static final Logger log = LoggerFactory.getLogger(DeccomSchedulingConfigurer.class);

	@Autowired
	private DeccomSchedulingService schedulingService;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		log.debug("Scheduled ControlVar tasks initialization");
		ScheduledExecutorService str = taskExecutor();
		taskRegistrar.setScheduler(str);
		schedulingService.startJobs(str);
	}

	@Bean(destroyMethod = "shutdown")
	public ScheduledExecutorService taskExecutor() {
		// 1000 scheluded tasks throw in parallel
		return Executors.newScheduledThreadPool(1000);
	}

}
