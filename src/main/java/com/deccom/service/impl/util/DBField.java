package com.deccom.service.impl.util;

import java.io.Serializable;

public class DBField implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3374346576811481976L;
	private String name;
	private Boolean isPrimaryKey;
	
	public DBField() {
		this.name = "";
		this.isPrimaryKey = false;
	}
	
	public DBField(String name, Boolean isPrimaryKey) {
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
