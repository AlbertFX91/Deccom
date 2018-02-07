package com.deccom.service.core;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.core.ControlVariable;
import com.deccom.domain.core.ControlVariableEntry;
import com.deccom.domain.core.Status;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.repository.core.ControlVariableRepository;
import com.deccom.service.core.util.ControlVariableServiceException;

@Service
public class ControlVariableService {

	private final Logger log = LoggerFactory.getLogger(ControlVariableService.class);
	private static final String i18nCodeRoot = "operations.controlvariable";

	@Autowired
	private ControlVariableRepository controlVariableRepository;

	@Autowired
	private ControlVariableSchedulingService schedulingService;

	/**
	 * Create a ControlVariable.
	 *
	 * @return the entity
	 */
	public ControlVariable create() {
		ControlVariable result = new ControlVariable();

		result.setCreationMoment(LocalDateTime.now());
		result.setStatus(Status.RUNNING);
		result.setControlVarEntries(new ArrayList<>());

		return result;
	}

	public ControlVariable save(ControlVariable controlVariable) {

		try {

			Integer value;

			value = controlVariable.getExtractor().getData();

			ControlVariable cv = controlVariableRepository.save(controlVariable);

			if (cv.getStatus().equals(Status.RUNNING) && !schedulingService.isRunning(cv))
				schedulingService.newJob(cv);

			return cv;

		} catch (NumberFormatException e) {
			throw new ControlVariableServiceException("Data must be an integer number", i18nCodeRoot + ".nointeger",
					"ControlVariableService", e);
		}

	}

	public ControlVariable findOne(String id) {
		log.debug("Request to get ControlVariable : {}", id);
		return controlVariableRepository.findOne(id);
	}

	public Page<ControlVariable> findAll(Pageable pageable) {
		log.debug("Request to get all Core_Connection");
		return controlVariableRepository.findAll(pageable);
	}

	public List<ControlVariable> findAll() {
		log.debug("Request to get all Core_Connection");
		return controlVariableRepository.findAll();
	}

	public void testLaunchCVS() {
		for (ControlVariable cv : findAll()) {
			System.out.println(cv.getName() + ": " + cv.getExtractor().getData());
		}
	}

	public void run() {
		log.debug("Request to run all Core_Connection");
		List<ControlVariable> controlVars = controlVariableRepository.findAll();
		for (ControlVariable cv : controlVars) {
			executeMonitorize(cv);
		}
	}

	public void executeMonitorize(ControlVariable controlVar) {
		if (controlVar.getStatus() == Status.RUNNING) {
			Integer value;
			ControlVariableExtractor extractor;
			extractor = controlVar.getExtractor();
			try {
				value = extractor.getData();
				addEntry(controlVar, value);
				log.debug("Data extracted [" + controlVar.getName() + "]: " + value);
			} catch (Throwable e) {
				System.out.println("EXTRACTOR ERROR");
				controlVar.setStatus(Status.BLOCKED);
				controlVariableRepository.save(controlVar);
				schedulingService.stopJob(controlVar);
			}
		}
	}

	public ControlVariableEntry addEntry(ControlVariable cv, Integer value) {
		ControlVariableEntry entry = new ControlVariableEntry();
		entry.setCreationMoment(LocalDateTime.now());
		entry.setValue(value);
		cv.getControlVarEntries().add(entry);
		controlVariableRepository.save(cv);
		return entry;
	}

	public ControlVariable pause(String controlVarId) {
		ControlVariable controlVar;

		controlVar = findOne(controlVarId);

		if (controlVar.getStatus().equals(Status.RUNNING)) {
			controlVar.setStatus(Status.PAUSED);
			schedulingService.stopJob(controlVar);
		}

		return controlVariableRepository.save(controlVar);
	}

