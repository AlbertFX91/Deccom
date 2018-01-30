package com.deccom.web.rest.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.core.Core_Connection;

@RestController
@RequestMapping("/api")
public class Core_ConnectionResource {
	private final Logger log = LoggerFactory
			.getLogger(Core_ConnectionResource.class);

	private static final String ENTITY_NAME = "Core_Connection";

	/**
	 * Get all the core_connections.
	 *
	 * @return the list of core_connections
	 */
	@GetMapping("/connection/all")
	@Timed
	public List<Core_Connection> findAll() {
		log.debug("Request to get all Core_Connections");
		Set<Class<? extends Core_Connection>> set = getSubClassesOf(Core_Connection.class);
		for(Class<? extends Core_Connection> c: set) {
			System.out.println(c.getName());
		}
		return new ArrayList<>();
	}
	@SuppressWarnings("unchecked")
	private static <T> Set<Class<? extends T>> getSubClassesOf(Class<T> cls){
		Reflections reflections = new Reflections(
				new ConfigurationBuilder()
				.setUrls(Arrays.asList(ClasspathHelper.forClass(cls))));
		Set<?> subTypes = reflections.getSubTypesOf(cls);
		return subTypes.stream()
				.map((o)->(Class<? extends T>) o)
				.filter((c)->!c.isInterface())
				.collect(Collectors.toSet());
	}
}