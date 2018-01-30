package com.deccom.domain.core.sql;

import com.deccom.domain.core.Core_Style;

public class Core_SQLStyle implements Core_Style {
	
	String defaultName = "SQL Extractor";
	String icon = "fa-database";
	String backgroundColor = "#3E2723";
	String fontColor = "#FFFFFF";
	
	
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
