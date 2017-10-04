package com.deccom.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.SQLControlVar;
import com.deccom.domain.SQLDataRecover;
import com.deccom.repository.SQLControlVarRepository;
import com.deccom.service.SQLControlVarService;


/**
 * Service Implementation for managing Acme.
 */
@Service
public class SQLControlVarServiceImpl implements SQLControlVarService{

    private final Logger log = LoggerFactory.getLogger(SQLControlVarServiceImpl.class);

    private final SQLControlVarRepository sqlControlVarRepository;

    public SQLControlVarServiceImpl(SQLControlVarRepository sqlControlVarRepository) {
        this.sqlControlVarRepository = sqlControlVarRepository;
    }

    
    /**
	 * Create a SQLControlVar by a SQLDataRecover object
	 * @param sqlDataRecover the object used to create the SQLControlVar
	 * @return the sqlControlVar resulted
	 */
    @Override
	public SQLControlVar create(SQLDataRecover sqlDataRecover) {
    	SQLControlVar res;
    	
    	res = new SQLControlVar();
    	res.setCreationMoment(LocalDate.now());
    	res.setName(sqlDataRecover.getControlVarName());
    	res.setQuery(sqlDataRecover.getQuery());
    	res.setSqlConnection(sqlDataRecover.getConnection());
    	res.setSqlControlVarEntries(new ArrayList<>());
    	
    	return res;
    }
    
    /**
     * Save a sqlControlVar.
     *
     * @param sqlControlVar the entity to save
     * @return the persisted entity
     */
    @Override
    public SQLControlVar save(SQLControlVar sqlControlVar) {
        log.debug("Request to save SQLControlVar : {}", sqlControlVar);
        return sqlControlVarRepository.save(sqlControlVar);
    }

    /**
     *  Get all the sqlControlVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    public Page<SQLControlVar> findAll(Pageable pageable) {
        log.debug("Request to get all sqlControlVars");
        return sqlControlVarRepository.findAll(pageable);
    }

    /**
     *  Get one sqlControlVar by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    public SQLControlVar findOne(String id) {
        log.debug("Request to get SQLControlVar : {}", id);
        return sqlControlVarRepository.findOne(id);
    }

    /**
     *  Delete the a sqlControlVar by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(String id) {
        log.debug("Request to delete SQLControlVar : {}", id);
        sqlControlVarRepository.delete(id);
    }
}
