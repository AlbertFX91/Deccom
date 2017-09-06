package com.deccom.service.impl.util;

public class DBServiceException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String i18nCode;
	private String entity;
	
	public DBServiceException(String msg, String i18nCode, String entity) {
		super(msg);
		this.i18nCode = i18nCode;
		this.entity = entity;
	}
	
	public String getI18nCode() {
		return this.i18nCode;
	}
	
	public String getEntity() {
		return this.entity;
	}
}
