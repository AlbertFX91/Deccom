package com.deccom.service.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.core.ControlVariable;

public class ControlVariableRunnable implements Runnable {
	private static final Logger log = LoggerFactory
			.getLogger(ControlVariableRunnable.class);

	private ControlVariable cv;
	private ControlVariableService service;
	
	public ControlVariableRunnable(ControlVariable cv,
			ControlVariableService service) {
		this.cv = cv;
		this.service = service;
	}
	
	@Override
	public void run() {
		log.debug("ControlVariable monitoring: " + cv.getId());
		service.executeMonitorize(cv);
	}

}
