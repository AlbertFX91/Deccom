package com.deccom.web.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Post;
import com.deccom.service.RestCallsService;

/**
 * REST controller for managing RestCalls.
 */
@RestController
@RequestMapping("/api")
public class RestCallsResource {

	private final Logger log = LoggerFactory.getLogger(RestCallsResource.class);

	private final RestCallsService restCallsService;

	public RestCallsResource(RestCallsService restCallsService) {
		this.restCallsService = restCallsService;
	}

	@GetMapping("/restcalls/nomapping")
	@Timed
	public ResponseEntity<String> noMapping(@RequestParam String url)
			throws Exception {

		log.debug("REST request without mapping");

		String result;

		result = restCallsService.noMapping(url);

		return ResponseEntity.ok().body(result);

	}

	@GetMapping("/restcalls/mapping")
	@Timed
	public ResponseEntity<List<Post>> mapping(@RequestParam String url)
			throws Exception {

		log.debug("REST request with mapping");

		List<Post> result;

		result = restCallsService.mapping(url);

		return ResponseEntity.ok().body(result);

	}

}
