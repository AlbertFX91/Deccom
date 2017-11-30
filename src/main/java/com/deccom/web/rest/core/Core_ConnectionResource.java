package com.deccom.web.rest.core;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.core.Core_Connection;
import com.deccom.domain.core.Core_RESTConnection;
import com.deccom.domain.core.Core_RESTExtractor;
import com.deccom.domain.core.Core_SQLConnection;
import com.deccom.domain.core.Core_SQLExtractor;
import com.deccom.service.impl.core.Core_ConnectionService;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class Core_ConnectionResource {
	private final Logger log = LoggerFactory.getLogger(Core_ConnectionResource.class);

    private static final String ENTITY_NAME = "Core_Connection";

    @Autowired
    private Core_ConnectionService connectionService;
    
    /**
     * GET  /coreconnection/create: test
     *
     * 
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/coreconnection/create")
    @Timed
    public ResponseEntity<String> createConnections() {
        log.debug("Creation Core_Connection objects");
        
        Core_RESTConnection rest = new Core_RESTConnection();
        rest.set_extractorClass(Core_RESTExtractor.class.getName());
        rest.setJsonPath("$[0].id");
        rest.setUrl("http://api.google.es");
        
        Core_SQLConnection sql = new Core_SQLConnection();
        sql.set_extractorClass(Core_SQLExtractor.class.getName());
        sql.setPassword("password");
        sql.setQuery("select * from somewhere");
        sql.setUrl("http://localhost:3808");
        sql.setUsername("username");
        
        connectionService.save(rest);
        connectionService.save(sql);
        
        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }
    
    /**
     *  Get all the core_connections.
     *
     *  @param pageable the pagination information
     *  @return the list of core_connections
     */
    @GetMapping("/coreconnection/all")
    @Timed
    public Page<Core_Connection> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return connectionService.findAll(pageable);
    }

}