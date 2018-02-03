package com.deccom.newcore.domain;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

/**
 * A ControlVar.
 */
public class ControlVariable {

	@Id
	private String id;
	@NotNull
	private String name;
	@NotNull
	private Status status;
	@NotNull
	private LocalDateTime creationMoment;
	@NotNull
	private List<ControlVariableEntry> controlVarEntries;
	@NotNull
	private DeccomExtractor extractor;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
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

	public List<ControlVariableEntry> getControlVarEntries() {
		return controlVarEntries;
	}

	public void setControlVarEntries(List<ControlVariableEntry> controlVarEntries) {
		this.controlVarEntries = controlVarEntries;
	}

	public DeccomExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(DeccomExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	public String toString() {
		return "ControlVar [id=" + id + ", name=" + name + ", creationMoment="
				+ creationMoment + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((creationMoment == null) ? 0 : creationMoment.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		ControlVariable other = (ControlVariable) obj;
		if (creationMoment == null) {
			if (other.creationMoment != null)
				return false;
		} else if (!creationMoment.equals(other.creationMoment))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
