package com.deccom.service.impl.core;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.core.Core_Connection;
import com.deccom.domain.core.Core_ControlVar;
import com.deccom.domain.core.Core_ControlVarEntry;
import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.repository.core.Core_ControlVarRepository;
	

@Service
public class Core_ControlVarService {
	private final Logger log = LoggerFactory.getLogger(Core_ControlVarService.class);
	
	@Autowired
    private Core_ControlVarRepository controlVarRepository;
	
	@Autowired
	private Core_SchedulingService schedulingService;
	
	/**
	 * Create a Core_ControlVar.
	 *
	 * @return the entity
	 */
	public Core_ControlVar create() {
		Core_ControlVar result = new Core_ControlVar();

		result.setCreationMoment(LocalDateTime.now());
		result.setDisabled(false);
		result.setEntries(new ArrayList<>());

		return result;
	}
	
	public Core_ControlVar save(Core_ControlVar c) {
		Core_ControlVar cv =  controlVarRepository.save(c);
		schedulingService.newJob(cv);
		return cv;
	}
	
    public Page<Core_ControlVar> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return controlVarRepository.findAll(pageable);
    }
	
    public List<Core_ControlVar> findAll(){
        log.debug("Request to get all Core_Connection");
    	return controlVarRepository.findAll();
    }
    
    public void run(){
        log.debug("Request to run all Core_Connection");
    	List<Core_ControlVar> controlVars = controlVarRepository.findAll();
    	for(Core_ControlVar cv: controlVars) {
    		executeMonitorize(cv);
    	}
    }   
    public void executeMonitorize(Core_ControlVar controlVar) {
    	Core_Connection connection = controlVar.getConnection();
    	Object wrapper;
		try {
			wrapper = Class.forName(connection.get_extractorClass()).newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			// TODO
			e.printStackTrace();
			wrapper = new Object();
		}
		Map<String, Core_Connection> fields = new HashMap<>();
		fields.put("dataConnection", connection);
		if(wrapper instanceof Core_DataExtractor) {
			Core_DataExtractor dataExtractor = (Core_DataExtractor) wrapper;
			propertiesInjection(dataExtractor, fields);
			String value = dataExtractor.getData();
			addEntry(controlVar, value);
			log.debug("Data extracted ["+controlVar.getName()+"]: "+value);
		}
    }
    public Core_ControlVarEntry addEntry(Core_ControlVar cv, String value) {
    	Core_ControlVarEntry entry = new Core_ControlVarEntry();
    	entry.setCreationMoment(LocalDateTime.now());
    	entry.setValue(value);
    	cv.getEntries().add(entry);
    	controlVarRepository.save(cv);
    	return entry;
    }
	private static <T> void propertiesInjection(Object o, Map<String, T> properties) {
		for (Entry<String, T> property : properties.entrySet()) {
			String f = property.getKey();
			T v = property.getValue();
			Class<?> clazz = o.getClass();
			try {
				Field field = clazz.getDeclaredField(f);
				field.setAccessible(true);
				field.set(o, v);
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}
		}
	}
}
