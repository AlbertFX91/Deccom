package com.deccom.web.rest;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Author;
import com.deccom.domain.DBQuery;
import com.deccom.service.DBService;
import com.deccom.service.impl.util.DBServiceException;
import com.deccom.web.rest.util.HeaderUtil;

import io.swagger.annotations.ApiParam;

/**
 * REST controller for managing Acme.
 */
@RestController
@RequestMapping("/api")
public class DBResource {

    private final Logger log = LoggerFactory.getLogger(DBResource.class);


    private final DBService dBService;

    public DBResource(DBService dBService) {
        this.dBService = dBService;
    }

    @GetMapping("/db/nomapping")
    @Timed
    public ResponseEntity<List<Map<String, String>>> callDBNoMapping() {
        log.debug("REST request to test DB");
        List<Map<String, String>> res = dBService.callNoMapping();
        return ResponseEntity.ok().body(res);
    }

    @GetMapping("/db/mapping")
    @Timed
    public ResponseEntity<List<Author>> callDBMapping(){
        log.debug("REST request to test DB");
        List<Author> res = dBService.callMapping();
        return ResponseEntity.ok().body(res);
    }

    /**
     * POST  /db/query : make a connection and a query to a database
     *
     * @param query the connection and query data
     * @return the ResponseEntity with status 200 (OK) and the query's data
     */
    @PostMapping("/db/query")
    @Timed
    public ResponseEntity<List<Map<String, String>>> query(@Valid @RequestBody DBQuery query) {
        log.debug("REST request to query a DB");
        List<Map<String, String>> res = dBService.query(query);
        return ResponseEntity.ok().body(res);
    }
    
    /**
     * GET  /db/oracle/query : make a connection and a query to a oracle sql database
     *
     * @param query the SQL sentence
     * @return the ResponseEntity with status 200 (OK) and the query's data
     */
    @GetMapping("/db/oracle/query")
    @Timed
    public ResponseEntity<List<Map<String, String>>> query(@RequestParam String query) {
        log.debug("REST request to query a Oracle SQL DB");
        log.debug(query);
        List<Map<String, String>> res = dBService.OracleSQLQuery(query);
        return ResponseEntity.ok().body(res);
    }
    
    @ExceptionHandler(DBServiceException.class)
    public ResponseEntity<String> panic(DBServiceException oops) {
    	return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(oops.getEntity(), oops.getI18nCode(), oops.getMessage())).body(null);
	}	
}
