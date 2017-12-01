package com.deccom.core;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deccom.service.impl.util.SQLUtil;

public class SQLExtractor implements DataExtractor{
	
	private String username;
	private String password;
	private String url;
	private String query;
	
	public SQLExtractor() {
		this.username = "";
		this.password = "";
		this.url = "";
		this.query = "";
	}
	
	public SQLExtractor(String username, String password, String url, String query) {
		this.username = username;
		this.password = password;
		this.url = url;
		this.query = query;
	}

	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	
	@Override
	public String getData() {
		String value;
		
		value = getFromSQL();
		
		return value;
	}
	
	protected String getFromSQL() {
		String value;
    	ResultSet rs;
    	Connection connection;
    	
    	connection = SQLUtil.connect(this.url, this.username, this.password);
    	rs = SQLUtil.executeQuery(connection, this.query);
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
    	
    	return value;
	}
	
	
}