	public ControlVariable restart(String controlVarId) {
		ControlVariable controlVar;

		controlVar = findOne(controlVarId);

		if (controlVar.getStatus().equals(Status.PAUSED)) {
			controlVar.setStatus(Status.RUNNING);
			schedulingService.newJob(controlVar);
		}

		return controlVariableRepository.save(controlVar);
	}
	/*
	 * /** List the available extractors by the connection objects created. We can't
	 * list an extractor if there are not a connection object for the extractor
	 * 
	 * @return
	 * 
	 * @throws InstantiationException
	 * 
	 * @throws IllegalAccessException
	 */
	/*
	 * public List<Core_DataExtractor> getAvailableExtractors() throws
	 * InstantiationException, IllegalAccessException { List<Core_DataExtractor> res
	 * = new ArrayList<>(); // Getting the parent Core_Connection class
	 * Class<Core_Connection> connection = Core_Connection.class; // Recovering the
	 * subclases of the parent Core_Connection [Ex: REST_Connection, SQL_Connection,
	 * etc...] Set<Class<? extends Core_Connection>> available =
	 * getSubClassesOf(connection); for(Class<? extends Core_Connection> c:
	 * available) { Core_Connection cn = c.newInstance(); // If the connection has
	 * an extractor configurated if(hasExtractor(cn)) { // Creation of an extractor
	 * instance Core_DataExtractor extractor = getExtractorByConnection(cn); // If
	 * it has the attribute style setted if(extractor.getStyle()!=null) {
	 * res.add(extractor); } } } return res; }
	 * 
	 * 
	 * private static <T> void propertiesInjection(Object o, Map<String, T>
	 * properties) { for (Entry<String, T> property : properties.entrySet()) {
	 * String f = property.getKey(); T v = property.getValue(); Class<?> clazz =
	 * o.getClass(); try { Field field = clazz.getDeclaredField(f);
	 * field.setAccessible(true); field.set(o, v); } catch (NoSuchFieldException e)
	 * { clazz = clazz.getSuperclass(); } catch (Exception e) { throw new
	 * IllegalStateException(e); } } }
	 * 
	 * private Boolean hasExtractor(Core_Connection connection) { return
	 * connection.getClass().isAnnotationPresent(Core_Extractor.class); }
	 * 
	 * private Core_DataExtractor injectConnectionInExtractor( Core_DataExtractor
	 * extractor, Core_Connection connection) { Map<String, Core_Connection> fields
	 * = new HashMap<>(); fields.put("dataConnection", connection);
	 * propertiesInjection(extractor, fields); return extractor; }
	 * 
	 * private Core_DataExtractor<Core_Connection> getExtractorByConnection(
	 * Core_Connection connection) { Object wrapper; Annotation annotation =
	 * connection.getClass().getAnnotation( Core_Extractor.class); Core_Extractor
	 * core_extractor = (Core_Extractor) annotation; try { wrapper =
	 * core_extractor.value().newInstance(); } catch (InstantiationException |
	 * IllegalAccessException e) { // TODO This is when the class its an interface,
	 * an abstract class, // or its private. // So, if its happens, its a
	 * programming error. It will throw an // exception e.printStackTrace();
	 * System.out.println("Error. Cannot instanciate the extractor"); return null; }
	 * if (wrapper instanceof Core_DataExtractor) { return
	 * (Core_DataExtractor<Core_Connection>) wrapper; } else { // TODO This is when
	 * the class in @Extractor does not extends from // Core_DataExtractor, but its
	 * controlled by the Core_Extractor // interface. // If its happens, but its
	 * unlikely, it will throw an exception System.out
	 * .println("Error. Wrapper is not an instance of Core_DataExtractor"); return
	 * null; } }
	 * 
	 * private static <T> Set<Class<? extends T>> getSubClassesOf(Class<T> cls){
	 * Reflections reflections = new Reflections( new ConfigurationBuilder()
	 * .setUrls(Arrays.asList(ClasspathHelper.forClass(cls)))); Set<?> subTypes =
	 * reflections.getSubTypesOf(cls); return subTypes.stream() .map((o)->(Class<?
	 * extends T>) o) .filter((c)->!c.isInterface()) .collect(Collectors.toSet()); }
	 */
}
