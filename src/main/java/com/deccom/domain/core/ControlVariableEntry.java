package com.deccom.domain.core;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

/**
 * A ControlVarEntry.
 */
public class ControlVariableEntry {

	@NotNull
	private Double value;
	@NotNull
	private LocalDateTime creationMoment;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public LocalDateTime getCreationMoment() {
		return creationMoment;
	}

	public void setCreationMoment(LocalDateTime creationMoment) {
		this.creationMoment = creationMoment;
	}

	@Override
	public String toString() {
		return "ControlVarEntry [value=" + value + ", creationMoment="
				+ creationMoment + "]";
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ControlVariableEntry other = (ControlVariableEntry) obj;
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

}
