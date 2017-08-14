package com.deccom.service;

import com.deccom.domain.Acme;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Acme.
 */
public interface AcmeService {

    /**
     * Save a acme.
     *
     * @param acme the entity to save
     * @return the persisted entity
     */
    Acme save(Acme acme);

    /**
     *  Get all the acmes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<Acme> findAll(Pageable pageable);

    /**
     *  Get the "id" acme.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    Acme findOne(String id);

    /**
     *  Delete the "id" acme.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
}
