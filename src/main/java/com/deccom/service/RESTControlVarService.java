package com.deccom.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTDataRecover;

/**
 * Service Interface for managing RESTControlVar.
 */
public interface RESTControlVarService {

	/**
	 * Create a restControlVar.
	 *
	 * @param restDataRecover
	 *            the restDataRecover to create the restControlVar from
	 * @return the persisted entity
	 */
	RESTControlVar create(RESTDataRecover restDataRecover);

	/**
	 * Save a restControlVar.
	 *
	 * @param restControlVar
	 *            the entity to save
	 * @return the persisted entity
	 */
	RESTControlVar save(RESTControlVar restControlVar);

	/**
	 * Get all the restControlVars.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	Page<RESTControlVar> findAll(Pageable pageable);

	/**
	 * Get the "id" restControlVar.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	RESTControlVar findOne(String id);

	/**
	 * Delete the "id" restControlVar.
	 *
	 * @param id
	 *            the id of the entity
	 */
	void delete(String id);

	/**
	 * Check the value of an existing entry.
	 */
	void monitorize() throws Exception;
    

    List<RESTControlVar> findAll();
	
}
