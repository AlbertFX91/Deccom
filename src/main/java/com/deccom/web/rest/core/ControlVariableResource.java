package com.deccom.web.rest.core;

import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.core.ControlVariable;
import com.deccom.domain.core.extractor.rest.RESTExtractor;
import com.deccom.domain.core.extractor.sql.SQLExtractor;
import com.deccom.domain.core.wrapper.New_ControlVariable;
import com.deccom.service.core.ControlVariableService;
import com.deccom.service.core.util.ControlVariableServiceException;
import com.deccom.web.rest.util.HeaderUtil;

@RestController
@RequestMapping("/api")
public class ControlVariableResource {

	private final Logger log = LoggerFactory.getLogger(ControlVariableResource.class);

	private static final String ENTITY_NAME = "ControlVar";

	@Autowired
	private ControlVariableService controlVariableService;

	/**
	 * Create example core_controlvar
	 *
	 * 
	 * @return the ResponseEntity with status 200 (OK)
	 */
	@GetMapping("/controlvar/create")
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
		sqlExtractor.setQuery("select age from author where idauthor='1' and name='name-1';");
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
	@GetMapping("/controlvar/test")
	@Timed
	public ResponseEntity<String> test() {
		log.debug("Printing controlVariable data");

		controlVariableService.testLaunchCVS();

		return ResponseEntity.ok().build();
	}

	@PostMapping("/controlvar")
	@Timed
	public ResponseEntity<ControlVariable> newControlVar(@RequestBody @Valid New_ControlVariable cv)
			throws URISyntaxException {
		log.debug("REST request to save ControlVariable: {}", cv);

		ControlVariable res = controlVariableService.convert(cv);
		res = controlVariableService.save(res);

		return ResponseEntity.created(new URI("/controlvar" + res.getId()))
				.headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, res.getId().toString())).body(res);
	}

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
	 * Get all the controlvars with a limited number of entries.
	 *
	 * @param pageable
	 *            the pagination information
	 * @param numberOfEntries
	 *            the number of entries included in each controlvar
	 * @return the list of core_controlvars with a limited number of entries
	 */
	@GetMapping("/controlvar/all_limited/{numberOfEntries}")
	@Timed
	public Page<ControlVariable> findAllLimitedNumberOfEntries(Pageable pageable,
			@PathVariable Integer numberOfEntries) {
		log.debug("Request to get all CVs with limited entries");
		return controlVariableService.findAllLimitedNumberOfEntries(pageable, numberOfEntries);
	}

	/**
	 * Get all the controlvars with a limited number of entries.
	 *
	 * @param pageable
	 *            the pagination information
	 * @param numberOfEntries
	 *            the number of entries included in each controlvar
	 * @return the list of core_controlvars with a limited number of entries
	 */
	@GetMapping("/controlvar/all_limited_query/{numberOfEntries}")
	@Timed
	public Page<ControlVariable> findAllLimitedNumberOfEntriesQuery(Pageable pageable,
			@PathVariable Integer numberOfEntries) {
		log.debug("Request to get all CVs with limited entries");
		return controlVariableService.findAllLimitedNumberOfEntriesQuery(pageable, numberOfEntries);
	}

	/**
	 * Get the running control variables with their entries between two dates.
	 *
	 * @param pageable
	 *            the pagination information
	 * @param startingDate
	 *            the starting date in the range
	 * @return the list of running control variables with their entries between two
	 *         dates
	 * @throws ParseException
	 */
	@GetMapping("/controlvar/dates")
	@Timed
	public Page<ControlVariable> findRunningControlVariablelsBetweenDates(Pageable pageable, String startingDate)
			throws ParseException {
		log.debug("Request to get the running CVs between two dates");
		return controlVariableService.findRunningControlVariablelsBetweenDates(pageable, startingDate);
	}

	/**
	 * PUT /controlvar/pause : Pauses a running control var.
	 *
	 * @param controlVarId
	 *            the id of the control var to update
	 * @return the ResponseEntity with status 200 (OK) and with body the paused
	 *         control var, or with status 400 (Bad Request) if the control var is
	 *         not valid, or with status 500 (Internal Server Error) if the control
	 *         var couldn't be paused
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/controlvar/pause")
	@Timed
	public ResponseEntity<ControlVariable> pause(String controlVarId) throws URISyntaxException {
		log.debug("REST request to pause Core_ControlVar with id: {}", controlVarId);
		ControlVariable result = controlVariableService.pause(controlVarId);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controlVarId.toString()))
				.body(result);
	}

	/**
	 * PUT /controlvar/pause : Restarts a paused control var.
	 *
	 * @param controlVarId
	 *            the id of the control var to update
	 * @return the ResponseEntity with status 200 (OK) and with body the restartd
	 *         control var, or with status 400 (Bad Request) if the control var is
	 *         not valid, or with status 500 (Internal Server Error) if the control
	 *         var couldn't be started
	 * @throws URISyntaxException
	 *             if the Location URI syntax is incorrect
	 */
	@PutMapping("/controlvar/restart")
	@Timed
	public ResponseEntity<ControlVariable> restart(String controlVarId) throws URISyntaxException {
		log.debug("REST request to restart Core_ControlVar with id: {}", controlVarId);
		ControlVariable result = controlVariableService.restart(controlVarId);
		return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, controlVarId.toString()))
				.body(result);
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
			throws ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InstantiationException {
		log.debug("Run Core_Connection objects");

		controlVariableService.run();

		return ResponseEntity.ok().build();
	}

	@ExceptionHandler(ControlVariableServiceException.class)
	public ResponseEntity<String> panic(ControlVariableServiceException oops) {
		return ResponseEntity.badRequest()
				.headers(HeaderUtil.createFailureAlert(oops.getEntity(), oops.getI18nCode(), oops.getMessage()))
				.body("{ \"error\": \"" + oops.getMessage() + "\" }");
	}

}
