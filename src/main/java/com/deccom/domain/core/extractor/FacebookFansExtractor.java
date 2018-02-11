package com.deccom.domain.core.extractor;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.json.JSONException;
import org.json.JSONObject;

import com.deccom.config.ApplicationProperties;
import com.deccom.service.impl.util.RESTServiceException;
import com.deccom.service.impl.util.RESTUtil;

public class FacebookFansExtractor extends RESTExtractor implements ControlVariableExtractor {

	private final ApplicationProperties applicationProperties;

	public FacebookFansExtractor(ApplicationProperties applicationProperties) {
		super();
		String url, jsonPath;
		this.applicationProperties = applicationProperties;
		url = "https://graph.facebook.com/v2.11/" + this.applicationProperties.getFacebookPageID()
				+ "?fields=fan_count";
		jsonPath = "$.[0].fan_count";
		
		setUrl(url);
		setJsonPath(jsonPath);
	}

	@Override
	public Integer getData() {

		String body = RESTUtil.getResponseFacebook(getUrl());
		String value = getByJSONPath(body, getJsonPath());
		return Integer.parseInt(value);

	}

	public static String getResponseFacebook(String url) {
		return RESTUtil.getResponseFacebook(url);
	}
	

}
