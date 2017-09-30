package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Author;
import com.deccom.domain.DBQuery;
import com.deccom.service.impl.util.DBResponse;

/**
 * Service Interface for DB connection
 */
public interface DBService {

    List<Map<String, String>> callNoMapping();
    
    List<Author> callMapping();
    
    DBResponse query(DBQuery query);
    
    List<Map<String, String>> OracleSQLQuery(String query);
    
}
