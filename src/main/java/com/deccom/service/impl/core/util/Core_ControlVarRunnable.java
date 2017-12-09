package com.deccom.service.impl.core.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.core.Core_ControlVar;
import com.deccom.domain.core.Status;
import com.deccom.service.impl.core.Core_ControlVarService;

public class Core_ControlVarRunnable implements Runnable {

	private static final Logger log = LoggerFactory
			.getLogger(Core_ControlVarRunnable.class);

	private Core_ControlVar cv;
	private Core_ControlVarService service;

	public Core_ControlVarRunnable(Core_ControlVar cv,
			Core_ControlVarService service) {
		this.cv = cv;
		this.service = service;
	}

	@Override
	public void run() {
		if (cv.getStatus() == Status.RUNNING) {
			log.debug("Core_ControlVar monitoring: " + cv.getId());
			service.executeMonitorize(cv);
		}
	}

}
