package com.deccom.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.RESTControlVar;
import com.deccom.service.RESTControlVarService;

public class DeccomRESTControlVarMonitorTask implements Runnable{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomRESTControlVarMonitorTask.class);
    
    private RESTControlVar cv;
    private RESTControlVarService service;
    
	public DeccomRESTControlVarMonitorTask(RESTControlVar cv, RESTControlVarService service) {
		this.cv = cv;
		this.service = service;
	}
	
	@Override
	public void run() {
		log.debug("RESTControlVar monitoring: "+cv.getId());
		service.executeMonitorize(cv);
	}

}
