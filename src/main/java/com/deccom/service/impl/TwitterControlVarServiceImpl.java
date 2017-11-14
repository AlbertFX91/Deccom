package com.deccom.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deccom.domain.RESTConnection;
import com.deccom.domain.RESTControlVar;
import com.deccom.domain.RESTControlVarEntry;
import com.deccom.repository.RESTControlVarRepository;
import com.deccom.service.RESTControlVarService;
import com.deccom.service.TwitterControlVarService;
import com.deccom.service.TwitterService;
import com.deccom.service.impl.util.RESTUtil;

/**
 * Service Implementation for managing TwitterControlVar.
 */
@Service
public class TwitterControlVarServiceImpl implements TwitterControlVarService {

	private final RESTControlVarRepository restControlVarRepository;

	private final TwitterService twitterService;

	private final RESTControlVarService restControlVarService;

	public TwitterControlVarServiceImpl(
			RESTControlVarRepository restControlVarRepository,
			TwitterService twitterService,
			RESTControlVarService restControlVarService) {

		this.restControlVarRepository = restControlVarRepository;
		this.twitterService = twitterService;
		this.restControlVarService = restControlVarService;

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
		aux = twitterService.noMapping(url).getContent().toString();
		value = RESTUtil.getByJSONPath(aux, query);
		restControlVarEntry = new RESTControlVarEntry(value, creationMoment);
		restControlVarEntries = restControlVar.getRestControlVarEntries();

		restControlVarEntries.add(restControlVarEntry);

		restControlVarService.save(restControlVar);

	}

}
