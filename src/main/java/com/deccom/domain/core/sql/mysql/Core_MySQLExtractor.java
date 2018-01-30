package com.deccom.domain.core.sql.mysql;

import com.deccom.domain.core.Core_DataExtractor;
import com.deccom.domain.core.Core_Style;
import com.deccom.domain.core.sql.Core_SQLConnection;
import com.deccom.domain.core.sql.Core_SQLExtractor;

public class Core_MySQLExtractor extends Core_SQLExtractor implements Core_DataExtractor<Core_SQLConnection>{
	
	private Core_MySQLStyle style = new Core_MySQLStyle();

	@Override
	public Core_Style getStyle() {
		return style;
	}
	
}
