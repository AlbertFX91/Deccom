package com.deccom.service.impl;

import com.deccom.service.AcmeService;
import com.deccom.domain.Acme;
import com.deccom.repository.AcmeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * Service Implementation for managing Acme.
 */
@Service
public class AcmeServiceImpl implements AcmeService{

    private final Logger log = LoggerFactory.getLogger(AcmeServiceImpl.class);

    private final AcmeRepository acmeRepository;

    public AcmeServiceImpl(AcmeRepository acmeRepository) {
        this.acmeRepository = acmeRepository;
    }

    /**
     * Save a acme.
     *
     * @param acme the entity to save
     * @return the persisted entity
     */
    @Override
    public Acme save(Acme acme) {
        log.debug("Request to save Acme : {}", acme);
        return acmeRepository.save(acme);
    }

    /**
     *  Get all the acmes.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<Acme> findAll(Pageable pageable) {
        log.debug("Request to get all Acmes");
        return acmeRepository.findAll(pageable);
    }

    /**
     *  Get one acme by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public Acme findOne(String id) {
        log.debug("Request to get Acme : {}", id);
        return acmeRepository.findOne(id);
    }

    /**
     *  Delete the  acme by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete Acme : {}", id);
        acmeRepository.delete(id);
    }
}
