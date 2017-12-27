package com.deccom.domain.core.rest;

import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.domain.core.Core_Style;
import com.deccom.service.impl.util.RESTUtil;

public class Core_RESTExtractor implements Core_DataExtractor{
	
	private Core_RESTConnection dataConnection;
	private Core_RESTStyle style;
	
	
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
	
}
