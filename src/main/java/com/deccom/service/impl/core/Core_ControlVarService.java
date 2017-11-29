package com.deccom.service.impl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.core.Core_ControlVar;
import com.deccom.repository.core.Core_ControlVarRepository;

@Service
public class Core_ControlVarService {
	private final Logger log = LoggerFactory.getLogger(Core_ControlVarService.class);
	
	@Autowired
    private Core_ControlVarRepository controlVarRepository;
	
	public Core_ControlVar save(Core_ControlVar c) {
		return controlVarRepository.save(c);
	}
	
    public Page<Core_ControlVar> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return controlVarRepository.findAll(pageable);
    }
	
}
