package com.deccom.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.RESTConnection;
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTControlVarEntry;
import com.deccom.domain.RESTDataRecover;
import com.deccom.repository.RESTControlVarRepository;
import com.deccom.service.RESTControlVarService;
import com.deccom.service.impl.util.RESTUtil;

/**
 * Service Implementation for managing RESTControlVar.
 */
@Service
public class RESTControlVarServiceImpl implements RESTControlVarService {

	private final Logger log = LoggerFactory
			.getLogger(RESTControlVarServiceImpl.class);

	private final RESTControlVarRepository restControlVarRepository;
	
	public RESTControlVarServiceImpl(
			RESTControlVarRepository restControlVarRepository) {

		this.restControlVarRepository = restControlVarRepository;

	}

	/**
	 * Create a restControlVar.
	 *
	 * @param restDataRecover
	 *            the restDataRecover to create the restControlVar from
	 * @return the persisted entity
	 */
	public RESTControlVar create(RESTDataRecover restDataRecover) {

		RESTControlVar result;
		String name;
		String query;
		Integer frequency_sec;
		LocalDateTime creationMoment;
		RESTConnection restConnection;
		List<RESTControlVarEntry> restControlVarEntries;

		name = restDataRecover.getControlVarName();
		query = restDataRecover.getQuery();
		frequency_sec = restDataRecover.getFrequency_sec();
		creationMoment = LocalDateTime.now();
		restConnection = restDataRecover.getRestConnection();
		restControlVarEntries = new ArrayList<>();
		result = new RESTControlVar(name, query, creationMoment, frequency_sec,
				restConnection, restControlVarEntries);

		return result;

	}

	/**
	 * Save a restControlVar.
	 *
	 * @param restControlVar
	 *            the entity to save
	 * @return the persisted entity
	 */
	@Override
	public RESTControlVar save(RESTControlVar restControlVar) {

		log.debug("Request to save RESTControlVar : {}", restControlVar);

		RESTControlVar res = restControlVarRepository.save(restControlVar);
		
		return res;

	}
	
	/**
	 * Update a restControlVar.
	 *
	 * @param restControlVar
	 *            the entity to save
	 * @return the persisted entity
	 */
	public RESTControlVar update(RESTControlVar restControlVar) {

		log.debug("Request to update RESTControlVar : {}", restControlVar);

		RESTControlVar res = restControlVarRepository.save(restControlVar);
		
		return res;

	}

	/**
	 * Get all the restControlVarServices.
	 *
	 * @param pageable
	 *            the pagination information
	 * @return the list of entities
	 */
	@Override
	public Page<RESTControlVar> findAll(Pageable pageable) {

		log.debug("Request to get all RESTControlVarServices");

		return restControlVarRepository.findAll(pageable);

	}

	/**
	 * Get one restControlVarService by id.
	 *
	 * @param id
	 *            the id of the entity
	 * @return the entity
	 */
	@Override
	public RESTControlVar findOne(String id) {

		log.debug("Request to get RESTControlVarService : {}", id);

		return restControlVarRepository.findOne(id);
	}

	/**
	 * Delete the restControlVarService by id.
	 *
	 * @param id
	 *            the id of the entity
	 */
	@Override
	public void delete(String id) {

		log.debug("Request to delete RESTControlVarService : {}", id);

		restControlVarRepository.delete(id);

	}

	public void executeMonitorize(RESTControlVar restControlVar) {

		String query;
		LocalDateTime creationMoment;
		RESTConnection restConnection;
		String url;
		String aux;
		String value;
		RESTControlVarEntry restControlVarEntry;
		List<RESTControlVarEntry> restControlVarEntries;

		query = restControlVar.getQuery();
		creationMoment = LocalDateTime.now();
		restConnection = restControlVar.getRestConnection();
		url = restConnection.getUrl();
		aux = RESTUtil.getResponse(url);
		value = RESTUtil.getByJSONPath(aux, query);
		restControlVarEntry = new RESTControlVarEntry(value, creationMoment);
		restControlVarEntries = restControlVar.getRestControlVarEntries();

		restControlVarEntries.add(restControlVarEntry);

		update(restControlVar);

	}

	@Override
	public List<RESTControlVar> findAll() {
		log.debug("Request to get all RESTControlVarServices");

		return restControlVarRepository.findAll();
	}

}
