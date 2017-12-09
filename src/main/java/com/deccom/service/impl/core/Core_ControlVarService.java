package com.deccom.service.impl.core;

import java.lang.annotation.Annotation;
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
import com.deccom.domain.core.Status;
import com.deccom.domain.core.annotation.Core_Extractor;
import com.deccom.repository.core.Core_ControlVarRepository;

@Service
public class Core_ControlVarService {
	private final Logger log = LoggerFactory
			.getLogger(Core_ControlVarService.class);

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
		result.setStatus(Status.RUNNING);
		result.setEntries(new ArrayList<>());

		return result;
	}

	public Core_ControlVar save(Core_ControlVar c) {
		Core_ControlVar cv = controlVarRepository.save(c);
		schedulingService.newJob(cv);
		return cv;
	}

	public Page<Core_ControlVar> findAll(Pageable pageable) {
		log.debug("Request to get all Core_Connection");
		return controlVarRepository.findAll(pageable);
	}

	public List<Core_ControlVar> findAll() {
		log.debug("Request to get all Core_Connection");
		return controlVarRepository.findAll();
	}

	public void run() {
		log.debug("Request to run all Core_Connection");
		List<Core_ControlVar> controlVars = controlVarRepository.findAll();
		for (Core_ControlVar cv : controlVars) {
			executeMonitorize(cv);
		}
	}

	public void executeMonitorize(Core_ControlVar controlVar) {
		Core_DataExtractor dataExtractor;
		Core_Connection connection;
		String value;

		connection = controlVar.getConnection();
		if (hasExtractor(connection)) {
			dataExtractor = getExtractorByConnection(connection);
			dataExtractor = injectConnectionInExtractor(dataExtractor,
					connection);
			value = dataExtractor.getData();
			addEntry(controlVar, value);
			log.debug("Data extracted [" + controlVar.getName() + "]: " + value);
		} else {
			// TODO This is when a class has not the annotation @Extractor
			// implemented. It'll throw an exception
			System.out
					.println("Error. Connection has not the annotation @Extractor");
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

	private static <T> void propertiesInjection(Object o,
			Map<String, T> properties) {
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

	private Boolean hasExtractor(Core_Connection connection) {
		return connection.getClass().isAnnotationPresent(Core_Extractor.class);
	}

	private Core_DataExtractor injectConnectionInExtractor(
			Core_DataExtractor extractor, Core_Connection connection) {
		Map<String, Core_Connection> fields = new HashMap<>();
		fields.put("dataConnection", connection);
		propertiesInjection(extractor, fields);
		return extractor;
	}

	private Core_DataExtractor getExtractorByConnection(
			Core_Connection connection) {
		Object wrapper;
		Annotation annotation = connection.getClass().getAnnotation(
				Core_Extractor.class);
		Core_Extractor core_extractor = (Core_Extractor) annotation;
		try {
			wrapper = core_extractor.value().newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO This is when the class its an interface, an abstract class,
			// or its private.
			// So, if its happens, its a programming error. It will throw an
			// exception
			e.printStackTrace();
			System.out.println("Error. Cannot instanciate the extractor");
			return null;
		}
		if (wrapper instanceof Core_DataExtractor) {
			return (Core_DataExtractor) wrapper;
		} else {
			// TODO This is when the class in @Extractor does not extends from
			// Core_DataExtractor, but its controlled by the Core_Extractor
			// interface.
			// If its happens, but its unlikely, it will throw an exception
			System.out
					.println("Error. Wrapper is not an instance of Core_DataExtractor");
			return null;
		}
	}
}
