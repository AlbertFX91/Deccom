package com.deccom.core.example.testframework;


import com.deccom.core.example.testframework.DeccomTesterInfo.Priority;

@DeccomTesterInfo(
		priority = Priority.HIGH,
		createdBy = "albertfx91@deccom.com",
		tags = {"sales", "test" }
)
public class DeccomTestExample {
	@DeccomTest
	void testA() {
		if(true)
			throw new RuntimeException("this test always failed");
	}
	
	@DeccomTest(enabled = false)
	void testB() {
		throw new RuntimeException("This test always passed");
	}
	
	@DeccomTest(enabled = true)
	void testC() {
		if(10 > 1) {
			// Do nothing, this test always passed
		}
	}
}
