package com.deccom.domain.core.extractor;

import com.deccom.domain.core.CVStyle;
import com.fasterxml.jackson.annotation.JsonIgnore;

public interface ControlVariableExtractor {

	@JsonIgnore
	Integer getData();
	CVStyle getStyle();
}
