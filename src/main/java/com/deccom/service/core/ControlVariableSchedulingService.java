package com.deccom.service.core;

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

import com.deccom.domain.core.ControlVariable;
import com.deccom.domain.core.Status;

/**
 * Service class for managing users.
 */
@Service
public class ControlVariableSchedulingService {

	private final Logger log = LoggerFactory
			.getLogger(ControlVariableSchedulingService.class);

	private ScheduledExecutorService str;

	@Autowired
	private ControlVariableService cvService;

	// Map which stores the ID from the controlVar and the job to be executed
	private Map<String, ScheduledFuture<?>> jobs;

	public synchronized void startJobs() {
		log.debug("Starting Jobs");
		jobs = new HashMap<>();
		for (ControlVariable cv : cvService.findAll()) {
			ScheduledFuture<?> job = str.scheduleAtFixedRate(
					new ControlVariableRunnable(cv, cvService), 0,
					cv.getFrequency(), TimeUnit.SECONDS);
			jobs.put(cv.getId(), job);
		}
	}

	public synchronized void startJobs(ScheduledExecutorService newstr) {
		this.str = newstr;
		startJobs();
	}

	public synchronized void resetJobs() {
		log.debug("Resetting jobs");
		for (Entry<String, ScheduledFuture<?>> e : jobs.entrySet()) {
			e.getValue().cancel(false);
		}
		jobs.clear();
		startJobs();
	}

	public synchronized void stopJobs() {
		log.debug("Stopping jobs");
		for (Entry<String, ScheduledFuture<?>> e : jobs.entrySet()) {
			e.getValue().cancel(false);
		}
		jobs.clear();
	}

	public void newJob(ControlVariable cv) {
		log.debug("New job");
		ScheduledFuture<?> job = str.scheduleAtFixedRate(
				new ControlVariableRunnable(cv, cvService), 0,
				cv.getFrequency(), TimeUnit.SECONDS);
		jobs.put(cv.getId(), job);
	}
	
	public synchronized void stopJob(ControlVariable cv) {
		if (cv.getStatus().equals(Status.PAUSED)
				|| cv.getStatus().equals(Status.BLOCKED)) {
			log.debug("Stopping job with name: " + cv.getName());
			jobs.get(cv.getId()).cancel(false);
			jobs.remove(cv.getId());
		}
	}
	
	public synchronized Boolean isRunning(ControlVariable cv) {
		return jobs.containsKey(cv.getId());
	}

	/*
	 * //Call this on deployment from the ScheduleDataRepository and everytime
	 * when schedule data changes. public synchronized void scheduleJob(int
	 * jobNr, long newRate) {//you are free to change/add new scheduling data,
	 * but suppose for now you only want to change the rate if (jobNr == 1)
	 * {//instead of if/else you could use a map with all job data if (job1 !=
	 * null) {//job was already scheduled, we have to cancel it
	 * job1.cancel(true); } //reschedule the same method with a new rate // job1
	 * = taskScheduler.scheduleAtFixedRate(new
	 * ScheduledMethodRunnable(myService, "methodInMyServiceToReschedule"),
	 * newRate); } }
	 */
}
