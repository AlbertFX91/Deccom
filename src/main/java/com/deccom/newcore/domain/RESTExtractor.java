package com.deccom.newcore.domain;

public class RESTExtractor implements ControlVariableExtractor {
	
	private String url;
	private String jsonPath;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonPath() {
		return jsonPath;
	}

	public void setJsonPath(String jsonPath) {
		this.jsonPath = jsonPath;
	}
	
	@Override
	public Integer getData() {
		// TODO Auto-generated method stub
		return 1;
	}

}
