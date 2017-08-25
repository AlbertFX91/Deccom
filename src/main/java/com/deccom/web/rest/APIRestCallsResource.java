package com.deccom.web.rest;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Post;
import com.deccom.service.APIRestCallsService;

/**
 * REST controller for managing APIRestCalls.
 */
@RestController
@RequestMapping("/api")
public class APIRestCallsResource {

	private final Logger log = LoggerFactory
			.getLogger(APIRestCallsResource.class);

	private final APIRestCallsService apiRestCallsService;

	public APIRestCallsResource(APIRestCallsService apiRestCallsService) {
		
		this.apiRestCallsService = apiRestCallsService;
		
	}

	@GetMapping("/apirestcalls/nomapping")
	@Timed
	public ResponseEntity<List<Map<String, String>>> noMapping() throws Exception {
		
		log.debug("REST request without mapping");
		
		List<Map<String, String>> result;
		
		result = apiRestCallsService.noMapping();
		
		return ResponseEntity.ok().body(result);
		
	}

	@GetMapping("/apirestcalls/mapping")
	@Timed
	public ResponseEntity<List<Post>> mapping() throws Exception {
		
		log.debug("REST request with mapping");
		
		List<Post> result;
		
		result = apiRestCallsService.mapping();
		
		return ResponseEntity.ok().body(result);
		
	}

}
