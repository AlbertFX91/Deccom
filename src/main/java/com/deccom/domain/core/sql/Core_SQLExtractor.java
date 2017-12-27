package com.deccom.domain.core.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.domain.core.Core_Style;
import com.deccom.service.impl.util.SQLUtil;

public class Core_SQLExtractor implements Core_DataExtractor{
	
	private Core_SQLConnection dataConnection;
	private Core_SQLStyle style = new Core_SQLStyle();
	
	public Core_SQLExtractor() {
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
    	
    	connection = SQLUtil.connect(dataConnection.getUrl(), dataConnection.getUsername(), dataConnection.getPassword());
    	rs = SQLUtil.executeQuery(connection, dataConnection.getQuery());
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

	@Override
	public Core_Style getStyle() {
		return style;
	}
	
	
}
