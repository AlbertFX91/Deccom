package com.deccom.domain;

import javax.validation.constraints.NotNull;

public class SQLConnection {
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String url;
	
	public SQLConnection(String username, String password, String url) {
		super();
		this.username = username;
		this.password = password;
		this.url = url;
	}
	
	public SQLConnection() {
		super();
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public SQLConnection username(String username) {
		this.username = username;
		return this;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public SQLConnection password(String password) {
		this.password = password;
		return this;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public SQLConnection url(String url) {
		this.url = url;
		return this;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		SQLConnection other = (SQLConnection) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SQLConnection [username=" + username + ", url=" + url + "]";
	}
	
	
	
}
