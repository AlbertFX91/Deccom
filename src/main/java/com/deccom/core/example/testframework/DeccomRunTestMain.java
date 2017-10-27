package com.deccom.core.example.testframework;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;

public class DeccomRunTestMain {
	public static void main(String[] args) throws Exception {
		System.out.println("Testing...");
		
		int passed = 0, failed = 0, count = 0, ignored = 0;
		
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
		
		// Process @DeccomTest
		for(Method method: obj.getDeclaredMethods()) {
			
			// If method is annotated with @DeccomTest
			if(method.isAnnotationPresent(DeccomTest.class)) {
				Annotation annotation = method.getAnnotation(DeccomTest.class);
				DeccomTest deccomTest = (DeccomTest) annotation;
				
				// If enabled = true (default)
				if(deccomTest.enabled()) {
					try {
						method.invoke(obj.newInstance());
						System.out.printf("%s - Test '%s' - passed %n", ++count, method.getName());
						passed++;
					} catch (Throwable ex) {
						System.out.printf("%s - Test '%s' - failed: %s %n", ++count, method.getName(), ex.getCause() );
						failed++;
					}
				} else {
					System.out.printf("%s - Test '%s' - ignored%n", ++count, method.getName());
					ignored++;
				}
			}
		}
		System.out.printf("%nResult: Total: %d, Passed: %d, Failed: %d, Ignored: %d%n", count, passed, failed, ignored);
	}
}
