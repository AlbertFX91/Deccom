package com.deccom.domain.core;

import com.deccom.service.impl.util.RESTUtil;

public class Core_RESTExtractor implements Core_DataExtractor{
	
	private String url;
	private String jsonpath;
	
	public Core_RESTExtractor() {
		this.url = "";
		this.jsonpath = "";
	}
	
	public Core_RESTExtractor(String url, String jsonpath) {
		this.url = url;
		this.jsonpath = jsonpath;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonpath() {
		return jsonpath;
	}
	public void setJsonpath(String jsonpath) {
		this.jsonpath = jsonpath;
	}

	public String getData() {
		String value;
		
		value = getByJsonPath();
		
		return value;
	}
	
	protected String getByJsonPath(){
		String body = RESTUtil.getResponse(this.url);
		String value = RESTUtil.getByJSONPath(body, this.jsonpath);
		return value;
	}
	
}
