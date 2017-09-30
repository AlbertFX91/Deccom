package com.deccom.service.impl.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DBMetadata implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2901949619902445718L;
	private List<String> tables;
	private List<DBField> fields;
	
	public DBMetadata() {
		this.tables = new ArrayList<>();
		this.fields = new ArrayList<>();
	}
	
	public DBMetadata(List<String> tables, List<DBField> fields) {
		this.tables = tables;
		this.fields = fields;
	}
	
	public List<String> getTables() {
		return this.tables;
	}
	
	public List<DBField> getFields() {
		return this.fields;
	}
	
	public void setTables(List<String> tables) {
		this.tables = tables;
	}

	public void setFields(List<DBField> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		return "DBMetadata [tables=" + tables + ", fields=" + fields + "]";
	}
	
	
	
}
