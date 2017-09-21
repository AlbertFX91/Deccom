package com.deccom.service.impl.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DBResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3490475895498731175L;
	private DBMetadata metadata;
	private List<Map<String, String>> data;
	
	public DBResponse() {
		this.metadata = new DBMetadata();
		this.data = new ArrayList<>();
	}
	
	public DBResponse(DBMetadata metadata, List<Map<String, String>> data) {
		this.metadata = metadata;
		this.data = data;
	}

	public DBMetadata getMetadata() {
		return metadata;
	}

	public void setDbMetadata(DBMetadata metadata) {
		this.metadata = metadata;
	}

	public List<Map<String, String>> getData() {
		return data;
	}

	public void setData(List<Map<String, String>> data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "DBResponse [Metadata=" + metadata + ", data=" + data + "]";
	}
	
	
	
}
