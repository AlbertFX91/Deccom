package com.deccom.domain.core;

public class CVStyle{
	
	public static CVStyle create() {
		return new CVStyle();
	}
	
	public static CVStyle create(String defaultName, String icon, String backgroundColor, String fontColor) {
		return new CVStyle(defaultName, icon, backgroundColor, fontColor);
	}
	
	private String defaultName;
	private String icon;
	private String backgroundColor;
	private String fontColor;
	
	public CVStyle() {
		defaultName = "";
		icon = "";
		backgroundColor = "";
		fontColor = "";
	}
	
	public CVStyle(String defaultName, String icon, String backgroundColor, String fontColor) {
		this.defaultName = defaultName;
		this.icon = icon;
		this.backgroundColor = backgroundColor;
		this.fontColor = fontColor;
	}
	
	public String getDefaultName() {
		return defaultName;
	}

	public void setDefaultName(String defaultName) {
		this.defaultName = defaultName;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public String getFontColor() {
		return fontColor;
	}

	public void setFontColor(String fontColor) {
		this.fontColor = fontColor;
	}
	
}
