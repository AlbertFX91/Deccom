package com.deccom.domain.core.extractor;

import javax.validation.constraints.NotNull;

import com.deccom.domain.core.CVStyle;

public class RandomExtractor implements ControlVariableExtractor {

	@NotNull
	private CVStyle style;

	public RandomExtractor() {
		style = CVStyle.create("Random Extractor", "fa fa-asterisk", "#FF0000", "#FFFFFF");
	}

	@Override
	public Double getData() {
		return (Math.random() * 100);
	}

	@Override
	public CVStyle getStyle() {
		return style;
	}

	public void setStyle(CVStyle style) {
		this.style = style;
	}

	@Override
	public String toString() {
		return "RandomExtractor []";
	}

}
