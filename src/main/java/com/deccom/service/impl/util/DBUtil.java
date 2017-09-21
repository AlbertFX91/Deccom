package com.deccom.service.impl.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.google.common.collect.Lists;

public class DBUtil {
	
	public static final String i18nCodeRoot = "operations.dbquery";
	
	public static DBMetadata getDBMetadata(Connection connection, ResultSet rs) {
		DBMetadata res = new DBMetadata();
		List<String> tableNames;
		List<String> primaryKeys;
		List<String> fields;
		List<DBField> dbfields = new ArrayList<>();
		try {
			tableNames = getTableNames(rs);
			primaryKeys = getPrimaryKeys(tableNames, connection);
			fields = getFields(rs);
			dbfields = fields.stream().map((field) -> new DBField(field, primaryKeys.contains(field)))
					.collect(Collectors.toList());
			res.setTables(tableNames);
			res.setFields(dbfields);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return res;
	}

	public static List<String> getFields(ResultSet rs) throws SQLException {
		List<String> res = Lists.newArrayList();
		// Getting the num of columns of the query result
		Integer numCols = rs.getMetaData().getColumnCount();
		// We loop each column
		IntStream.range(1, numCols + 1).forEach(i -> {
			try {
				String column = rs.getMetaData().getColumnName(i);
				res.add(column);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		return res;
	}

	public static List<String> getTableNames(ResultSet rs) {
		Set<String> res = new HashSet<>();
		String table = "";
		Integer i = 1;

		try {
			ResultSetMetaData metadata = rs.getMetaData();
			table = metadata.getTableName(i);
			while (true) {
				i++;
				res.add(table);
				table = metadata.getTableName(i);
			}
		} catch (SQLException e) {
			return new ArrayList<>(res);
		}

	}

	public static List<String> getPrimaryKeys(List<String> tableNames, Connection connection) throws SQLException {
		Set<String> res = new HashSet<>();
		for (String tableName : tableNames) {
			List<Map<String, String>> md = collectAll(connection.getMetaData().getPrimaryKeys("", "", tableName));
			for (Map<String, String> entry : md) {
				res.add(entry.get("COLUMN_NAME"));
			}
		}
		return new ArrayList<>(res);
	}
	

	public static List<Map<String, String>> collectAll(ResultSet rs) {
		List<Map<String, String>> res = Lists.newArrayList();
		try {
			while (rs.next()) {
				// Getting the num of columns of the query result
				Integer numCols = rs.getMetaData().getColumnCount();
				// Map with K: Column, V: Value
				Map<String, String> data = new HashMap<>();
				// We loop each column
				IntStream.range(1, numCols + 1).forEach(i -> {
					try {
						String column = rs.getMetaData().getColumnName(i);
						String value = rs.getString(i);
						data.put(column, value);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				});
				res.add(data);
			}
		} catch (SQLException e) {
			throw new DBServiceException("Data extraction error", i18nCodeRoot + ".extractionerror", "DBService", e);
			// throw new IllegalStateException("Data extraction error", e);
		}
		return res;
	}

}
