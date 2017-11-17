package com.deccom.domain;

import javax.validation.constraints.NotNull;

public class SQLDataRecover {

	@NotNull
	private String query;
	@NotNull
	private String controlVarName;
	@NotNull
	private Integer frequency_sec;
	@NotNull
	private SQLConnection connection;

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public SQLDataRecover query(String query) {
		this.query = query;
		return this;
	}

	public String getControlVarName() {
		return controlVarName;
	}

	public void setControlVarName(String controlVarName) {
		this.controlVarName = controlVarName;
	}

	public SQLDataRecover controlVarName(String controlVarName) {
		this.controlVarName = controlVarName;
		return this;
	}

	public SQLConnection getConnection() {
		return connection;
	}

	public void setConnection(SQLConnection connection) {
		this.connection = connection;
	}

	public SQLDataRecover connection(SQLConnection connection) {
		this.connection = connection;
		return this;
	}

	public Integer getFrequency_sec() {
		return frequency_sec;
	}

	public void setFrequency_sec(Integer frequency_sec) {
		this.frequency_sec = frequency_sec;
	}

	public SQLDataRecover frequency_sec(Integer frequency_sec) {
		this.frequency_sec = frequency_sec;
		return this;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((connection == null) ? 0 : connection.hashCode());
		result = prime * result + ((controlVarName == null) ? 0 : controlVarName.hashCode());
		result = prime * result + ((frequency_sec == null) ? 0 : frequency_sec.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
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
		SQLDataRecover other = (SQLDataRecover) obj;
		if (connection == null) {
			if (other.connection != null)
				return false;
		} else if (!connection.equals(other.connection))
			return false;
		if (controlVarName == null) {
			if (other.controlVarName != null)
				return false;
		} else if (!controlVarName.equals(other.controlVarName))
			return false;
		if (frequency_sec == null) {
			if (other.frequency_sec != null)
				return false;
		} else if (!frequency_sec.equals(other.frequency_sec))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SQLDataRecover [query=" + query + ", controlVarName=" + controlVarName + ", frequency_sec="
				+ frequency_sec + ", connection=" + connection + "]";
	}

}
