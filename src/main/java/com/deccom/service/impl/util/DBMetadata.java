package com.deccom.service.impl.util;

import java.util.ArrayList;
import java.util.List;

public class DBMetadata {
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
		return this.getFields();
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
