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
import com.deccom.service.SampleDBService;

/**
 * REST controller for managing Acme.
 */
@RestController
@RequestMapping("/api")
public class DBResource {

    private final Logger log = LoggerFactory.getLogger(DBResource.class);


    private final SampleDBService sampleDBService;

    public DBResource(SampleDBService sampleDBService) {
        this.sampleDBService = sampleDBService;
    }

    @GetMapping("/db/nomap")
    @Timed
    public ResponseEntity<List<Map<String, String>>> testDB() {
        log.debug("REST request to test DB");
        List<Map<String, String>> res = sampleDBService.callNoMapping();
        return ResponseEntity.ok().body(res);
    }

}
