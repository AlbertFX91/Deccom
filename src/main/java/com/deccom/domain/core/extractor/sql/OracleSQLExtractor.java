package com.deccom.domain.core.extractor.sql;

import com.deccom.domain.core.extractor.ControlVariableExtractor;

public class OracleSQLExtractor extends SQLExtractor implements ControlVariableExtractor {

	public OracleSQLExtractor() {
		super();
		String jdbc;
		jdbc = "oracle";
		setJdbc(jdbc);
	}

}
