package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Author;

/**
 * Service Interface for DB connection
 */
public interface DBService {

    List<Map<String, String>> callNoMapping();
    
    List<Author> callMapping();
}
