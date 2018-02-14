package com.deccom.domain.core.extractor;

public class MySQLExtractor extends SQLExtractor implements ControlVariableExtractor {
	
	public MySQLExtractor() {
		super();
		String jdbc;
		jdbc = "mysql";
		setJdbc(jdbc);
	}

}
