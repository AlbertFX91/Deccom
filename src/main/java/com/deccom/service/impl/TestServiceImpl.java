package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.service.impl.util.RESTServiceException;

public class TestServiceImpl {

	private static final Logger log = LoggerFactory
			.getLogger(TestServiceImpl.class);
	private final static String i18nCodeRoot = "operations.REST";

	// Client
	public static final String USER_AGENT = "Chrome/60.0.3112.101";

	public static void main(String[] args) throws Exception {

		TestServiceImpl http = new TestServiceImpl();
		String url;

		url = "https://api.twitter.com/1.1/statuses/update.json?include_entities=true&oauth_consumer_key=2WpcTxgPurbnhOBE7ThLMK08x&oauth_nonce=kYjzVBB8Y0ZFabxSWbWovY3uYSQ2pTgmZeNu2VS4cg&oauth_signature_method=HMAC-SHA1&oauth_timestamp=1318622958&oauth_token=927121131101933568-voMQGUmzAuLUItWqr3ajm8p3XZ8A7Eq&oauth_version=1.0";

		System.out.println("Testing 1 - Send Http GET request");
		http.getResponse(url);

	}

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

}
