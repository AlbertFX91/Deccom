package com.deccom.domain.core.rest;

import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.domain.core.Core_Style;
import com.deccom.service.impl.util.RESTUtil;

public class Core_RESTExtractor implements Core_DataExtractor<Core_RESTConnection>{
	
	private Core_RESTConnection dataConnection;
	private Core_RESTStyle style = new Core_RESTStyle();
	
	
	public Core_RESTExtractor() {
	}
	
	@Override
	public String getData() {
		String value;
		
		value = getByJsonPath();
		
		return value;
	}
	
	protected String getByJsonPath(){
		String body = RESTUtil.getResponse(dataConnection.getUrl());
		String value = RESTUtil.getByJSONPath(body, dataConnection.getJsonPath());
		return value;
	}

	@Override
	public Core_Style getStyle() {
		return style;
	}

	@Override
	public Core_RESTConnection getConnection() {
		return dataConnection;
	}

	@Override
	public void setConnection(Core_RESTConnection connection) {
		this.dataConnection = connection;
	}
	
}
