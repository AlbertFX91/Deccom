package com.deccom.service.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deccom.domain.Author;
import com.deccom.domain.SQLQuery;
import com.deccom.service.SQLService;
import com.deccom.service.impl.util.SQLMetadata;
import com.deccom.service.impl.util.SQLResponse;
import com.deccom.service.impl.util.SQLServiceException;
import com.deccom.service.impl.util.SQLUtil;
import com.google.common.collect.Lists;

@Service
public class SQLServiceImpl implements SQLService {
	private final Logger log = LoggerFactory.getLogger(SQLServiceImpl.class);
	private final String i18nCodeRoot = "operations.dbquery";

	public SQLServiceImpl() {
	}

	public List<Map<String, String>> callNoMapping() {
		String url = "jdbc:mysql://localhost:3306/deccom";
		String username = "developer";
		String password = "developer";

		String table = "author";
		String cols = "*";
		String query = "SELECT " + cols + " FROM `" + table + "`";

		// Checking of the driver
		SQLUtil.checkDriver("com.mysql.jdbc.Driver");

		// Data structure for the result data
		List<Map<String, String>> res;

		// Database connection
		Connection connection = SQLUtil.connect(url, username, password);

		// Result of the query execution
		ResultSet rs = SQLUtil.executeQuery(connection, query);

		// Data collection
		res = SQLUtil.collectAll(rs);

		return res;
	}

	public List<Author> callMapping() {
		String url = "jdbc:mysql://localhost:3306/deccom";
		String username = "developer";
		String password = "developer";

		String table = "author";
		String cols = "*";
		String query = "SELECT " + cols + " FROM `" + table + "`";

		// Checking of the driver
		SQLUtil.checkDriver("com.mysql.jdbc.Driver");

		// Data structure for the result data
		List<Author> res;

		// Database connection
		Connection connection = SQLUtil.connect(url, username, password);

		// Result of the query execution
		ResultSet rs = SQLUtil.executeQuery(connection, query);

		// Data collection
		res = collectAsAuthor(rs);

		return res;
	}

	public SQLResponse query(SQLQuery query) {
		String url = query.getUrl();
		String username = query.getUsername();
		String password = query.getPassword();
		String q = query.getQuery();
		SQLMetadata dbMetadata;
		
		// Checking of the driver
		SQLUtil.checkDriver("com.mysql.jdbc.Driver");

		// Data structure for the result data
		List<Map<String, String>> data;

		// Database connection
		Connection connection = SQLUtil.connect(url, username, password);

		// Result of the query execution
		ResultSet rs = SQLUtil.executeQuery(connection, q);

		// Data collection
		data = SQLUtil.collectAll(rs);

		// Get the metadata from the query
		dbMetadata = SQLUtil.getDBMetadata(connection, rs);

		return new SQLResponse(dbMetadata, data);
	}

	public List<Map<String, String>> OracleSQLQuery(String query) {
		String url = "jdbc:oracle:thin:@localhost:1521:orcl12";
		String username = "c##developer";
		String password = "developer";

		// Checking of the driver
		SQLUtil.checkDriver("oracle.jdbc.driver.OracleDriver");

		// Data structure for the result data
		List<Map<String, String>> res;

		// Database connection
		Connection connection = SQLUtil.connect(url, username, password);

		// Result of the query execution
		ResultSet rs = SQLUtil.executeQuery(connection, query);

		// Data collection
		res = SQLUtil.collectAll(rs);

		return res;

	}

	private List<Author> collectAsAuthor(ResultSet rs) {
		List<Author> res = Lists.newArrayList();
		try {
			while (rs.next()) {
				Author author = new Author();

				Integer idAuthor = rs.getInt("idauthor");
				Double avgBooks = rs.getDouble("avgbooks");
				String name = rs.getString("name");
				Integer age = rs.getInt("age");
				LocalDate debut = LocalDate.parse(rs.getString("debut"),
						DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss.S"));

				author.setIdAuthor(idAuthor);
				author.setAvgBooks(avgBooks);
				author.setDebut(debut);
				author.setName(name);
				author.setAge(age);

				log.debug(author.toString());
				res.add(author);
			}
		} catch (SQLException e) {
			throw new SQLServiceException("Data extraction error", i18nCodeRoot + ".extractionerror", "DBService", e);
		}
		return res;
	}

}
