package com.deccom.domain.core.extractor;

import com.deccom.service.impl.util.RESTUtil;

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
		String body = RESTUtil.getResponse(getUrl());
		String value = RESTUtil.getByJSONPath(body, getJsonPath());
		return value.length();
	}

}
