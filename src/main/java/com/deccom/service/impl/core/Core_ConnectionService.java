package com.deccom.service.impl.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.domain.core.Core_Connection;
import com.deccom.repository.core.Core_ConnectionRepository;

@Service
public class Core_ConnectionService {
	private final Logger log = LoggerFactory.getLogger(Core_ConnectionService.class);
	
	@Autowired
    private Core_ConnectionRepository connectionRepository;
	
	public Core_Connection save(Core_Connection c) {
		return connectionRepository.save(c);
	}
	
    public Page<Core_Connection> findAll(Pageable pageable) {
        log.debug("Request to get all Core_Connection");
        return connectionRepository.findAll(pageable);
    }
	
}
