package com.deccom.domain.core.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.deccom.domain.core.Core_DataExtractor;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // On class level
public @interface Core_Extractor {
	Class<? extends Core_DataExtractor> value();
}
