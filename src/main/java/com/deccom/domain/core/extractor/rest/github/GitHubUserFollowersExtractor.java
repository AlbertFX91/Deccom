package com.deccom.domain.core.extractor.rest.github;

import com.deccom.domain.core.CVStyleUtil;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.extractor.rest.RESTExtractor;
import com.deccom.domain.core.fields.DeccomField;

public class GitHubUserFollowersExtractor extends RESTExtractor implements ControlVariableExtractor {

	@DeccomField()
	private String username;

	public GitHubUserFollowersExtractor() {

		super();

		String url;

		url = "https://api.github.com/users/";

		setUrl(url);
		setJsonPath("$.followers");
		setStyle(CVStyleUtil.gitHubUserFollowers);
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

		body = super.getResponse(getUrl() + getUsername());
		value = getByJSONPath(body, getJsonPath());

		return Double.parseDouble(value);
	}

}
