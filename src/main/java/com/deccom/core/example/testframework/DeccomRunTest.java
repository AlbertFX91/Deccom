package com.deccom.core.example.testframework;

import java.lang.annotation.Annotation;
import java.util.Arrays;

public class DeccomRunTest {
	public static void main(String[] args) throws Exception {
		System.out.println("Testing...");
		
		int passed = 0, failed = 0, count = 0, ignore = 0;
		
		Class<DeccomTestExample> obj = DeccomTestExample.class;
		
		// Process @DeccomTesterInfo
		if(obj.isAnnotationPresent(DeccomTesterInfo.class)) {
			
			Annotation annotation = obj.getAnnotation(DeccomTesterInfo.class);
			DeccomTesterInfo deccomTesterInfo = (DeccomTesterInfo) annotation;
			
			System.out.printf("%nPriority: %s", deccomTesterInfo.priority());
			System.out.printf("%nCreatedBy: %s", deccomTesterInfo.createdBy());
			System.out.printf("%nTags: %s", Arrays.stream(deccomTesterInfo.tags()).reduce("", (x,y)->x+y+","));
			System.out.printf("%nLastModified: %s%n%n", deccomTesterInfo.lastModified());
			
		}
	}
}
