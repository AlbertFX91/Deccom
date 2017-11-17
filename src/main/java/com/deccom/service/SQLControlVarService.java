package com.deccom.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccom.domain.SQLControlVar;
import com.deccom.domain.SQLDataRecover;

/**
 * Service Interface for managing SQLControlVar.
 */
public interface SQLControlVarService {
	
	/**
	 * Create a SQLControlVar by a SQLDataRecover object
	 * @param sqlDataRecover the object used to create the SQLControlVar
	 * @return the sqlControlVar resulted
	 */
	SQLControlVar create(SQLDataRecover sqlDataRecover);
	
    /**
     * Save a sqlControlVar.
     *
     * @param acme the entity to save
     * @return the persisted entity
     */
    SQLControlVar save(SQLControlVar sqlControlVar);

    /**
     *  Get all the sqlControlVars.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<SQLControlVar> findAll(Pageable pageable);

    /**
     *  Get the "id" sqlControlVar.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    SQLControlVar findOne(String id);

    /**
     *  Delete the "id" sqlControlVar.
     *
     *  @param id the id of the entity
     */
    void delete(String id);
    
    /**
     *  Get all the sqlControlVars.
     *
     *  @return the list of entities
     */
    List<SQLControlVar> findAll();
    
	/**
	 * Get the data to monitor by a SQLControlVar and save the new entry in the object
	 * @param controlVar
	 */
	void executeMonitorize(SQLControlVar controlVar);
    
}
