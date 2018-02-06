package com.deccom.domain.core.extractor;

import javax.validation.constraints.NotNull;

import com.deccom.service.impl.util.RESTUtil;

public class RESTExtractor implements ControlVariableExtractor {

	@NotNull
	private String url;
	@NotNull
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
		return Integer.parseInt(value);
	}

}
