package com.deccom.domain.core.extractor.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotNull;

import com.deccom.domain.core.CVStyle;
import com.deccom.domain.core.extractor.ControlVariableExtractor;
import com.deccom.domain.core.fields.DeccomField;
import com.deccom.domain.core.fields.InputType;
import com.deccom.service.impl.util.SQLUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class SQLExtractor implements ControlVariableExtractor {

	@NotNull
	@DeccomField()
	private String username;
	
	@NotNull
	@DeccomField(type=InputType.PASSWORD)
	private String password;
	
	@NotNull
	@DeccomField(type=InputType.URL)
	private String url;
	
	@NotNull
	@DeccomField(component="sql.query.field")
	private String query;

	@NotNull
	@DeccomField()
	private String jdbc;
	
	@NotNull
	private CVStyle style;

	public SQLExtractor() {
		style = CVStyle.create("SQL", "fa-database", "#EF6C00", "#000000");
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getJdbc() {
		return jdbc;
	}

	public void setJdbc(String jdbc) {
		this.jdbc = jdbc;
	}

	@Override
	public CVStyle getStyle() {
		return style;
	}

	public void setStyle(CVStyle style) {
		this.style = style;
	}

	@Override
	@JsonIgnore
	public Integer getData() {
		// TODO Auto-generated method stub
		return Integer.parseInt(getFromSQL());
	}

	protected String getFromSQL() {
		String value;
		ResultSet rs;
		Connection connection;

		connection = SQLUtil.connect(getJdbc(), getUrl(), getUsername(), getPassword());
		rs = SQLUtil.executeQuery(connection, getQuery());
		List<Map<String, String>> data = SQLUtil.collectAll(rs);

		// Only one row is accepted
		if (data.size() != 1) {
			throw new RuntimeException("Wrong Control Variable: Data collection with more than one result");
		}

		// Only one row with one column is accepted
		Map<String, String> entry = data.get(0);
		if (entry.size() != 1) {
			throw new RuntimeException("Wrong Control Variable: Data collection with more than one column");
		}

		value = new ArrayList<>(entry.values()).get(0);

		return value;
	}

}
