package com.deccom.web.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Post;
import com.deccom.domain.RESTDataRecover;
import com.deccom.service.RESTService;
import com.deccom.service.impl.util.RESTServiceException;
import com.deccom.web.rest.util.HeaderUtil;
import com.deccom.web.rest.util.PaginationUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing RestCalls.
 */
@RestController
@RequestMapping("/api")
public class RESTResource {

	private final Logger log = LoggerFactory.getLogger(RESTResource.class);

	private final RESTService restService;

	public RESTResource(RESTService restCallsService) {
		this.restService = restCallsService;
	}

	@GetMapping("/rest/nomapping")
	@Timed
	public ResponseEntity<String> noMapping(@RequestParam String url,
			@ApiParam Pageable pageable) throws Exception {

		log.debug("REST request without mapping");

		Page<String> result;
		result = restService.noMapping(url, pageable);
		HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
				result, "/api/rest/nomapping");
		return new ResponseEntity<>(result.getContent().toString(), headers,
				HttpStatus.OK);

	}

	@GetMapping("/rest/mapping")
	@Timed
	public ResponseEntity<List<Post>> mapping(@RequestParam String url)
			throws Exception {

		log.debug("REST request with mapping");

		List<Post> result;

		result = restService.mapping(url);

		return ResponseEntity.ok().body(result);

	}

	@GetMapping("/rest/query")
	@Timed
	public ResponseEntity<String> query(@RequestParam String url,
			@RequestParam String jsonPath) throws Exception {

		log.debug("REST request with query");

		String result;

		result = restService.getByJsonPath(url, jsonPath);

		return ResponseEntity.ok().body(result);

	}

	@PostMapping("/rest/datarecover")
	@Timed
	public ResponseEntity<Void> restDataRecover(
			@Valid @RequestBody RESTDataRecover restDataRecover) {

		log.debug("REST request to save RESTDataRecover : {}", restDataRecover);

		return ResponseEntity.ok().build();

	}

	@ExceptionHandler(RESTServiceException.class)
	public ResponseEntity<String> panic(RESTServiceException oops) {
		return ResponseEntity
				.badRequest()
				.headers(
						HeaderUtil.createFailureAlert(oops.getEntity(),
								oops.getI18nCode(), oops.getMessage()))
				.body(null);
	}

}
