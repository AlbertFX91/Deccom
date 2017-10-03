package com.deccom.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RESTConnection.
 */
public class RESTConnection {

	@NotNull
	@Field("url")
	private String url;

	public RESTConnection(String url) {

		super();
		this.url = url;

	}
	
	public RESTConnection() {
		
		super();
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RESTConnection other = (RESTConnection) obj;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RESTConnection [url=" + url + "]";
	}

}
