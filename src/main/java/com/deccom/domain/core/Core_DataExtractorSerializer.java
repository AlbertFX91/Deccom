package com.deccom.domain.core;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class Core_DataExtractorSerializer extends JsonSerializer<Core_DataExtractor>{

	@Override
	public void serialize(Core_DataExtractor extractor, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
        jgen.writeStringField("_class", extractor.getClass().getName());
        jgen.writeObjectField("style", extractor.getStyle());
        jgen.writeEndObject();
	}
	
}
