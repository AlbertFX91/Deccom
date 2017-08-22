package com.deccom.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;


/**
 * Sample service to connect to a DB
 */
@Service
public class SampleDBService{

    private final Logger log = LoggerFactory.getLogger(SampleDBService.class);

    public SampleDBService() {
    }

    public List<Map<String,String>> callNoMapping() {
    	String url = "jdbc:mysql://localhost:3306/deccom";
    	String username = "developer";
    	String password = "developer";
    	
    	
    	String table = "author";
    	String cols = "*";
    	String query = "SELECT "+cols+" FROM `"+table+"`";
    	
    	// Checking of the driver
    	try {
    	    Class.forName("com.mysql.jdbc.Driver");
    	} 
    	catch (ClassNotFoundException e) {
    		throw new IllegalStateException("Cannot find the com.mysql.jdbc.Driver Class", e);
    	}
    	
    	// Data structure for the result data
    	List<Map<String, String>> res = Lists.newArrayList();
    	// Database connection
    	Connection connection;
    	// Result of the query execution
    	ResultSet rs;
    	
    	// Database connection
    	try {
    		connection = DriverManager.getConnection(url, username, password);
        	log.debug("Database connected!");
    	} catch (SQLException e) {
    	    throw new IllegalStateException("Cannot connect the database!", e);
    	}
    	
    	// Query execution
    	try {
    		PreparedStatement statement = connection.prepareStatement(query);
        	rs = statement.executeQuery();
    	} catch (SQLException e) {
    	    throw new IllegalStateException("Query execution error", e);
    	}
    	
    	// Data collection
    	try {
        	while(rs.next()) {
        		// Getting the num of columns of the query result
        		Integer numCols = rs.getMetaData().getColumnCount();
        		// Map with K: Column, V: Value
        		Map<String, String> data = new HashMap<>();
        		// We loop each column
        		IntStream.range(1, numCols+1)
        			.forEach(i->{
        				try {
        					String column = rs.getMetaData().getColumnName(i);
        					String value = rs.getString(i);
							data.put(column, value);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			});
        		log.debug(data.toString());
        		res.add(data);
        	}
    	} catch (SQLException e) {
    	    throw new IllegalStateException("Data extraction error", e);
    	}
    	return res;
    }
}
