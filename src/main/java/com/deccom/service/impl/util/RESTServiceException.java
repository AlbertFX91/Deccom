package com.deccom.service.impl.util;

public class RESTServiceException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String i18nCode;
	private String entity;

	public RESTServiceException(String msg, String i18nCode, String entity,
			Throwable cause) {
		super(msg, cause);
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
