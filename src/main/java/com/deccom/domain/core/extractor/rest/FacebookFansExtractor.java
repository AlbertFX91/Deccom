package com.deccom.domain.core.extractor.rest;

import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.service.impl.util.RESTUtil;

public class FacebookFansExtractor extends RESTExtractor implements ControlVariableExtractor {

	private String facebookPageID;
	
	public FacebookFansExtractor() {

		super();

		String url, jsonPath, facebookPageID;

		// url = "https://graph.facebook.com/v2.11/" + facebookPageID + "?fields=fan_count";
		url = "https://graph.facebook.com/v2.11/";
		jsonPath = "$.fan_count";
		facebookPageID = "546664955726052";

		setUrl(url);
		setJsonPath(jsonPath);
		setFacebookPageID(facebookPageID);

	}
	
	public String getFacebookPageID() {
		return facebookPageID;
	}

	public void setFacebookPageID(String facebookPageID) {
		this.facebookPageID = facebookPageID;
	}

	@Override
	public Integer getData() {

		String url, body, value;
		
		url = getUrl() + getFacebookPageID() + "?fields=fan_count";

		body = RESTUtil.getResponseFacebook(url);
		value = getByJSONPath(body, getJsonPath());

		return Integer.parseInt(value);

	}

	public static String getResponseFacebook(String url) {
		System.out.println("getResponseFacebook: " + RESTUtil.getResponseFacebook(url));
		return RESTUtil.getResponseFacebook(url);
	}

}
