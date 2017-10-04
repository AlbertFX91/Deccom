package com.deccom.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
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
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTDataRecover;
import com.deccom.service.RESTControlVarService;
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

	private final RESTControlVarService restControlVarService;

	public RESTResource(RESTService restService,
			RESTControlVarService restControlVarService) {
		this.restService = restService;
		this.restControlVarService = restControlVarService;
	}

	/**
	 * GET /nomapping : send a HTTP GET request to an URL.
	 *
	 * @param url
	 *            the url to send the request to
	 * @param pageable
	 *            the pagination information
	 * @return the ResponseEntity with status 200 (OK) and the json in body
	 */
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

	/**
	 * GET /mapping : send a HTTP GET request to an URL.
	 *
	 * @param url
	 *            the url to send the request to
	 * @return the ResponseEntity with status 200 (OK) and the json in body
	 */
	@GetMapping("/rest/mapping")
	@Timed
	public ResponseEntity<List<Post>> mapping(@RequestParam String url)
			throws Exception {

		log.debug("REST request with mapping");

		List<Post> result;

		result = restService.mapping(url);

		return ResponseEntity.ok().body(result);

	}

	/**
	 * GET /query : get a query from a JSON.
	 *
	 * @param url
	 *            the url where the JSON came from
	 * @param jsonPath
	 *            the path to create the query
	 * @return the ResponseEntity with status 200 (OK) and the query in body
	 */
	@GetMapping("/rest/query")
	@Timed
	public ResponseEntity<String> query(@RequestParam String url,
			@RequestParam String jsonPath) throws Exception {

		log.debug("REST request with query");

		String result;

		result = restService.getByJsonPath(url, jsonPath);

		return ResponseEntity.ok().body(result);

	}

	/**
	 * POST /datarecover : create a new RESTControlVar.
	 *
	 * @param restDataRecover
	 *            the RESTDataRecover to create the new RESTControlVar from
	 * @return the ResponseEntity with status 200 (OK) and the RESTControlVar in
	 *         body
	 */
	@PostMapping("/rest/datarecover")
	@Timed
	public ResponseEntity<RESTControlVar> restDataRecover(
			@Valid @RequestBody RESTDataRecover restDataRecover)
			throws URISyntaxException {

		log.debug("REST request to save RESTDataRecover : {}", restDataRecover);

		RESTControlVar result;

		RESTControlVar aux = restControlVarService.create(restDataRecover);

		result = restControlVarService.save(aux);

		// return ResponseEntity.ok().build();

		return ResponseEntity
				.created(new URI("/api/rest/datarecover/" + result.getId()))
				.headers(
						HeaderUtil.createEntityCreationAlert("RESTControlVar",
								result.getId().toString())).body(result);

	}

	/**
	 * GET /atomic : check the status of a variable creating new entries
	 *
	 * @param query
	 *            the sentence from the connection
	 * @return the ResponseEntity with status 200 (OK) and the query's data
	 */
	@GetMapping("/rest/atomic")
	@Timed
	public ResponseEntity<String> atomic() {
		
		log.debug("REST request to atomic");
		restControlVarService.monitorize();
		return ResponseEntity.ok().body("OK");
		
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
