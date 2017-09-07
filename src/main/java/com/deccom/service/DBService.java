package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Author;
import com.deccom.domain.DBQuery;

/**
 * Service Interface for DB connection
 */
public interface DBService {

    List<Map<String, String>> callNoMapping();
    
    List<Author> callMapping();
    
    List<Map<String,String>> query(DBQuery query);
    
    List<Map<String, String>> OracleSQLQuery(String query);
    
}
