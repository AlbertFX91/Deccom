package com.deccom.core.example.testframework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE) // On class level
public @interface DeccomTesterInfo {
	
	public enum Priority { LOW, MEDIUM, HIGH }
	
	Priority priority() default Priority.MEDIUM;
	
	String[] tags() default "";
	
	String createdBy() default "DeccomDev";
	
	String lastModified() default "23/10/2017";
}
