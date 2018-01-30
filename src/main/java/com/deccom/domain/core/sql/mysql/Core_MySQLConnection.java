package com.deccom.domain.core.sql.mysql;

import com.deccom.domain.core.annotation.Core_Extractor;
import com.deccom.domain.core.sql.Core_SQLConnection;

@Core_Extractor(Core_MySQLExtractor.class)
public class Core_MySQLConnection extends Core_SQLConnection{
	
	public Core_MySQLConnection() {
		super();
		setProperties();
	}
	
	private void setProperties() {
		super.setJdbc("mysql");
	}
	
}
