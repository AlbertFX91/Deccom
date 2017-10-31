package com.deccom.config;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.deccom.domain.RESTConnection;
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTControlVarEntry;
import com.deccom.service.RESTControlVarService;

public class DeccomSaveTask implements Runnable{
	

    private static final Logger log = LoggerFactory.getLogger(DeccomSaveTask.class);
    
    private RESTControlVarService service;
    
	private String id;
	public DeccomSaveTask(String id, RESTControlVarService service) {
		this.id = id;
		this.service = service;
	}
	
	@Override
	public void run() {
		List<RESTControlVarEntry> ls = new ArrayList<>();
		service.save(new RESTControlVar(id, id, LocalDateTime.now(), new RESTConnection(id), ls));
		System.out.println("MyTask: "+id);
	}

}
