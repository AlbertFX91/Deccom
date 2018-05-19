package com.deccom.domain.core.extractor.rest.facebook;

import com.deccom.domain.core.CVStyleUtil;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.extractor.rest.RESTExtractor;
import com.deccom.domain.core.fields.DeccomField;
import com.deccom.service.impl.util.RESTUtil;

public class FacebookFansExtractor extends RESTExtractor implements ControlVariableExtractor {

	@DeccomField()
	private String facebookPageID;

	public FacebookFansExtractor() {

		super();

		String url, jsonPath, facebookPageID;

		url = "https://graph.facebook.com/v2.11/";
		jsonPath = "$.fan_count";
		facebookPageID = "546664955726052";

		setUrl(url);
		setJsonPath(jsonPath);
		setFacebookPageID(facebookPageID);
		setStyle(CVStyleUtil.facebook);

	}

	public String getFacebookPageID() {
		return facebookPageID;
	}

	public void setFacebookPageID(String facebookPageID) {
		this.facebookPageID = facebookPageID;
	}

	@Override
	public Double getData() {

		String url, body, value;

		url = getUrl() + getFacebookPageID() + "?fields=fan_count";

		body = RESTUtil.getResponseFacebook(url);
		value = getByJSONPath(body, getJsonPath());

		return Double.parseDouble(value);

	}

	public static String getResponseFacebook(String url) {
		System.out.println("getResponseFacebook: " + RESTUtil.getResponseFacebook(url));
		return RESTUtil.getResponseFacebook(url);
	}

}
