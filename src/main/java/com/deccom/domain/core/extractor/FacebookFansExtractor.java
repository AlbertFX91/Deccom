package com.deccom.domain.core.extractor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.deccom.config.ApplicationProperties;
import com.deccom.service.impl.util.RESTUtil;

@Configuration
@EnableConfigurationProperties({ ApplicationProperties.class })
public class FacebookFansExtractor extends RESTExtractor implements ControlVariableExtractor {

	@Autowired
	private ApplicationProperties applicationProperties = new ApplicationProperties();

	public FacebookFansExtractor() {

		super();

		String url, jsonPath;

		url = "https://graph.facebook.com/v2.11/" + applicationProperties.getFacebookPageID() + "?fields=fan_count";
		jsonPath = "$.[0].fan_count";

		setUrl(url);
		setJsonPath(jsonPath);

	}

	@Override
	public Integer getData() {

		String body, value;

		body = RESTUtil.getResponseFacebook(getUrl());
		value = getByJSONPath(body, getJsonPath());

		return Integer.parseInt(value);

	}

	public static String getResponseFacebook(String url) {
		return RESTUtil.getResponseFacebook(url);
	}

}
