package com.deccom.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "sqlControlVar")
public class SQLControlVar implements Serializable{
	
	private static final long serialVersionUID = -7617834633279947898L;

	@Id
	private String id;
	
	@NotNull
	@Field("name")
	private String name;
	
	@NotNull
	@Field("query")
	private String query;
	
	@NotNull
	@Field("creationMoment")
	private LocalDateTime creationMoment;
	
	@NotNull
	@Field("frequency_sec")
	private Integer frequency_sec;
	
	@NotNull
	@Field("sqlConnection")
	private SQLConnection sqlConnection;

	@NotNull
	@Field("sqlControlVarEntries")
	private List<SQLControlVarEntry> sqlControlVarEntries;
	
	public SQLControlVar() {
		super();
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(LocalDateTime creationMoment) {
		this.creationMoment = creationMoment;
	}
	
	public Integer getFrequency_sec() {
		return frequency_sec;
	}

	public void setFrequency_sec(Integer frequency_sec) {
		this.frequency_sec = frequency_sec;
	}

	public SQLConnection getSqlConnection() {
		return sqlConnection;
	}

	public void setSqlConnection(SQLConnection sqlConnection) {
		this.sqlConnection = sqlConnection;
	}

	public List<SQLControlVarEntry> getSqlControlVarEntries() {
		return sqlControlVarEntries;
	}

	public void setSqlControlVarEntries(List<SQLControlVarEntry> sqlControlVarEntries) {
		this.sqlControlVarEntries = sqlControlVarEntries;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SQLControlVar other = (SQLControlVar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SQLControlVar [id=" + id + ", name=" + name + ", query=" + query + ", creationMoment=" + creationMoment
				+ ", frequency_sec=" + frequency_sec
				+ ", sqlConnection=" + sqlConnection + "]";
	}

	
	
	
	
}
