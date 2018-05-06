package com.deccom.domain.core.extractor;

import com.deccom.domain.core.CVStyle;

public class ControlVariableExtractorImpl implements ControlVariableExtractor {

	@Override
	public Double getData() {
		return 0.0;
	}

	@Override
	public CVStyle getStyle() {
		return CVStyle.create();
	}

}
