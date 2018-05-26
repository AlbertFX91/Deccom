package com.deccom.domain.core.extractor.sql;

import com.deccom.domain.core.CVStyleUtil;
import com.deccom.domain.core.extractor.ControlVariableExtractor;

public class MySQLExtractor extends SQLExtractor implements ControlVariableExtractor {

	public MySQLExtractor() {
		super();

		String jdbc;

		jdbc = "mysql";

		setJdbc(jdbc);
		setStyle(CVStyleUtil.mysql);
	}

	public Double getData() {
		String url;

		url = super.getUrl();

		url += url.contains("?") ? "&" : "?";
		url += "useSSL=false";

		super.setUrl(url);

		return super.getData();
	}
	
}
