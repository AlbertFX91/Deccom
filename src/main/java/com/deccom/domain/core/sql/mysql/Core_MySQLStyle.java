package com.deccom.domain.core.sql.mysql;

import com.deccom.domain.core.Core_Style;

public class Core_MySQLStyle implements Core_Style {
	
	String defaultName = "MySQL Extractor";
	String icon = "fa-database";
	String backgroundColor = "#F4511E";
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
