package com.deccom.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.deccom.domain.Acme;
import com.deccom.service.AcmeService;
import com.deccom.web.rest.util.HeaderUtil;
import com.deccom.web.rest.util.PaginationUtil;
import io.swagger.annotations.ApiParam;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Acme.
 */
@RestController
@RequestMapping("/api")
public class AcmeResource {

    private final Logger log = LoggerFactory.getLogger(AcmeResource.class);

    private static final String ENTITY_NAME = "acme";

    private final AcmeService acmeService;

    public AcmeResource(AcmeService acmeService) {
        this.acmeService = acmeService;
    }

    /**
     * POST  /acmes : Create a new acme.
     *
     * @param acme the acme to create
     * @return the ResponseEntity with status 201 (Created) and with body the new acme, or with status 400 (Bad Request) if the acme has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/acmes")
    @Timed
    public ResponseEntity<Acme> createAcme(@Valid @RequestBody Acme acme) throws URISyntaxException {
        log.debug("REST request to save Acme : {}", acme);
        if (acme.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert(ENTITY_NAME, "idexists", "A new acme cannot already have an ID")).body(null);
        }
        Acme result = acmeService.save(acme);
        return ResponseEntity.created(new URI("/api/acmes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /acmes : Updates an existing acme.
     *
     * @param acme the acme to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated acme,
     * or with status 400 (Bad Request) if the acme is not valid,
     * or with status 500 (Internal Server Error) if the acme couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/acmes")
    @Timed
    public ResponseEntity<Acme> updateAcme(@Valid @RequestBody Acme acme) throws URISyntaxException {
        log.debug("REST request to update Acme : {}", acme);
        if (acme.getId() == null) {
            return createAcme(acme);
        }
        Acme result = acmeService.save(acme);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, acme.getId().toString()))
            .body(result);
    }

    /**
     * GET  /acmes : get all the acmes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of acmes in body
     */
    @GetMapping("/acmes")
    @Timed
    public ResponseEntity<List<Acme>> getAllAcmes(@ApiParam Pageable pageable) {
        log.debug("REST request to get a page of Acmes");
        Page<Acme> page = acmeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/acmes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /acmes/:id : get the "id" acme.
     *
     * @param id the id of the acme to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the acme, or with status 404 (Not Found)
     */
    @GetMapping("/acmes/{id}")
    @Timed
    public ResponseEntity<Acme> getAcme(@PathVariable String id) {
        log.debug("REST request to get Acme : {}", id);
        Acme acme = acmeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(acme));
    }

    /**
     * DELETE  /acmes/:id : delete the "id" acme.
     *
     * @param id the id of the acme to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/acmes/{id}")
    @Timed
    public ResponseEntity<Void> deleteAcme(@PathVariable String id) {
        log.debug("REST request to delete Acme : {}", id);
        acmeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id)).build();
    }
}
