package com.deccom.core;

import com.deccom.service.impl.util.RESTUtil;

public class RESTExtractor implements DataExtractor{
	
	private String url;
	private String jsonpath;
	
	public RESTExtractor() {
		this.url = "";
		this.jsonpath = "";
	}
	
	public RESTExtractor(String url, String jsonpath) {
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
