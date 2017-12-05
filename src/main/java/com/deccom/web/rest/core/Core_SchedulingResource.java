package com.deccom.web.rest.core;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.deccom.service.impl.core.Core_SchedulingService;

import io.github.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing Acme.
 */
@RestController
@RequestMapping("/api")
public class Core_SchedulingResource {

    private final Logger log = LoggerFactory.getLogger(Core_SchedulingResource.class);

    // private static final String ENTITY_NAME = "scheduling";

    @Autowired
    private final Core_SchedulingService schedulingService;

    public Core_SchedulingResource(Core_SchedulingService schedulingService) {
        this.schedulingService = schedulingService;
    }

    /**
     * GET  /jobs/reset : reset all the jobs
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/jobs/reset")
    @Timed
    public ResponseEntity<String> resetJobs() {
        log.debug("REST request to reset all scheduling jobs");
        schedulingService.resetJobs();
        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }
    
    /**
     * GET  /jobs/reset : stop all the jobs
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK)
     */
    @GetMapping("/jobs/stop")
    @Timed
    public ResponseEntity<String> stopJobs() {
        log.debug("REST request to stop all scheduling jobs");
        schedulingService.stopJobs();
        return ResponseUtil.wrapOrNotFound(Optional.empty());
    }

}
