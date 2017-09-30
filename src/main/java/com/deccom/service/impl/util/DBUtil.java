package com.deccom.service.impl.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.service.impl.DBServiceImpl;
import com.google.common.collect.Lists;

public class DBUtil {

	public static final String i18nCodeRoot = "operations.dbquery";
	private final static Logger log = LoggerFactory.getLogger(DBServiceImpl.class);

	/**
	 * This function create an object DBMetadata with:
	 * - The tables involved in the query
	 * - The query's fields which data like if it's a primary key
	 * @param connection The connection object of the query
	 * @param rs The resultSet of the query execution
	 * @return the DBMetadata result object
	 */
	public static DBMetadata getDBMetadata(Connection connection, ResultSet rs) {
		DBMetadata res = new DBMetadata();
		List<String> tableNames;
		List<String> primaryKeys;
		List<String> fields;
		List<DBField> dbfields = new ArrayList<>();
		try {
			// Return the table names involved in the query
			tableNames = getTableNames(rs);
			// Return the table's primary keys
			primaryKeys = getPrimaryKeys(tableNames, connection);
			// Return all the fields involved in the queries
			fields = getFields(rs);
			// Create all the DBFields by the primary keys and the fields
			dbfields = fields.stream().map((field) -> new DBField(field, primaryKeys.contains(field)))
					.collect(Collectors.toList());
			// Construct the final DBMetadata object
			res.setTables(tableNames);
			res.setFields(dbfields);
		} catch (SQLException e) {
			throw ExceptionHandle(e);
		}
		return res;
	}

	/**
	 * Function which gets all the fields involved in a resulset caused by a query
	 * @param rs the resultset of a query
	 * @return the list of the fields involved
	 * @throws SQLException An exception if it isnt posible to get the num of columns involved
	 */
	public static List<String> getFields(ResultSet rs) throws SQLException {
		List<String> res = Lists.newArrayList();
		// Getting the num of columns of the query result
		Integer numCols = rs.getMetaData().getColumnCount();
		// We loop each column
		IntStream.range(1, numCols + 1).forEach(i -> {
			try {
				// Get the column name, which is the field
				String column = rs.getMetaData().getColumnName(i);
				res.add(column);
			} catch (SQLException e) {
				// This block is not going to be necessary because it control the range of the indexes
				e.printStackTrace();
			}
		});
		return res;
	}

	/**
	 * Get the table's names involved in a resulset of a query
	 * @param rs the result of a query
	 * @return a list witch the tables involved
	 */
	public static List<String> getTableNames(ResultSet rs) {
		Set<String> res = new HashSet<>();
		String table = "";
		Integer i = 1;

		// Because is not posible to control the num of tableNames. Its necessary to create a while(true) and catch the indexOufOutBounds
		try {
			// Recover the metadata of the resulset
			ResultSetMetaData metadata = rs.getMetaData();
			// Get the first table name
			table = metadata.getTableName(i);
			// This while true is dangerous but its controlled by the try... catch clause
			while (true) {
				i++;
				res.add(table);
				table = metadata.getTableName(i);
			}
		} catch (SQLException e) {
			return new ArrayList<>(res);
		}

	}

	/**
	 * Get all the primaryKeys of a list of tables
	 * @param tableNames List of tables to get its primaryKeys
	 * @param connection The connection object involved
	 * @return A list of the primaryKeys fields
	 * @throws SQLException it throws and exception if the getMetadata function its not possible to execute
	 */
	public static List<String> getPrimaryKeys(List<String> tableNames, Connection connection) throws SQLException {
		Set<String> res = new HashSet<>();
		for (String tableName : tableNames) {
			// It collects all the data of the resulset from the getPrimaryKeys function
			List<Map<String, String>> md = collectAll(connection.getMetaData().getPrimaryKeys("", "", tableName));
			// Then, foreach primary key, we get the column_name data, which is the primary key field
			for (Map<String, String> entry : md) {
				res.add(entry.get("COLUMN_NAME"));
			}
		}
		return new ArrayList<>(res);
	}

	/**
	 * Collect all the data result of a ResultSet into a List of Map composed by a key-value
	 * @param rs the result of a queries
	 * @return A list of maps with the data result
	 */
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
						e.printStackTrace();
					}
				});
				res.add(data);
			}
		} catch (SQLException e) {
			throw ThrowDBException("Data extraction error", "extractionerror", "DBService", e);
		}
		return res;
	}

	/**
	 * Return a DBServiceException by a SQLException function. It uses the SQLCode to discredit it
	 * @param e the SQLException error
	 * @return a DBServiceException with a custom msg
	 */
	public static DBServiceException ExceptionHandle(SQLException e) {
		Integer SQLCode = e.getErrorCode();
		String i18nerrorCode;
		switch (SQLCode) {
		case 1045:
			i18nerrorCode = "credentialserror";
			break;
		case 0:
			i18nerrorCode = "connectionerror";
			break;
		default:
			i18nerrorCode = "connectionerror";
			break;
		}
		return ThrowDBException("Cannot connect with the database", i18nerrorCode, "DBService", e);
	}
	
	/**
	 * Function which create a DBServiceException
	 * @param msg the msg to print
	 * @param i18nCode the 118ncode to translate it in Frontend
	 * @param entity the place where the error has been created
	 * @param e the father error
	 * @return the new DBServiceException
	 */
	public static DBServiceException ThrowDBException(String msg, String i18nCode, String entity, Throwable e) {
		return new DBServiceException(msg, i18nCodeRoot + "." + i18nCode, entity, e);

	}
	
	/**
	 * It checks if a driver is operative in the servers
	 * @param driver the class route of the driver
	 */
	public static void checkDriver(String driver) {
		try {
			/*
			 * Sample drivers:
			 * Class.forName("com.mysql.jdbc.Driver");
			 * Class.forName("oracle.jdbc.driver.OracleDriver");
			 */
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			throw ThrowDBException("Cannot find the " + driver + " Class", "drivernotfound", "DBService",e);
		}
	}

	/**
	 * A function which connect with a datasource by a url, username and a passwords
	 * @param url the url connection of the datasource
	 * @param username the username's credential
	 * @param password the password's credential
	 * @return the Connection object
	 */
	public static Connection connect(String url, String username, String password) {
		Connection connection;
		try {
			connection = DriverManager.getConnection(url, username, password);
			log.debug("Database connected");
		} catch (SQLException e) {
			throw DBUtil.ExceptionHandle(e);
		}
		return connection;
	}

	/**
	 * A function which execute a query by a connection object
	 * @param connection the connection object which is going to execute the query
	 * @param query the query to execute
	 * @return the ResultSet returned by the executeQuery method
	 */
	public static ResultSet executeQuery(Connection connection, String query) {
		ResultSet res;
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			res = statement.executeQuery();
		} catch (SQLException e) {
			throw DBUtil.ThrowDBException("Query execution error", "queryerror", "DBService", e);
			// throw new IllegalStateException("Query execution error", e);
		}
		return res;
	}

}
