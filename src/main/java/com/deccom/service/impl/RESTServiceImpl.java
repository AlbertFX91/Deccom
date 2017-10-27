package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.service.RESTService;
import com.deccom.service.impl.util.RESTServiceException;
import com.jayway.jsonpath.JsonPath;

@Service
public class RESTServiceImpl implements RESTService {

	private final Logger log = LoggerFactory.getLogger(RESTServiceImpl.class);
	private final String i18nCodeRoot = "operations.REST";

	public RESTServiceImpl() {

	}

	// Client
	private final String USER_AGENT = "Chrome/60.0.3112.101";

	// HTTP GET request
	public Page<String> noMapping(String url, Pageable pageable)
			throws Exception {

		String response;
		List<String> list;
		int pageNumber;
		int pageSize;
		int firstElement;
		Page<String> page = null;

		response = getResponse(url);
		list = new LinkedList<>();
		pageNumber = pageable.getPageNumber();
		pageSize = pageable.getPageSize();
		firstElement = pageNumber * pageSize;

		try {

			// If there is more than one JSON in the response, it is an array
			if (checkResponse(response)) {

				// This array is created from the response, and contains all the
				// JSON objects to be returned
				JSONArray jsonArray;
				jsonArray = new JSONArray(response);

				for (int i = firstElement; i < jsonArray.length()
						&& i < firstElement + pageSize; i++) {

					JSONObject finalObject;

					finalObject = jsonArray.getJSONObject(i);

					list.add(finalObject.toString());

				}

				page = new PageImpl<>(list, null, jsonArray.length());

				// If there is only one JSON in the response, it is not an array
			} else {

				JSONObject finalObject;

				finalObject = new JSONObject(response);

				list.add(finalObject.toString());

				page = new PageImpl<>(list, null, 1);
			}

			// Finally, this list contains all the maps representing the
			// documents from the response

			return page;

		} catch (JSONException e) {
			throw new RESTServiceException("Wrong JSON format", i18nCodeRoot
					+ ".jsonerror", "RESTService", e);
		}

	}

	public Page<String> noMapping(String url) throws Exception {
		Page<String> result;
		Pageable pageable;

		pageable = new PageRequest(0, 20);

		result = noMapping(url, pageable);

		return result;
	}

	// HTTP GET request
	public String getByJsonPath(String url, String jsonPath) throws Exception {

		String result;

		result = this.noMapping(url).getContent().toString();

		return JsonPath.parse(result).read(jsonPath).toString();

	}

	private String getResponse(String url) throws Exception {

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

	// This method tells if the response contains an array with many JSON
	// objects or just one JSON object
	private Boolean checkResponse(String response) {

		char character;

		character = response.charAt(0);

		return character == '[';

	}

}
