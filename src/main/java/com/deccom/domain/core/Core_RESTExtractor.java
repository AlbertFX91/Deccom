package com.deccom.domain.core;

import com.deccom.service.impl.util.RESTUtil;

public class Core_RESTExtractor implements Core_DataExtractor{
	
	private Core_RESTConnection dataConnection;
	
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
	
}
