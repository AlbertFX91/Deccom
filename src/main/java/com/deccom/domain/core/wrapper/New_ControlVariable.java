package com.deccom.domain.core.wrapper;

import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.deccom.domain.core.ControlVariable;

public class New_ControlVariable {
	@NotNull
	private String extractorClass;
	@NotNull
	private Map<String, String> extractorData;
	@Valid
	private ControlVariable controlVariable;
	
	public String getExtractorClass() {
		return extractorClass;
	}
	public void setExtractorClass(String extractorClass) {
		this.extractorClass = extractorClass;
	}
	public Map<String, String> getExtractorData() {
		return extractorData;
	}
	public void setExtractorData(Map<String, String> extractorData) {
		this.extractorData = extractorData;
	}
	public ControlVariable getControlVariable() {
		return controlVariable;
	}
	public void setControlVariable(ControlVariable controlVariable) {
		this.controlVariable = controlVariable;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((controlVariable == null) ? 0 : controlVariable.hashCode());
		result = prime * result + ((extractorClass == null) ? 0 : extractorClass.hashCode());
		result = prime * result + ((extractorData == null) ? 0 : extractorData.hashCode());
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
		New_ControlVariable other = (New_ControlVariable) obj;
		if (controlVariable == null) {
			if (other.controlVariable != null)
				return false;
		} else if (!controlVariable.equals(other.controlVariable))
			return false;
		if (extractorClass == null) {
			if (other.extractorClass != null)
				return false;
		} else if (!extractorClass.equals(other.extractorClass))
			return false;
		if (extractorData == null) {
			if (other.extractorData != null)
				return false;
		} else if (!extractorData.equals(other.extractorData))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "NewControlVariable [extractorClass=" + extractorClass + ", extractorData=" + extractorData
				+ ", controlVariable=" + controlVariable + "]";
	}
}
