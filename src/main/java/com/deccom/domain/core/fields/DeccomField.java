package com.deccom.domain.core.fields;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // The annotation is retained in runtime
@Target(ElementType.FIELD)
public @interface DeccomField {
	
	InputType type() default InputType.TEXT;
	
	String component() default "";
}
