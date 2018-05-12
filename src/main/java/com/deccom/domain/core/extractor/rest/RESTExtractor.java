package com.deccom.domain.core.extractor.rest;

import java.net.HttpURLConnection;

import javax.validation.constraints.NotNull;

import com.deccom.domain.core.CVStyle;
import com.deccom.domain.core.CVStyleUtil;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.fields.DeccomField;
import com.deccom.service.impl.util.RESTUtil;

public class RESTExtractor implements ControlVariableExtractor {

	@NotNull
	@DeccomField(display = false)
	private String url;
	
	@NotNull
	@DeccomField(component="rest.jsonPath.field")
	private String jsonPath;
	
	@NotNull
	private CVStyle style;
	
	public RESTExtractor() {
		style = CVStyleUtil.rest;
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
	public Double getData() {
		
		String body, value;
		
		body = getResponse(url);
		value = getByJSONPath(body, getJsonPath());
		
		return Double.parseDouble(value);
		
	}

	@Override
	public CVStyle getStyle() {
		return style;
	}
	
	public void setStyle(CVStyle style) {
		this.style = style;
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
  
	@Override
	public String toString() {
		return "RESTExtractor [url=" + url + ", jsonPath=" + jsonPath + "]";
	}
}
