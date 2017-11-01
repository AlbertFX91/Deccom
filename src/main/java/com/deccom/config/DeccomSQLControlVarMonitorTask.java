package com.deccom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.SQLControlVar;
import com.deccom.service.SQLControlVarService;

public class DeccomSQLControlVarMonitorTask implements Runnable{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomSQLControlVarMonitorTask.class);
    
    private SQLControlVar cv;
    private SQLControlVarService service;
    
	public DeccomSQLControlVarMonitorTask(SQLControlVar cv, SQLControlVarService service) {
		this.cv = cv;
		this.service = service;
	}
	
	@Override
	public void run() {
		log.debug("SQLControlVar monitoring: "+cv.getId());
		service.executeMonitorize(cv);
	}

}
