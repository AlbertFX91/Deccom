package com.deccom.service.impl.sql;

import java.io.Serializable;

public class SQLField implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3374346576811481976L;
	private String name;
	private Boolean isPrimaryKey;
	
	public SQLField() {
		this.name = "";
		this.isPrimaryKey = false;
	}
	
	public SQLField(String name, Boolean isPrimaryKey) {
		this.name = name;
		this.isPrimaryKey = isPrimaryKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getIsPrimaryKey() {
		return isPrimaryKey;
	}

	public void setIsPrimaryKey(Boolean isPrimaryKey) {
		this.isPrimaryKey = isPrimaryKey;
	}

	@Override
	public String toString() {
		return "DBField [name=" + name + ", isPrimaryKey=" + isPrimaryKey + "]";
	}
	
	
	
	
	
}
