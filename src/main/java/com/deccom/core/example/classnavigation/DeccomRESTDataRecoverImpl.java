package com.deccom.core.example.classnavigation;

public class DeccomRESTDataRecoverImpl implements DeccomRESTDataRecover{

	private String xpath;

	private String url;
	
	public DeccomRESTDataRecoverImpl() {
		this.url = "";
		this.xpath = "";
	}
	
	public DeccomRESTDataRecoverImpl(String url, String xpath) {
		this.url = url;
		this.xpath = xpath;
	}
	
	public String getXpath() {
		return xpath;
	}

	public void setXpath(String xpath) {
		this.xpath = xpath;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	
	
	@Override
	public String getData() {
		return "RESTDataExample";
	}

	@Override
	public boolean connect() {
		System.out.printf("%nConnection to '%s'...",this.url);
		return true;
	}

}
