package com.deccom.domain.core;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
public class Core_ControlVar {
	@Id
	private String id;
	@NotNull
	private String name;
	@NotNull
	private LocalDateTime creationMoment;
	@NotNull
	private Boolean disabled;
	@NotNull
	private Integer frequency_sec;
	
	@NotNull
	private Core_Connection connection;
	@NotNull
	private List<Core_ControlVarEntry> entries;
	
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
	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}
	public void setCreationMoment(LocalDateTime creationMoment) {
		this.creationMoment = creationMoment;
	}
	public Boolean getDisabled() {
		return disabled;
	}
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	public Integer getFrequency_sec() {
		return frequency_sec;
	}
	public void setFrequency_sec(Integer frequency_sec) {
		this.frequency_sec = frequency_sec;
	}
	
	public Core_Connection getConnection() {
		return connection;
	}
	public void setConnection(Core_Connection connection) {
		this.connection = connection;
	}
	public List<Core_ControlVarEntry> getEntries() {
		return entries;
	}
	public void setEntries(List<Core_ControlVarEntry> entries) {
		this.entries = entries;
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
		Core_ControlVar other = (Core_ControlVar) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Core_ControlVar [id=" + id + ", name=" + name + ", creationMoment=" + creationMoment + ", disabled="
				+ disabled + ", frequency_sec=" + frequency_sec + ", connection=" + connection + "]";
	}
	
	
	
}
