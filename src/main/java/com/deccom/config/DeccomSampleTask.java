package com.deccom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DeccomSampleTask implements Runnable{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomSampleTask.class);

	private String id;
	public DeccomSampleTask(String id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("MyTask: "+id);
	}

}
