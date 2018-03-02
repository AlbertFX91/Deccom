package com.deccom.domain.core.extractor;

import com.deccom.domain.core.CVStyle;

public class Item_ControlVariableExtractor {

	private String extractorClass;

	private CVStyle style;
	
	private String uid;

	public Item_ControlVariableExtractor(String extractorClass, CVStyle style, String uid) {
		this.extractorClass = extractorClass;
		this.style = style;
		this.uid = uid;
	}

	public Item_ControlVariableExtractor() {
		extractorClass = "";
		style = new CVStyle();
	}

	public String getExtractorClass() {
		return extractorClass;
	}

	public void setExtractorClass(String extractorClass) {
		this.extractorClass = extractorClass;
	}

	public CVStyle getStyle() {
		return style;
	}

	public void setStyle(CVStyle style) {
		this.style = style;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((extractorClass == null) ? 0 : extractorClass.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
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
		Item_ControlVariableExtractor other = (Item_ControlVariableExtractor) obj;
		if (extractorClass == null) {
			if (other.extractorClass != null)
				return false;
		} else if (!extractorClass.equals(other.extractorClass))
			return false;
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Item_ControlVariableExtractor [extractorClass=" + extractorClass + ", style=" + style + ", uid=" + uid
				+ "]";
	}


}
