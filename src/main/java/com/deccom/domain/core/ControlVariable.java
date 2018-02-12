package com.deccom.domain.core;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;

import com.deccom.domain.core.extractor.ControlVariableExtractor;

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
	@Min(value=1)
	private Integer frequency;
	@NotNull
	private List<ControlVariableEntry> controlVarEntries;
	@NotNull
	private ControlVariableExtractor extractor;

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

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public List<ControlVariableEntry> getControlVarEntries() {
		return controlVarEntries;
	}

	public void setControlVarEntries(
			List<ControlVariableEntry> controlVarEntries) {
		this.controlVarEntries = controlVarEntries;
	}

	public ControlVariableExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(ControlVariableExtractor extractor) {
		this.extractor = extractor;
	}

	@Override
	public String toString() {
		return "ControlVariable [id=" + id + ", name=" + name + ", status="
				+ status + ", creationMoment=" + creationMoment
				+ ", frequency=" + frequency + ", controlVarEntries="
				+ controlVarEntries + ", extractor=" + extractor + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((controlVarEntries == null) ? 0 : controlVarEntries
						.hashCode());
		result = prime * result
				+ ((creationMoment == null) ? 0 : creationMoment.hashCode());
		result = prime * result
				+ ((extractor == null) ? 0 : extractor.hashCode());
		result = prime * result
				+ ((frequency == null) ? 0 : frequency.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		if (controlVarEntries == null) {
			if (other.controlVarEntries != null)
				return false;
		} else if (!controlVarEntries.equals(other.controlVarEntries))
			return false;
		if (creationMoment == null) {
			if (other.creationMoment != null)
				return false;
		} else if (!creationMoment.equals(other.creationMoment))
			return false;
		if (extractor == null) {
			if (other.extractor != null)
				return false;
		} else if (!extractor.equals(other.extractor))
			return false;
		if (frequency == null) {
			if (other.frequency != null)
				return false;
		} else if (!frequency.equals(other.frequency))
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
		if (status != other.status)
			return false;
		return true;
	}

}
