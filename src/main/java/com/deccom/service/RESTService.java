package com.deccom.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing REST.
 */
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
	 * Requests a JSON response.
	 * 
	 * @param url
	 *            the URL to send the request to
	 * @param pageable
	 *            the pagination options
	 * @return the requested JSON as a String
	 */
	Page<String> noMapping(String url);

}
