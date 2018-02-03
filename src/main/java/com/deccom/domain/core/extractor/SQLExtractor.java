package com.deccom.domain.core.extractor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deccom.service.impl.util.SQLUtil;

public class SQLExtractor implements ControlVariableExtractor {

	private String username;
	private String password;
	private String url;
	private String query;
	private String jdbc;

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

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public Integer getData() {
		// TODO Auto-generated method stub
		return getFromSQL().length();
	}
	
	protected String getFromSQL() {
		String value;
    	ResultSet rs;
    	Connection connection;
    	
    	connection = SQLUtil.connect(getJdbc(), getUrl(), getUsername(), getPassword());
    	rs = SQLUtil.executeQuery(connection, getQuery());
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
