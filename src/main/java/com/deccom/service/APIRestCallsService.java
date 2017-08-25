package com.deccom.service;

import java.util.List;
import java.util.Map;

import com.deccom.domain.Post;

public interface APIRestCallsService {

	/**
	 * Sends a HTTP GET request
	 *
	 * @param url
	 *            the url of the web
	 * @return the response of the request
	 */
	String getResponse(String url) throws Exception;

	/**
	 * Sends a HTTP GET request to https://jsonplaceholder.typicode.com/posts
	 * 
	 * @return the requested books
	 */
	List<Map<String, String>> noMapping() throws Exception;

	/**
	 * Sends a HTTP GET request to
	 * https://jsonplaceholder.typicode.com/posts?userId=1
	 * 
	 * @return the requested posts
	 */
	List<Post> mapping() throws Exception;

}
