package com.deccom.web.rest.core;

import java.net.URISyntaxException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.core.ControlVariable;
import com.deccom.domain.core.extractor.RESTExtractor;
import com.deccom.domain.core.extractor.SQLExtractor;
import com.deccom.service.core.ControlVariableService;
import com.deccom.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ControlVariableResource {

	private final Logger log = LoggerFactory.getLogger(ControlVariableResource.class);
	
	private static final String ENTITY_NAME = "ControlVariable";

	@Autowired
	private ControlVariableService controlVariableService;

	/**
	 * Create example core_controlvar
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@GetMapping("/controlvariable/create")
	@Timed
	public ResponseEntity<String> create() {
		log.debug("Creating example controlVariable objects");

		ControlVariable restControlVariable = controlVariableService.create();
		ControlVariable sqlControlVariable = controlVariableService.create();
		RESTExtractor restExtractor = new RESTExtractor();
		SQLExtractor sqlExtractor = new SQLExtractor();

		restControlVariable.setName("restControlVariable");
		sqlControlVariable.setName("sqlControlVariable");
		restControlVariable.setFrequency(30);
		sqlControlVariable.setFrequency(15);
		restExtractor.setUrl("http://jsonplaceholder.typicode.com/users");
		restExtractor.setJsonPath("$[2].phone");
		sqlExtractor.setUsername("developer");
		sqlExtractor.setPassword("developer");
		sqlExtractor.setUrl("localhost:3306/deccom");
		sqlExtractor.setQuery("select age from author where  idauthor='1' and name='name-1';");
		sqlExtractor.setJdbc("mysql");
		restControlVariable.setExtractor(restExtractor);
		sqlControlVariable.setExtractor(sqlExtractor);

		controlVariableService.save(restControlVariable);
		controlVariableService.save(sqlControlVariable);

		return ResponseEntity.ok().build();
	}
	
	/**
	 * Test core_controlvar
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@GetMapping("/controlvariable/test")
	@Timed
	public ResponseEntity<String> test() {
		log.debug("Printing controlVariable data");

		controlVariableService.testLaunchCVS();
		
		return ResponseEntity.ok().build();
	}
//
//	@PostMapping("/controlvar/new1")
//	@Timed
//	public ResponseEntity<String> newControlVars1(
//			@RequestBody Core_ControlVar cv) throws IllegalArgumentException,
//			IllegalAccessException {
//		/**
//		 * Development report @AlbertFX91: - It doesnt work - the data is not
//		 * injected in the connection
//		 */
//		log.debug("New Core_ControlVar object 1");
//		Core_Connection connection = cv.getConnection();
//		System.out.println(cv);
//		for (Field field : connection.getClass().getDeclaredFields()) {
//			field.setAccessible(true);
//			Object value = field.get(connection);
//			System.out.println(field.getName() + ": " + value);
//		}
//
//		return ResponseEntity.ok().build();
//	}
//
//	/*
//	 * EXAMPLE CONTROLVAR CREATION DATA FOR SWAGGER 
//	 { 
//	 	"_connectionClass":
//	 	"com.deccom.domain.core.Core_RESTConnection", "connectionData": { "url" :
//	 	"http://jsonplaceholder.typicode.com/users", "jsonPath": "$.[0].id" },
//	 	"controlvar": { "connection": {}, "creationMoment":
//	 	"2017-12-06T21:36:28.081Z", "disabled": true, "entries": [],
//	 	"frequency_sec": 20, "name": "exampleCV" } }
//	 */
//	@PostMapping("/controlvar/new2")
//	@Timed
//	public ResponseEntity<String> newControlVars2(
//			@RequestBody @Valid Core_ControlVarCreation cve)
//			throws URISyntaxException {
//		/**
//		 * Development report @AlbertFX91: + It works - connectionClass name its
//		 * neccesary - connectionData dynamic: Its neccesary to verify it after
//		 * create a control var
//		 */
//		log.debug("New Core_ControlVar object 2");
//		Core_ControlVar cv = cve.getControlvar();
//		String _connectionClass = cve.get_connectionClass();
//		Map<String, String> connectionData = cve.getConnectionData();
//		Core_Connection connection;
//
//		/* _connectionClass verification */
//		try {
//			connection = (Core_Connection) Class.forName(_connectionClass)
//					.newInstance();
//		} catch (InstantiationException | IllegalAccessException
//				| ClassNotFoundException e) {
//			// TODO Throw specific exception
//			return ResponseEntity.badRequest().body(
//					"_connectionClass error: " + e.getMessage());
//		}
//
//		/* ConnectionData verification */
//		Boolean connectionVerification = connectionVerification(connection,
//				connectionData);
//		if (!connectionVerification) {
//			// TODO Throw specific exception
//			return ResponseEntity.badRequest().body("connectionData error");
//		}
//
//		propertiesInjection(connection, connectionData);
//		cv.setConnection(connection);
//		controlvarService.save(cv);
//		return ResponseEntity.ok().build();
//	}
//	
//	/*
//	 * EXAMPLE CONTROLVAR CREATION DATA FOR SWAGGER 
//	{
//	  "connection": {
//	     "_connectionClass": "com.deccom.domain.core.rest.Core_RESTConnection",
//	     "url": "http://jsonplaceholder.typicode.com/users", 
//	     "jsonPath": "$.[0].id" 
//	  },
//	  "creationMoment": "2017-12-26T23:55:53.793Z",
//	  "frequency_sec": 30,
//          "entries": [],
//	  "name": "awesomeCV",
//	  "status": "RUNNING"
//	}
//	 */
//	@PostMapping("/controlvar/new3")
//	@Timed
//	public ResponseEntity<String> newControlVar3(
//			@RequestBody @Valid Core_ControlVar cv)
//			throws URISyntaxException {
//		/**
//		 * Development report @AlbertFX91: + It works - connectionClass name its
//		 * mandatory - connectionData dynamic: Its mandatory to verify it after
//		 * create a control var
//		 */
//		log.debug("New Core_ControlVar object 3");
//		controlvarService.save(cv);
//		return ResponseEntity.ok().build();
//	}

	/**
	 * Get all the controlvars.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of core_controlvars
	 */
	@GetMapping("/controlvar/all")
	@Timed
	public Page<ControlVariable> findAll(Pageable pageable) {
		log.debug("Request to get all Core_Connection");
		return controlVariableService.findAll(pageable);
	}

	/**
	 * PUT /controlvar/pause : Pauses a running control var.
	 *
	 * @param controlVarId
	 *            the id of the control var to update
	 * @return the ResponseEntity with status 200 (OK) and with body the paused
	 *         control var, or with status 400 (Bad Request) if the control var
	 *         is not valid, or with status 500 (Internal Server Error) if the
	 *         control var couldn't be paused
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/controlvar/pause")
	@Timed
	public ResponseEntity<ControlVariable> pause(String controlVarId)
			throws URISyntaxException {
		log.debug("REST request to pause Core_ControlVar with id: {}",
				controlVarId);
		ControlVariable result = controlVariableService.pause(controlVarId);
		return ResponseEntity
				.ok()
				.headers(
						HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
								controlVarId.toString())).body(result);
	}

	/**
	 * PUT /controlvar/pause : Restarts a paused control var.
	 *
	 * @param controlVarId
	 *            the id of the control var to update
	 * @return the ResponseEntity with status 200 (OK) and with body the
	 *         restartd control var, or with status 400 (Bad Request) if the
	 *         control var is not valid, or with status 500 (Internal Server
	 *         Error) if the control var couldn't be started
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/controlvar/restart")
	@Timed
	public ResponseEntity<ControlVariable> restart(String controlVarId)
			throws URISyntaxException {
		log.debug("REST request to restart Core_ControlVar with id: {}",
				controlVarId);
		ControlVariable result = controlVariableService.restart(controlVarId);
		return ResponseEntity
				.ok()
				.headers(
						HeaderUtil.createEntityUpdateAlert(ENTITY_NAME,
								controlVarId.toString())).body(result);
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

		controlVariableService.run();

		return ResponseEntity.ok().build();
	}
	
//	/**
//	 * Get all available controlvars to create
//	 *
//	 * @return the list of controlvars to create
//	 * @throws IllegalAccessException 
//	 * @throws InstantiationException 
//	 */
//	@GetMapping("/controlvar/available")
//	@Timed
//	public List<Core_DataExtractor> available() throws InstantiationException, IllegalAccessException {
//		log.debug("Request to get all Controlvars to create");
//		List<Core_DataExtractor> res = controlVariableService.getAvailableExtractors();
//		return res;
//	}
	
	
//	// TODO Handle error
//	private static <T> void propertiesInjection(Object o,
//			Map<String, T> properties) {
//		for (Entry<String, T> property : properties.entrySet()) {
//			String f = property.getKey();
//			T v = property.getValue();
//			Class<?> clazz = o.getClass();
//			try {
//				Field field = clazz.getDeclaredField(f);
//				field.setAccessible(true);
//				field.set(o, v);
//			} catch (NoSuchFieldException e) {
//				clazz = clazz.getSuperclass();
//			} catch (Exception e) {
//				throw new IllegalStateException(e);
//			}
//		}
//	}
//	
//	private static Boolean connectionVerification(Core_Connection connection,
//			Map<String, String> connectionData) {
//		return Arrays.stream(connection.getClass().getDeclaredFields())
//				.allMatch(f -> connectionData.containsKey(f.getName()));
//	}
	

}
