package com.deccom.service.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deccom.domain.Author;
import com.deccom.domain.DBQuery;
import com.deccom.service.DBService;
import com.deccom.service.impl.util.DBServiceException;
import com.google.common.collect.Lists;

@Service
public class DBServiceImpl implements DBService {
	private final Logger log = LoggerFactory.getLogger(DBServiceImpl.class);
	private final String i18nCodeRoot = "operations.dbquery";

    public DBServiceImpl() {
    }
    
    public List<Map<String,String>> callNoMapping() {
    	String url = "jdbc:mysql://localhost:3306/deccom";
    	String username = "developer";
    	String password = "developer";
    	
    	
    	String table = "author";
    	String cols = "*";
    	String query = "SELECT "+cols+" FROM `"+table+"`";
    	
    	// Checking of the driver
    	checkDriver("com.mysql.jdbc.Driver");
    	
    	// Data structure for the result data
    	List<Map<String, String>> res;
    	
    	// Database connection
    	Connection connection = connect(url, username, password);
    	
    	// Result of the query execution
    	ResultSet rs = executeQuery(connection, query);
    	
    	// Data collection
    	res = collectAll(rs);
    	
    	return res;
    }
    
    public List<Author> callMapping() {
    	String url = "jdbc:mysql://localhost:3306/deccom";
    	String username = "developer";
    	String password = "developer";
    	
    	
    	String table = "author";
    	String cols = "*";
    	String query = "SELECT "+cols+" FROM `"+table+"`";
    	
    	// Checking of the driver
    	checkDriver("com.mysql.jdbc.Driver");
    	
    	// Data structure for the result data
    	List<Author> res;
    	
    	// Database connection
    	Connection connection = connect(url, username, password);
    	
    	// Result of the query execution
    	ResultSet rs = executeQuery(connection, query);
    	
    	// Data collection
    	res = collectAsAuthor(rs);
    	
    	return res;
    }

    public List<Map<String,String>> query(DBQuery query) {
    	String url = query.getUrl();
    	String username = query.getUsername();
    	String password = query.getPassword();
    	String q = query.getQuery();
    	
    	// Checking of the driver
    	checkDriver("com.mysql.jdbc.Driver");
    	
    	// Data structure for the result data
    	List<Map<String, String>> res;
    	
    	// Database connection
    	Connection connection = connect(url, username, password);
    	
    	// Result of the query execution
    	ResultSet rs = executeQuery(connection, q);
    	
    	// Data collection
    	res = collectAll(rs);
    	
    	return res;
    }
    
    
    public List<Map<String, String>> OracleSQLQuery(String query){
    	String url = "jdbc:oracle:thin:@localhost:1521:orcl12";
    	String username = "c##developer";
    	String password = "developer";
    	
    	// Checking of the driver
    	checkDriver("oracle.jdbc.driver.OracleDriver");
    	
    	// Data structure for the result data
    	List<Map<String, String>> res;
    	
    	// Database connection
    	Connection connection = connect(url, username, password);
    	
    	// Result of the query execution
    	ResultSet rs = executeQuery(connection, query);
    	
    	// Data collection
    	res = collectAll(rs);
    	
    	return res;
    	
    }
    
    private void checkDriver(String driver) {
    	try {
    	    //Class.forName("com.mysql.jdbc.Driver");
    		//Class.forName("oracle.jdbc.driver.OracleDriver");
    		Class.forName(driver);
    	} 
    	catch (ClassNotFoundException e) {
    		throw new DBServiceException("Cannot find the "+driver+" Class", "dbquery.drivernotfound", "DBService", e);
    		// throw new IllegalStateException("Cannot find the "+driver+" Class", e);
    	}
    }
    
    private Connection connect(String url, String username, String password) {
    	Connection connection;
    	try {
    		connection = DriverManager.getConnection(url, username, password);
        	log.debug("Database connected!");
    	} catch (SQLException e) {
    		Integer SQLCode = e.getErrorCode();
    		String i18nerrorCode;
    		switch(SQLCode) {
    			case 1045: 	i18nerrorCode = i18nCodeRoot+".credentialserror"; break;
    			case 0: 	i18nerrorCode = i18nCodeRoot+".connectionerror"; break;
    			default: 	i18nerrorCode = i18nCodeRoot+".connectionerror"; break;
    		}
    		throw new DBServiceException("Cannot connect with the database", i18nerrorCode, "DBService", e);
    	    // throw new IllegalStateException("Cannot connect the database!", e);
    	}
    	return connection;
    }
    
    private ResultSet executeQuery(Connection connection, String query) {
    	ResultSet res;
    	try {
    		PreparedStatement statement = connection.prepareStatement(query);
    		res = statement.executeQuery();
    	} catch (SQLException e) {
    		throw new DBServiceException("Query execution error", i18nCodeRoot+".queryerror", "DBService", e);
    	    // throw new IllegalStateException("Query execution error", e);
    	}
    	return res;
    }
    
    private List<Map<String, String>> collectAll(ResultSet rs){
    	List<Map<String, String>> res = Lists.newArrayList();
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
    		throw new DBServiceException("Data extraction error", i18nCodeRoot+".extractionerror", "DBService", e);
    	    // throw new IllegalStateException("Data extraction error", e);
    	}
    	return res;
    }

    private List<Author> collectAsAuthor(ResultSet rs){
    	List<Author> res = Lists.newArrayList();
    	try {
        	while(rs.next()) {
        		Author author = new Author();
        		
        		Integer idAuthor = rs.getInt("idauthor");
        		Double avgBooks = rs.getDouble("avgbooks");
        		String name = rs.getString("name");
        		Integer age = rs.getInt("age");
        		LocalDate debut = LocalDate.parse(
        				rs.getString("debut"),
        				DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S"));
        		
        		author.setIdAuthor(idAuthor);
        		author.setAvgBooks(avgBooks);
        		author.setDebut(debut);
        		author.setName(name);
        		author.setAge(age);
        		
        		log.debug(author.toString());
        		res.add(author);
        	}
    	} catch (SQLException e) {
    		throw new DBServiceException("Data extraction error", i18nCodeRoot+".extractionerror", "DBService", e);
    	    // throw new IllegalStateException("Data extraction error", e);
    	} 
    	return res;
    }
}
