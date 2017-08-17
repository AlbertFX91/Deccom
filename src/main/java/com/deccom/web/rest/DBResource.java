package com.deccom.web.rest;

import java.util.HashMap;
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
import com.google.common.collect.Lists;

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

    @GetMapping("/db")
    @Timed
    public ResponseEntity<List<Map<String, String>>> testDB() {
        log.debug("REST request to test DB");
        List<Map<String, String>> res = sampleDBService.call();
        return ResponseEntity.ok().body(res);
    }
    
    @GetMapping("/map")
    @Timed
    public ResponseEntity<List<Map<String, String>>> testMap() {
        log.debug("REST request to test Map");
        
        Map<String, String> m1 = new HashMap<>();
        m1.put("name", "name1");
        m1.put("description", "description1");
        m1.put("genre", "genre1");
        Map<String, String> m2 = new HashMap<>();
        m2.put("name", "name2");
        m2.put("description", "description2");
        m2.put("genre", "genre2");
        List<Map<String, String>> res = Lists.newArrayList(m1,m2);
        
        
        return ResponseEntity.ok().body(res);
    }

}
