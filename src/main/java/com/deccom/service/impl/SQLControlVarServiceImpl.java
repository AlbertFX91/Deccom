package com.deccom.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.deccom.domain.SQLConnection;
import com.deccom.domain.SQLControlVar;
import com.deccom.domain.SQLControlVarEntry;
import com.deccom.domain.SQLDataRecover;
import com.deccom.repository.SQLControlVarRepository;
import com.deccom.service.SQLControlVarService;
import com.deccom.service.impl.util.SQLUtil;


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
    	res.setCreationMoment(LocalDateTime.now());
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
    
    // @Scheduled(fixedRate = 1000 * 30)
    public void monitorize() {
    	List<SQLControlVar> controlVars = sqlControlVarRepository.findAll();
    	
    	controlVars.forEach((x)->executeMonitorize(x));

    }
    public void executeMonitorize(SQLControlVar controlVar) {
    	String value;
    	SQLControlVarEntry controlVarEntry;
    	ResultSet rs;
    	
    	SQLConnection sqlConnection = controlVar.getSqlConnection();
    	Connection connection = SQLUtil.connect(sqlConnection.getUrl(), 
    			sqlConnection.getUsername(), 
    			sqlConnection.getPassword());
    	
    	rs = SQLUtil.executeQuery(connection, controlVar.getQuery());
    	List<Map<String, String>> data = SQLUtil.collectAll(rs);
    	
    	// Only one row is accepted
    	if(data.size() != 1) {
    		throw new RuntimeException("Wrong Control Variable: Data collection with more than one result");
    	}
    	
    	// Only one row with one column is accepted
    	Map<String, String> entry = data.get(0);
    	if(entry.size() != 1) {
    		throw new RuntimeException("Wrong Control Variable: Data collection with more than one column");
    	}
    	
    	value = new ArrayList<>(entry.values()).get(0);
    	controlVarEntry = new SQLControlVarEntry(value, LocalDateTime.now());
    	controlVar.getSqlControlVarEntries().add(controlVarEntry);
    	save(controlVar);
    	
    }
}
