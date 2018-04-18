package com.deccom.service.impl.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SQLResponse implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3490475895498731175L;
	private SQLMetadata metadata;
	private List<Map<String, String>> data;
	
	public SQLResponse() {
		this.metadata = new SQLMetadata();
		this.data = new ArrayList<>();
	}
	
	public SQLResponse(SQLMetadata metadata, List<Map<String, String>> data) {
		this.metadata = metadata;
		this.data = data;
	}

	public SQLMetadata getMetadata() {
		return metadata;
	}

	public void setDbMetadata(SQLMetadata metadata) {
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
