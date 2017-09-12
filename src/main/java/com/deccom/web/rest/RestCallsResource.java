package com.deccom.web.rest;

import io.swagger.annotations.ApiParam;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Post;
import com.deccom.service.RestCallsService;
import com.deccom.service.impl.util.RestCallsServiceException;
import com.deccom.web.rest.util.HeaderUtil;

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
	public ResponseEntity<String> noMapping(@RequestParam String url,
			@ApiParam Pageable pageable) throws Exception {

		log.debug("REST request without mapping");

		String result;

		result = restCallsService.noMapping(url, pageable);

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

	@GetMapping("/restcalls/query")
	@Timed
	public ResponseEntity<String> query(@RequestParam String url,
			@RequestParam String jsonPath) throws Exception {

		log.debug("REST request with query");

		String result;

		result = restCallsService.getByJsonPath(url, jsonPath);

		return ResponseEntity.ok().body(result);

	}

	@ExceptionHandler(RestCallsServiceException.class)
	public ResponseEntity<String> panic(RestCallsServiceException oops) {
		return ResponseEntity
				.badRequest()
				.headers(
						HeaderUtil.createFailureAlert(oops.getEntity(),
								oops.getI18nCode(), oops.getMessage()))
				.body(null);
	}

}
