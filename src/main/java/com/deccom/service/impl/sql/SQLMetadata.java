package com.deccom.service.impl.sql;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SQLMetadata implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2901949619902445718L;
	private List<String> tables;
	private List<SQLField> fields;
	
	public SQLMetadata() {
		this.tables = new ArrayList<>();
		this.fields = new ArrayList<>();
	}
	
	public SQLMetadata(List<String> tables, List<SQLField> fields) {
		this.tables = tables;
		this.fields = fields;
	}
	
	public List<String> getTables() {
		return this.tables;
	}
	
	public List<SQLField> getFields() {
		return this.fields;
	}
	
	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public void setFields(List<SQLField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "DBMetadata [tables=" + tables + ", fields=" + fields + "]";
	}
	
	
	
}
