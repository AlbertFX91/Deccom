package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Author;
import com.deccom.domain.SQLQuery;
import com.deccom.service.impl.util.SQLResponse;

/**
 * Service Interface for DB connection
 */
public interface SQLService {

    List<Map<String, String>> callNoMapping();
    
    List<Author> callMapping();
    
    SQLResponse query(SQLQuery query);
    
    List<Map<String, String>> OracleSQLQuery(String query);
    
}
