package com.deccom.domain.core.extractor;

import com.deccom.domain.core.CVStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@JsonDeserialize(as = ControlVariableExtractorImpl.class)
public interface ControlVariableExtractor {

	@JsonIgnore
	Integer getData();
	CVStyle getStyle();
}
