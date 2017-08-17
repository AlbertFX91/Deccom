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

    public List<Map<String,String>> call() {
    	String url = "jdbc:mysql://localhost:3306/deccom";
    	String username = "developer";
    	String password = "developer";
    	
    	// Checking of the driver
    	try {
    	    Class.forName("com.mysql.jdbc.Driver");
    	} 
    	catch (ClassNotFoundException e) {
    	    // TODO Auto-generated catch block
    	    e.printStackTrace();
    	} 
    	List<Map<String, String>> res = Lists.newArrayList();
    	// Trying the connection
    	try {
    		Connection connection = DriverManager.getConnection(url, username, password);
        	log.debug("Database connected!");
        	String sql = "SELECT * FROM `author`";
        	PreparedStatement statement = connection.prepareStatement(sql);
        	ResultSet rs = statement.executeQuery();
        	while(rs.next()) {
        		Integer numCols = rs.getMetaData().getColumnCount();
        		/*String s = IntStream.range(0, numCols-1)
        				.mapToObj(i -> {
        					try { 
        						return rs.getString(i+1);
        					} catch (SQLException e) {
        						// TODO Auto-generated catch block
        						e.printStackTrace();
        						return null;
        					}
        				})
        				.reduce("", (x,y) -> x+","+y);
        				
        		log.debug("Values: "+s);
        		*/
        		
        		Map<String, String> data = new HashMap<>();
        		IntStream.range(1, numCols)
        			.forEach(i->{
        				try {
							data.put(rs.getMetaData().getColumnName(i)
									,rs.getString(i));
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
        			});
        		log.debug(data.toString());
        		res.add(data);
        	}
    	} catch (SQLException e) {
    	    throw new IllegalStateException("Cannot connect the database!", e);
    	}
    	return res;
    }
}
