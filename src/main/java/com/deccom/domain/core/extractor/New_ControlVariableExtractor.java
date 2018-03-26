package com.deccom.domain.core.extractor;

import java.util.HashSet;
import java.util.Set;

import com.deccom.domain.core.fields.DeccomFieldClass;

public class New_ControlVariableExtractor {
	private ControlVariableExtractor extractor;
	private Set<DeccomFieldClass> fields;

	public static New_ControlVariableExtractor create(ControlVariableExtractor extractor,
			Set<DeccomFieldClass> fields) {
		return new New_ControlVariableExtractor(extractor, fields);
	}

	public static New_ControlVariableExtractor create() {
		return new New_ControlVariableExtractor();
	}

	public New_ControlVariableExtractor(ControlVariableExtractor extractor, Set<DeccomFieldClass> fields) {
		this.extractor = extractor;
		this.fields = fields;
	}

	public New_ControlVariableExtractor() {
		this.extractor = new ControlVariableExtractorImpl();
		this.fields = new HashSet<DeccomFieldClass>();
	}

	public ControlVariableExtractor getExtractor() {
		return extractor;
	}

	public void setExtractor(ControlVariableExtractor extractor) {
		this.extractor = extractor;
	}

	public Set<DeccomFieldClass> getFields() {
		return fields;
	}

	public void setFields(Set<DeccomFieldClass> fields) {
		this.fields = fields;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extractor == null) ? 0 : extractor.hashCode());
		result = prime * result + ((fields == null) ? 0 : fields.hashCode());
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
		New_ControlVariableExtractor other = (New_ControlVariableExtractor) obj;
		if (extractor == null) {
			if (other.extractor != null)
				return false;
		} else if (!extractor.equals(other.extractor))
			return false;
		if (fields == null) {
			if (other.fields != null)
				return false;
		} else if (!fields.equals(other.fields))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "New_ControlVariableExtractor [extractor=" + extractor + ", fields=" + fields + "]";
	}
	
	
	
}
