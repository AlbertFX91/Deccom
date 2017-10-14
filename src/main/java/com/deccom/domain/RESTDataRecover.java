package com.deccom.domain;

import javax.validation.constraints.NotNull;

/**
 * A RESTDataRecover.
 */
public class RESTDataRecover {

	@NotNull
	private String query;
	
	@NotNull
	private String controlVarName;
	
	@NotNull
	private RESTConnection restConnection;

	public RESTDataRecover(String query, String controlVarName,
			RESTConnection restConnection) {

		super();
		this.query = query;
		this.controlVarName = controlVarName;
		this.restConnection = restConnection;

	}
	
	public RESTDataRecover() {
		
		super();
		
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getControlVarName() {
		return controlVarName;
	}

	public void setControlVarName(String controlVarName) {
		this.controlVarName = controlVarName;
	}

	public RESTConnection getRestConnection() {
		return restConnection;
	}

	public void setRestConnection(RESTConnection restConnection) {
		this.restConnection = restConnection;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((controlVarName == null) ? 0 : controlVarName.hashCode());
		result = prime * result + ((query == null) ? 0 : query.hashCode());
		result = prime * result
				+ ((restConnection == null) ? 0 : restConnection.hashCode());
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
		RESTDataRecover other = (RESTDataRecover) obj;
		if (controlVarName == null) {
			if (other.controlVarName != null)
				return false;
		} else if (!controlVarName.equals(other.controlVarName))
			return false;
		if (query == null) {
			if (other.query != null)
				return false;
		} else if (!query.equals(other.query))
			return false;
		if (restConnection == null) {
			if (other.restConnection != null)
				return false;
		} else if (!restConnection.equals(other.restConnection))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RESTDataRecover [query=" + query + ", controlVarName="
				+ controlVarName + ", restConnection=" + restConnection + "]";
	}

}
