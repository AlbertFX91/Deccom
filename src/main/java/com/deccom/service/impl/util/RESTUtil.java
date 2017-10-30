package com.deccom.service.impl.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.service.impl.RESTServiceImpl;
import com.jayway.jsonpath.JsonPath;

public class RESTUtil {

	private static final Logger log = LoggerFactory
			.getLogger(RESTServiceImpl.class);
	private static final String i18nCodeRoot = "operations.REST";

	// Client
	public static final String USER_AGENT = "Chrome/60.0.3112.101";

	/**
	 * Sends a HTTP GET request to an URL.
	 * 
	 * @param url
	 *            the url to send the request to
	 * @return the requested JSON as a String
	 */
	public static String getResponse(String url) throws Exception {

		URL obj;
		HttpURLConnection con;
		int responseCode;
		BufferedReader in;
		String inputLine, result;
		StringBuffer response;

		try {
			// The path is transformed into an URL object to establish the
			// connection
			obj = new URL(url);
			con = (HttpURLConnection) obj.openConnection();

			// Optional default is GET
			con.setRequestMethod("GET");

			// Adding request header
			con.setRequestProperty("User-Agent", USER_AGENT);

			// Here we will know if the response is positive or not
			responseCode = con.getResponseCode();
			log.debug("\nSending 'GET' request to URL : " + url);
			log.debug("Response Code : " + responseCode);

			// Now, it is time to read the data as a string using BufferedReader
			in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			response = new StringBuffer();

			// This string will contain the JSON sent as response line by line
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			// Here is the full response. Now we have to deal with it
			result = response.toString();

			return result;

		} catch (MalformedURLException e) {
			throw new RESTServiceException("Wrong URL format", i18nCodeRoot
					+ ".urlerror", "RESTService", e);
		}

		catch (java.net.UnknownHostException | java.io.FileNotFoundException e) {
			throw new RESTServiceException("Unreachable URL", i18nCodeRoot
					+ ".unreachableurl", "RESTService", e);
		}

	}

	/**
	 * Tells if the response contains an array with many JSON objects or just
	 * one JSON object.
	 * 
	 * @param response
	 *            the response to check
	 * @return the answer to whether the request it is an array or not
	 */
	public static Boolean checkResponse(String response) {

		char character;

		character = response.charAt(0);

		return character == '[';

	}

	/**
	 * Sends a HTTP GET request to an URL and captures the data using a
	 * JSONPath.
	 * 
	 * @param url
	 *            the URL to send the request to
	 * @param jsonPath
	 *            the JSONPath query to capture the data
	 * @return the data captured
	 */
	public static String getByJSONPath(String value, String jsonPath)
			throws Exception {

		return JsonPath.parse(value).read(jsonPath).toString();

	}

}
