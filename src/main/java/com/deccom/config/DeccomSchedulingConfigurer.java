package com.deccom.config;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

import com.deccom.domain.RESTControlVar;
import com.deccom.domain.SQLControlVar;
import com.deccom.service.RESTControlVarService;
import com.deccom.service.SQLControlVarService;

@Configuration
@EnableScheduling
public class DeccomSchedulingConfigurer implements SchedulingConfigurer{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomSchedulingConfigurer.class);
	
	@Autowired
    private RESTControlVarService restCvService;
	@Autowired
    private SQLControlVarService sqlCvService;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		log.debug("Scheduled ControlVar tasks initialization");
		taskRegistrar.setScheduler(taskExecutor());
		

		log.debug("Scheduled RESTControlVar tasks");
		for(RESTControlVar cv: restCvService.findAll()) {
			taskRegistrar.addTriggerTask(
					// Runnable object
					new DeccomRESTControlVarMonitorTask(cv, restCvService), 
					// Trigger object
					new DeccomSecondTrigger(
							// Example dynamic period to test the parallel execution
							cv.getName().equals("id1") ? 5 : 10
							));
    	}
	
		log.debug("Scheduled SQLControlVar tasks");
		for(SQLControlVar cv: sqlCvService.findAll()) {
			taskRegistrar.addTriggerTask(
					// Runnable object
					new DeccomSQLControlVarMonitorTask(cv, sqlCvService), 
					// Trigger object
					new DeccomSecondTrigger(
							// Example dynamic period to test the parallel execution
							cv.getName().equals("controlvar-1") ? 5 : 10
							));
    	}
	}
	
	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor() {
		// 1000 scheluded tasks throw in parallel
		return Executors.newScheduledThreadPool(1000);
	}

}
