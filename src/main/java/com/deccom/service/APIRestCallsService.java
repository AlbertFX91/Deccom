package com.deccom.service;

import java.util.List;

import com.deccom.domain.Post;

public interface APIRestCallsService {

	/**
	 * Sends a HTTP GET request to an URL
	 * 
	 * @param url
	 *            the url to send the request to
	 * @return the requested JSON as a String
	 */
	// List<Map<String, String>> noMapping(String url) throws Exception;
	String noMapping(String url) throws Exception;

	/**
	 * Sends a HTTP GET request to an URL
	 * 
	 * @param url
	 *            the url to send the request to
	 * @return the requested posts
	 */
	List<Post> mapping(String url) throws Exception;

}
