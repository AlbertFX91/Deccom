package com.deccom.core.example.testframework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) // The annotation is retained in runtime
@Target(ElementType.METHOD) 		// Can use in method only.
public @interface DeccomTest { 		// @interface tells Java this is a custom annotation.
	
	// Should ignore this test?
	public boolean enabled() default true;
}
