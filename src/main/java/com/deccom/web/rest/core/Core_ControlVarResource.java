package com.deccom.web.rest.core;

import java.time.LocalDateTime;
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
import com.deccom.domain.core.Core_ControlVar;
import com.deccom.domain.core.Core_RESTConnection;
import com.deccom.domain.core.Core_SQLConnection;
import com.deccom.service.impl.core.Core_ControlVarService;

import io.github.jhipster.web.util.ResponseUtil;

@RestController
@RequestMapping("/api")
public class Core_ControlVarResource {
	private final Logger log = LoggerFactory.getLogger(Core_ControlVarResource.class);

    private static final String ENTITY_NAME = "Core_ControlVar";

    @Autowired
    private Core_ControlVarService controlvarService;
    
    /**
     * Create example core_controlvar
     *
     * 
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/controlvar/create")
    @Timed
    public ResponseEntity<String> createControlVars() {
        log.debug("Creation Core_Connection objects");
        
        Core_RESTConnection rest = new Core_RESTConnection();
        rest.setJsonPath("$[0].id");
        rest.setUrl("http://api.google.es");
        
        Core_SQLConnection sql = new Core_SQLConnection();
        sql.setPassword("password");
        sql.setQuery("select * from somewhere");
        sql.setUrl("http://localhost:3808");
        sql.setUsername("username");
        
        Core_ControlVar c1 = new Core_ControlVar();
        c1.setConnection(rest);
        c1.setCreationMoment(LocalDateTime.now());
        c1.setDisabled(false);
        c1.setFrequency_sec(1000);
        c1.setName("RESTCONTROLVAR");
        
        Core_ControlVar c2 = new Core_ControlVar();
        c2.setConnection(sql);
        c2.setCreationMoment(LocalDateTime.now());
        c2.setDisabled(false);
        c2.setFrequency_sec(3000);
        c2.setName("SQLCONTROLVAR");
        
        
        controlvarService.save(c1);
        controlvarService.save(c2);
        
        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }
    
    /**
     *  Get all the core_controlvars.
     *
     *  @param pageable the pagination information
     *  @return the list of core_controlvars
     */
    @GetMapping("/controlvar/all")
    @Timed
    public Page<Core_ControlVar> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return controlvarService.findAll(pageable);
    }

}