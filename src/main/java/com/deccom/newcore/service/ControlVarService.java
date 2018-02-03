package com.deccom.newcore.service;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.newcore.domain.ControlVariable;
import com.deccom.newcore.domain.Status;
import com.deccom.newcore.repository.ControlVariableRepository;

@Service
public class ControlVarService {

	private final Logger log = LoggerFactory.getLogger(ControlVarService.class);

	@Autowired
	private ControlVariableRepository controlVariableRepository;

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
		ControlVariable cv = controlVariableRepository.save(controlVariable);
		return cv;
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

}
