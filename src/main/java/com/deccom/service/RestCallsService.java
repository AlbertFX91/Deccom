package com.deccom.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.deccom.domain.Post;

public interface RestCallsService {

	/**
	 * Sends a HTTP GET request to an URL
	 * 
	 * @param url
	 *            the url to send the request to
	 * @param pageable
	 *            the options for the pagination
	 * @return the requested JSON as a String
	 */
	String noMapping(String url, Pageable pageable) throws Exception;
	
	/**
	 * Sends a HTTP GET request to an URL
	 * 
	 * @param url
	 *            the url to send the request to
	 * @param pageable
	 *            the options for the pagination
	 * @return the requested JSON as a String
	 */
	String noMapping(String url) throws Exception;

	/**
	 * Sends a HTTP GET request to an URL
	 * 
	 * @param url
	 *            the url to send the request to
	 * @return the requested posts
	 */
	List<Post> mapping(String url) throws Exception;

	/**
	 * Send a HTTP Get to an URL and capture the data by a JSONPath
	 * 
	 * @param url
	 *            the url to send the request to
	 * @param jsonPath
	 *            the jsonpath query to capture the data
	 * @return the data captured
	 */
	String getByJsonPath(String url, String jsonPath) throws Exception;

}
