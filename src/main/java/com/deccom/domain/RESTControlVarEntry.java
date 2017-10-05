package com.deccom.domain;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.data.mongodb.core.mapping.Field;

/**
 * A RESTControlVar.
 */
public class RESTControlVarEntry {

	@NotNull
	@Field("value")
	private String value;

	@NotNull
	@Field("creationMoment")
	private LocalDateTime creationMoment;

	public RESTControlVarEntry(String value, LocalDateTime creationMoment) {

		super();
		this.value = value;
		this.creationMoment = creationMoment;

	}

	public RESTControlVarEntry() {

		super();

	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(LocalDateTime creationMoment) {
		this.creationMoment = creationMoment;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RESTControlVarEntry other = (RESTControlVarEntry) obj;
		if (creationMoment == null) {
			if (other.creationMoment != null)
				return false;
		} else if (!creationMoment.equals(other.creationMoment))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationMoment == null) ? 0 : creationMoment.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@Override
	public String toString() {
		return "RESTControlVarEntry [value=" + value + ", creationMoment="
				+ creationMoment + "]";
	}

}
