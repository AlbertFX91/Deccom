package com.deccom.domain.core.rest;

import com.deccom.domain.core.Core_Style;

public class Core_RESTStyle implements Core_Style {
	
	String defaultName = "REST Extractor";
	String icon = "fa-globe";
	String backgroundColor = "#FF6D00";
	String fontColor = "#000000";
	
	
	@Override
	public String getDefaultName() {
		return defaultName;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public String getBackgroundColor() {
		return backgroundColor;
	}

	@Override
	public String getFontColor() {
		return fontColor;
	}

}
