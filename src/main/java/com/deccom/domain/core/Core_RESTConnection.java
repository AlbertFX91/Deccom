package com.deccom.domain.core;

import com.deccom.domain.core.annotation.Core_Extractor;

@Core_Extractor(Core_RESTExtractor.class)
public class Core_RESTConnection implements Core_Connection {
	private String url;
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
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((jsonPath == null) ? 0 : jsonPath.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Core_RESTConnection other = (Core_RESTConnection) obj;
		if (jsonPath == null) {
			if (other.jsonPath != null)
				return false;
		} else if (!jsonPath.equals(other.jsonPath))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Core_RESTConnection [url=" + url + ", jsonPath=" + jsonPath + "]";
	}
	
	
	
}
