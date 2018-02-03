package com.deccom.domain.core;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@JsonSerialize(using = Core_DataExtractorSerializer.class)
public interface Core_DataExtractor {
	String getData();
	Core_Style getStyle();
}
