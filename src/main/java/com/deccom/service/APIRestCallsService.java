package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Post;

public interface APIRestCallsService {

	/**
	 * Sends a HTTP GET request to http://127.0.0.1:8080/books/.
	 * 
	 * @return the requested books
	 */
	List<Map<String, Object>> noMapping() throws Exception;

	/**
	 * Sends a HTTP GET request to
	 * https://jsonplaceholder.typicode.com/posts?userId=1.
	 * 
	 * @return the requested posts
	 */
	List<Post> mapping() throws Exception;

}
