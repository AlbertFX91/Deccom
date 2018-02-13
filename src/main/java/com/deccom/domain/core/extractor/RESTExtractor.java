package com.deccom.domain.core.extractor;

import java.net.HttpURLConnection;

import javax.validation.constraints.NotNull;

import com.deccom.domain.core.CVStyle;
import com.deccom.service.impl.util.RESTUtil;

public class RESTExtractor implements ControlVariableExtractor {

	@NotNull
	private String url;
	@NotNull
	private String jsonPath;
	@NotNull
	private CVStyle style;
	
	public RESTExtractor() {
		style = CVStyle.create("API REST", "fa-file-code", "#4DB6AC", "#000000");
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
		
		String body, value;
		
		body = getResponse(url);
		value = getByJSONPath(body, getJsonPath());
		
		return Integer.parseInt(value);
		
	}

	@Override
	public CVStyle getStyle() {
		return style;
	}

	protected String getResponse(String url) {
		return RESTUtil.getResponse(url);
	}
	
	protected String getResponse(HttpURLConnection con) {
		return RESTUtil.getResponse(con);
	}
	
	protected String getByJSONPath(String body, String jsonPath) {
		return RESTUtil.getByJSONPath(body, jsonPath);
	}
	
}
