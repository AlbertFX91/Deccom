package com.deccom.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RESTService {

	/**
	 * Requests a JSON response.
	 * 
	 * @param url
	 *            the URL to send the request to
	 * @param pageable
	 *            the pagination options
	 * @return the requested JSON as a String
	 */
	Page<String> noMapping(String url, Pageable pageable) throws Exception;

	/**
	 * Send a HTTP Get to an URL and capture the data by a JSONPath.
	 * 
	 * @param url
	 *            the URL to send the request to
	 * @param jsonPath
	 *            the JSONPath query to capture the data
	 * @return the data captured
	 */
	String getByJSONPath(String url, String jsonPath) throws Exception;

}
