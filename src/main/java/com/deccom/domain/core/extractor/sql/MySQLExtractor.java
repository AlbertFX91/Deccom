package com.deccom.domain.core.extractor.sql;

import com.deccom.domain.core.extractor.ControlVariableExtractor;

public class MySQLExtractor extends SQLExtractor implements ControlVariableExtractor {
	
	public MySQLExtractor() {
		super();
		String jdbc;
		jdbc = "mysql";
		setJdbc(jdbc);
	}

}
