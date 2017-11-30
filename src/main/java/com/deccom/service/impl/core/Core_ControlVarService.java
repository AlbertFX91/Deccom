package com.deccom.service.impl.core;

import java.lang.reflect.Field;
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
import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.repository.core.Core_ControlVarRepository;
	

@Service
public class Core_ControlVarService {
	private final Logger log = LoggerFactory.getLogger(Core_ControlVarService.class);
	
	@Autowired
    private Core_ControlVarRepository controlVarRepository;
	
	public Core_ControlVar save(Core_ControlVar c) {
		return controlVarRepository.save(c);
	}
	
    public Page<Core_ControlVar> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return controlVarRepository.findAll(pageable);
    }
	
    public void run() throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
    	List<Core_ControlVar> controlVars = controlVarRepository.findAll();
    	for(Core_ControlVar cv: controlVars) {
    		Core_Connection connection = cv.getConnection();

    		System.out.println(connection.getClass());
    		// Data recover
    		/*Map<Field, Object> fields = new HashMap<>();
    		for(Field field: connection.getClass().getDeclaredFields()) {
    			field.setAccessible(true);
    			Object value = field.get(connection);
    			fields.put(field, value);
    		}
    		// Data printing
    		for(Entry<Field, Object> pair: fields.entrySet()) {
    			System.out.println(pair.getKey()+": "+pair.getValue());
    		}*/
    			
    		Object wrapper = Class.forName(connection.get_extractorClass()).newInstance();
    		Map<String, Core_Connection> fields = new HashMap<>();
    		fields.put("dataConnection", connection);
    		if(wrapper instanceof Core_DataExtractor) {
    			Core_DataExtractor dataExtractor = (Core_DataExtractor) wrapper;
    			propertiesInjection(dataExtractor, fields);
    			System.out.println("Data extracted ["+connection.getClass().getName()+"]: "+dataExtractor.getData());
    		}
    		
    		System.out.println("-------------------------------------------------");
    		
    	}
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
