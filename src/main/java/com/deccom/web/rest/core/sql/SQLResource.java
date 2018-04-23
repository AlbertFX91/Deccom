package com.deccom.web.rest.core.sql;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.service.impl.sql.SQLQuery;
import com.deccom.service.impl.sql.SQLResponse;
import com.deccom.service.impl.sql.SQLServiceException;
import com.deccom.service.impl.sql.SQLUtil;
import com.deccom.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Acme.
 */
@RestController
@RequestMapping("/api")
public class SQLResource {

	private final Logger log = LoggerFactory.getLogger(SQLResource.class);

	public SQLResource() {
	}

	/**
	 * POST /db/query : make a connection and a query to a database
	 *
	 * @param query
	 *            the connection and query data
	 * @return the ResponseEntity with status 200 (OK) and the query's data
	 */
	@PostMapping("/sql/query")
	@Timed
	public ResponseEntity<SQLResponse> query(@Valid @RequestBody SQLQuery query) {
		log.debug("REST request to query a DB");
		SQLResponse res = SQLUtil.query(query);
		return ResponseEntity.ok().body(res);
	}
	

	@ExceptionHandler(SQLServiceException.class)
	public ResponseEntity<String> panic(SQLServiceException oops) {
		return ResponseEntity.badRequest()
				.headers(HeaderUtil.createFailureAlert(oops.getEntity(), oops.getI18nCode(), oops.getMessage()))
				.body(null);
	}
}
