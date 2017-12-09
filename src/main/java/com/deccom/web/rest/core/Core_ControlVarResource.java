package com.deccom.web.rest.core;

import java.lang.reflect.Field;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.core.Core_Connection;
import com.deccom.domain.core.Core_ControlVar;
import com.deccom.domain.core.Core_ControlVarCreation;
import com.deccom.domain.core.Core_RESTConnection;
import com.deccom.domain.core.Core_SQLConnection;
import com.deccom.domain.core.Status;
import com.deccom.service.impl.core.Core_ControlVarService;

@RestController
@RequestMapping("/api")
public class Core_ControlVarResource {
	private final Logger log = LoggerFactory
			.getLogger(Core_ControlVarResource.class);

	// private static final String ENTITY_NAME = "Core_ControlVar";

	@Autowired
	private Core_ControlVarService controlvarService;

	/**
	 * Create example core_controlvar
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@GetMapping("/controlvar/example")
	@Timed
	public ResponseEntity<String> exampleControlVars() {
		log.debug("Creating example Core_ControlVar objects");

		Core_RESTConnection rest = new Core_RESTConnection();
		rest.setJsonPath("$[2].phone");
		rest.setUrl("http://jsonplaceholder.typicode.com/users");

		Core_SQLConnection sql = new Core_SQLConnection();
		sql.setUsername("developer");
		sql.setPassword("developer");
		sql.setQuery("select age from author where  idauthor='1' and name='name-1';");
		sql.setUrl("jdbc:mysql://localhost:3306/deccom");

		Core_ControlVar c1 = controlvarService.create();
		c1.setConnection(rest);
		c1.setCreationMoment(LocalDateTime.now());
		c1.setStatus(Status.RUNNING);
		c1.setFrequency_sec(60);
		c1.setName("RESTCONTROLVAR");

		Core_ControlVar c2 = controlvarService.create();
		c2.setConnection(sql);
		c2.setCreationMoment(LocalDateTime.now());
		c2.setStatus(Status.RUNNING);
		c2.setFrequency_sec(30);
		c2.setName("SQLCONTROLVAR");

		controlvarService.save(c1);
		controlvarService.save(c2);

		return ResponseEntity.ok().build();
	}

	@PostMapping("/controlvar/new1")
	@Timed
	public ResponseEntity<String> newControlVars1(
			@RequestBody Core_ControlVar cv) throws IllegalArgumentException,
			IllegalAccessException {
		/**
		 * Development report @AlbertFX91: - It doesnt work - the data is not
		 * injected in the connection
		 */
		log.debug("New Core_ControlVar object 1");
		Core_Connection connection = cv.getConnection();
		System.out.println(cv);
		for (Field field : connection.getClass().getDeclaredFields()) {
			field.setAccessible(true);
			Object value = field.get(connection);
			System.out.println(field.getName() + ": " + value);
		}

		return ResponseEntity.ok().build();
	}

	/*
	 * EXAMPLE CONTROLVAR CREATION DATA FOR SWAGGER { "_connectionClass":
	 * "com.deccom.domain.core.Core_RESTConnection", "connectionData": { "url" :
	 * "http://jsonplaceholder.typicode.com/users", "jsonPath": "$.[0].id" },
	 * "controlvar": { "connection": {}, "creationMoment":
	 * "2017-12-06T21:36:28.081Z", "disabled": true, "entries": [],
	 * "frequency_sec": 20, "name": "exampleCV" } }
	 */
	@PostMapping("/controlvar/new2")
	@Timed
	public ResponseEntity<String> newControlVars2(
			@RequestBody @Valid Core_ControlVarCreation cve)
			throws URISyntaxException {
		/**
		 * Development report @AlbertFX91: + It works - connectionClass name its
		 * neccesary - connectionData dynamic: Its neccesary to verify it after
		 * create a control var
		 */
		log.debug("New Core_ControlVar object 2");
		Core_ControlVar cv = cve.getControlvar();
		String _connectionClass = cve.get_connectionClass();
		Map<String, String> connectionData = cve.getConnectionData();
		Core_Connection connection;

		/* _connectionClass verification */
		try {
			connection = (Core_Connection) Class.forName(_connectionClass)
					.newInstance();
		} catch (InstantiationException | IllegalAccessException
				| ClassNotFoundException e) {
			// TODO Throw specific exception
			return ResponseEntity.badRequest().body(
					"_connectionClass error: " + e.getMessage());
		}

		/* ConnectionData verification */
		Boolean connectionVerification = connectionVerification(connection,
				connectionData);
		if (!connectionVerification) {
			// TODO Throw specific exception
			return ResponseEntity.badRequest().body("connectionData error");
		}

		propertiesInjection(connection, connectionData);
		cv.setConnection(connection);
		controlvarService.save(cv);
		return ResponseEntity.ok().build();
	}

	/**
	 * Get all the core_controlvars.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of core_controlvars
	 */
	@GetMapping("/controlvar/all")
	@Timed
	public Page<Core_ControlVar> findAll(Pageable pageable) {
		log.debug("Request to get all Core_Connection");
		return controlvarService.findAll(pageable);
	}

	/**
	 * Run loadControlVars from db
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InstantiationException
	 */
	@GetMapping("/controlvar/run")
	@Timed
	public ResponseEntity<String> runControlVars()
			throws ClassNotFoundException, IllegalArgumentException,
			IllegalAccessException, InstantiationException {
		log.debug("Run Core_Connection objects");

		controlvarService.run();

		return ResponseEntity.ok().build();
	}

	// TODO Handle error

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

	private static Boolean connectionVerification(Core_Connection connection,
			Map<String, String> connectionData) {
		return Arrays.stream(connection.getClass().getDeclaredFields())
				.allMatch(f -> connectionData.containsKey(f.getName()));
	}
}