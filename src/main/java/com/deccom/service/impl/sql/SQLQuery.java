package com.deccom.service.impl.sql;

import javax.validation.constraints.NotNull;

public class SQLQuery {

	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private String url;
	@NotNull
	private String query;
	@NotNull
	private String jdbc;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public SQLQuery username(String username) {
		this.username = username;
		return this;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public SQLQuery password(String password) {
		this.password = password;
		return this;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public SQLQuery url(String url) {
		this.url = url;
		return this;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public SQLQuery query(String query) {
		this.query = query;
		return this;
	}

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}

	public SQLQuery jdbc(String jdbc) {
		this.jdbc = jdbc;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((jdbc == null) ? 0 : jdbc.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
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
		SQLQuery other = (SQLQuery) obj;
		if (jdbc == null) {
			if (other.jdbc != null)
				return false;
		} else if (!jdbc.equals(other.jdbc))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
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
		return "SQLQuery [username=" + username + ", password=" + password + ", url=" + url + ", query=" + query
				+ ", jdbc=" + jdbc + "]";
	}

}
