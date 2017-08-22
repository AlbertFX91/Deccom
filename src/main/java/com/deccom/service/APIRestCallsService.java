package com.deccom.service;

public interface APIRestCallsService {

	/**
	 * Sends a HTTP GET request to http://127.0.0.1:8080/books/.
	 */
	void sendGetBooks() throws Exception;

	/**
	 * Sends a HTTP GET request to
	 * https://jsonplaceholder.typicode.com/posts?userId=1.
	 */
	void sendGetPosts() throws Exception;

}
