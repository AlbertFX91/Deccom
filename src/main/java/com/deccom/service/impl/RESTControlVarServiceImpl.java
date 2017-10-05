package com.deccom.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deccom.domain.RESTConnection;
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTControlVarEntry;
import com.deccom.domain.RESTDataRecover;
import com.deccom.repository.RESTControlVarRepository;
import com.deccom.service.RESTControlVarService;
import com.deccom.service.RESTService;

/**
 * Service Implementation for managing RESTControlVar.
 */
@Service
public class RESTControlVarServiceImpl implements RESTControlVarService {

	private final Logger log = LoggerFactory
			.getLogger(RESTControlVarServiceImpl.class);

	private final RESTControlVarRepository restControlVarRepository;

	private final RESTService restService;

	public RESTControlVarServiceImpl(
			RESTControlVarRepository restControlVarRepository,
			RESTService restService) {

		this.restControlVarRepository = restControlVarRepository;
		this.restService = restService;

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
		LocalDate creationMoment;
		RESTConnection restConnection;
		List<RESTControlVarEntry> restControlVarEntries;

		name = restDataRecover.getControlVarName();
		query = restDataRecover.getQuery();
		creationMoment = LocalDate.now();
		restConnection = restDataRecover.getRestConnection();
		restControlVarEntries = new ArrayList<>();
		result = new RESTControlVar(name, query, creationMoment,
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

		return restControlVarRepository.save(restControlVar);

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

	@Scheduled(fixedRate = 1000 * 30)
	public void monitorize() throws Exception {

		List<RESTControlVar> restControlVars;

		restControlVars = restControlVarRepository.findAll();

		// restControlVars.forEach((x) -> executeMonitorize(x));

		for (RESTControlVar restControlVar : restControlVars) {
			executeMonitorize(restControlVar);
		}

	}

	public void executeMonitorize(RESTControlVar restControlVar)
			throws Exception {

		String query;
		LocalDate creationMoment;
		RESTConnection restConnection;
		String url;
		String value;
		RESTControlVarEntry restControlVarEntry;
		List<RESTControlVarEntry> restControlVarEntries;

		query = restControlVar.getQuery();
		creationMoment = LocalDate.now();
		restConnection = restControlVar.getRestConnection();
		url = restConnection.getUrl();
		value = restService.getByJsonPath(url, query);
		restControlVarEntry = new RESTControlVarEntry(value, creationMoment);
		restControlVarEntries = restControlVar.getRestControlVarEntries();

		restControlVarEntries.add(restControlVarEntry);

		save(restControlVar);

	}

}
