package com.deccom.domain.core.extractor;

import com.deccom.config.ApplicationProperties;
import com.deccom.service.impl.util.RESTUtil;

public class FacebookFansExtractor extends RESTExtractor implements ControlVariableExtractor {

	private String url;
	private String jsonPath;
	private final ApplicationProperties applicationProperties;

	public FacebookFansExtractor(ApplicationProperties applicationProperties, String url) {

		super();
		this.applicationProperties = applicationProperties;
		this.url = "https://graph.facebook.com/v2.11/" + this.applicationProperties.getFacebookPageID()
				+ "?fields=fan_count";
		this.jsonPath = "$.[0].fan_count";

	}

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
