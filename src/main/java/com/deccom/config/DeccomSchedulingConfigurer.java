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
import com.deccom.service.RESTControlVarService;

@Configuration
@EnableScheduling
public class DeccomSchedulingConfigurer implements SchedulingConfigurer{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomSchedulingConfigurer.class);
	
	@Autowired
    private RESTControlVarService restCvService;
	
	@Override
	public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
		log.info("Scheduled ControlVar tasks initialization");
		taskRegistrar.setScheduler(taskExecutor());
		for(RESTControlVar r: restCvService.findAll()) {
			taskRegistrar.addTriggerTask(
					// Runnable object
					new DeccomSaveTask(r.getId(), restCvService), 
					// Trigger object
					new DeccomCustomTrigger(10));
    	}
		
		/*
		taskRegistrar.addTriggerTask(
				new Runnable() {
                    @Override public void run() {
                        myBean().run();
                    }
                },
				new Trigger() {
					@Override public Date nextExecutionTime(TriggerContext triggerContext) {
                        Calendar nextExecutionTime =  new GregorianCalendar();
                        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.MILLISECOND, 2000); //you can get the value from wherever you want
                        return nextExecutionTime.getTime();
		            }
		        });
		*/
		
		/*
		for(int i = 1; i <= 10; i++) {
			taskRegistrar.addTriggerTask(
					new MyTask(i),
					new DeccomCustomTrigger(i));
		}*/
	}
	
	@Bean(destroyMethod="shutdown")
	public Executor taskExecutor() {
		// 1000 scheluded tasks throw in parallel
		return Executors.newScheduledThreadPool(1000);
	}

}
