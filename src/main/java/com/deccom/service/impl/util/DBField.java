package com.deccom.service.impl.util;

public class DBField {
	private String name;
	private String type;
	private Boolean primaryKey;
	
	public DBField(String name, String type, Boolean primaryKey) {
		this.name = name;
		this.type = type;
		this.primaryKey = primaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Boolean primaryKey) {
		this.primaryKey = primaryKey;
	}

	@Override
	public String toString() {
		return "DBField [name=" + name + ", type=" + type + ", primaryKey=" + primaryKey + "]";
	}
	
	
	
	
	
}
