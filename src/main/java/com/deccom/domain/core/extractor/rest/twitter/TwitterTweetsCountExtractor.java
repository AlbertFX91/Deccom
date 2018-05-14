package com.deccom.domain.core.extractor.rest.twitter;

import com.deccom.domain.core.CVStyleUtil;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.extractor.rest.RESTExtractor;
import com.deccom.domain.core.fields.DeccomField;
import com.deccom.service.impl.util.RESTUtil;

public class TwitterTweetsCountExtractor extends RESTExtractor implements ControlVariableExtractor {

	@DeccomField()
	private String username;

	public TwitterTweetsCountExtractor() {

		super();

		String url;

		url = "https://api.twitter.com/1.1/users/lookup.json";

		setUrl(url);
		setJsonPath("$.[0].statuses_count");
		setStyle(CVStyleUtil.twitterTweetsCount);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public Double getData() {

		String body, value;

		body = RESTUtil.getResponseTwitter(getUrl() + "?screen_name=" + username);
		value = getByJSONPath(body, getJsonPath());

		return Double.parseDouble(value);
	}

}
