package com.deccom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyTask implements Runnable{
	

    private static final Logger log = LoggerFactory.getLogger(MyTask.class);

	private String id;
	public MyTask(String id) {
		this.id = id;
	}
	
	@Override
	public void run() {
		System.out.println("MyTask: "+id);
	}

}
