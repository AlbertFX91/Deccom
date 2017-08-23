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
import com.deccom.domain.Author;
import com.deccom.service.DBService;

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

}
