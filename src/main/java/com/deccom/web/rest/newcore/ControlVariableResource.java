package com.deccom.web.rest.newcore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.newcore.domain.ControlVariable;
import com.deccom.newcore.domain.RESTExtractor;
import com.deccom.newcore.domain.SQLExtractor;
import com.deccom.service.newcore.ControlVariableService;

@RestController
@RequestMapping("/api")
public class ControlVariableResource {

	private final Logger log = LoggerFactory.getLogger(ControlVariableResource.class);

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
	

}
