package com.deccom.config;

import java.util.Date;

import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.support.CronTrigger;

public class DeccomCustomTrigger implements Trigger {
	private String cronExpresion;
	
	public DeccomCustomTrigger(Integer seconds) {
		this.cronExpresion = "*/"+seconds+" * * * * *";
	}

	@Override
	public Date nextExecutionTime(TriggerContext triggerContext) {
		/*
		Calendar nextExecutionTime =  new GregorianCalendar();
        Date lastActualExecutionTime = triggerContext.lastActualExecutionTime();
        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
        nextExecutionTime.add(Calendar.MILLISECOND, factor*500); //you can get the value from wherever you want
        return nextExecutionTime.getTime();*/
		return new CronTrigger(cronExpresion).nextExecutionTime(triggerContext);
	}
}
