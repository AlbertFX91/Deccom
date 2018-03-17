package com.deccom.domain.core.extractor;

import com.deccom.domain.core.CVStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ControlVariableExtractorImpl.class)
public interface ControlVariableExtractor {

	@JsonIgnore
	Integer getData();
	CVStyle getStyle();
	default String getUid() {
		// Get a positive uid by the class name
		return "" + (this.getClass().getName().toString().hashCode() & 0xffffff);
	};
	default String getExtractorClass() {
		return this.getClass().getName();
	}
}
