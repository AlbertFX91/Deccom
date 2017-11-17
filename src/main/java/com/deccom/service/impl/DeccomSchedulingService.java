package com.deccom.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.deccom.config.DeccomRESTControlVarMonitorTask;
import com.deccom.config.DeccomSQLControlVarMonitorTask;
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.SQLControlVar;
import com.deccom.service.RESTControlVarService;
import com.deccom.service.SQLControlVarService;

/**
 * Service class for managing users.
 */
@Service
public class DeccomSchedulingService {
 
    private final Logger log = LoggerFactory.getLogger(DeccomSchedulingService.class);
    
    private ScheduledExecutorService str;
    
    @Autowired
	private RESTControlVarService restCvService;
	@Autowired
	private SQLControlVarService sqlCvService;

	// Map which stores the ID from the controlVar and the job to be executed
    private Map<String, ScheduledFuture<?>> jobs;
   

    public synchronized void startJobs() {
    	jobs = new HashMap<>();
    	for (RESTControlVar cv : restCvService.findAll()) {
			ScheduledFuture<?> job = str.scheduleAtFixedRate(new DeccomRESTControlVarMonitorTask(cv, restCvService), cv.getFrequency_sec() , cv.getFrequency_sec(), TimeUnit.SECONDS);
			jobs.put(cv.getId(), job);
		}
		for (SQLControlVar cv : sqlCvService.findAll()) {
			ScheduledFuture<?> job = str.scheduleAtFixedRate(new DeccomSQLControlVarMonitorTask(cv, sqlCvService), 0 , cv.getFrequency_sec(), TimeUnit.SECONDS);
			jobs.put(cv.getId(), job);
		}
    }
    
    public synchronized void startJobs(ScheduledExecutorService newstr) {
		this.str = newstr;
		startJobs();
    	
    }
    
    
    public synchronized void resetJobs() {
    	for(Entry<String, ScheduledFuture<?>> e: jobs.entrySet()) {
    		e.getValue().cancel(false);
    	}
    	jobs.clear();
    	startJobs();
    }
    
    public synchronized void stopJobs() {
    	for(Entry<String, ScheduledFuture<?>> e: jobs.entrySet()) {
    		e.getValue().cancel(false);
    	}
    	jobs.clear();
    }
    
    public void newJob(RESTControlVar cv) {
    	ScheduledFuture<?> job = str.scheduleAtFixedRate(new DeccomRESTControlVarMonitorTask(cv, restCvService), 0 , cv.getFrequency_sec(), TimeUnit.SECONDS);
		jobs.put(cv.getId(), job);
    }
    
    public void newJob(SQLControlVar cv) {
    	ScheduledFuture<?> job = str.scheduleAtFixedRate(new DeccomSQLControlVarMonitorTask(cv, sqlCvService), 0 , cv.getFrequency_sec(), TimeUnit.SECONDS);
		jobs.put(cv.getId(), job);
    }
    /*
    //Call this on deployment from the ScheduleDataRepository and everytime when schedule data changes.
    public synchronized void scheduleJob(int jobNr, long newRate) {//you are free to change/add new scheduling data, but suppose for now you only want to change the rate
            if (jobNr == 1) {//instead of if/else you could use a map with all job data
                    if (job1 != null) {//job was already scheduled, we have to cancel it
                            job1.cancel(true);
                    }
                    //reschedule the same method with a new rate
                    // job1 = taskScheduler.scheduleAtFixedRate(new ScheduledMethodRunnable(myService, "methodInMyServiceToReschedule"), newRate);
            }
    }
    */
}
