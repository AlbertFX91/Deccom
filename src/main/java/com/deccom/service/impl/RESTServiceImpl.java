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

import com.deccom.domain.Post;
import com.deccom.service.RESTService;
import com.deccom.service.impl.util.RESTServiceException;
import com.google.gson.Gson;
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
		// ObjectMapper mapper;
		// List<Map<String, String>> result;
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
		System.out.println(pageable.toString());

		// The data structure to be used for each document is Map<String,
		// Object>
		// mapper = new ObjectMapper();
		// result = new ArrayList<Map<String, String>>();

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

					// Map<String, String> map;
					// Obtaining a JSON object from each document in the JSON
					// array
					// finalObject = jsonArray.getJSONObject(i);
					// Each document is turned into a Map<String, Object>
					// map = mapper.readValue(finalObject.toString(),
					// new TypeReference<Map<String, String>>() {
					// });
					// A list of them will contain all the documents
					// result.add(map);

				}

				page = new PageImpl<>(list, null, jsonArray.length());

				// If there is only one JSON in the response, it is not an array
			} else {

				JSONObject finalObject;

				finalObject = new JSONObject(response);

				list.add(finalObject.toString());

				// The JSON is turned into a map
				// Map<String, String> map;
				// map = mapper.readValue(response,
				// new TypeReference<Map<String, String>>() {
				// });
				// The single JSON is added to the returning list
				// result.add(map);
				page = new PageImpl<>(list, null, 1);
			}

			// Finally, this list contains all the maps representing the
			// documents
			// from the response

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
	public List<Post> mapping(String url) throws Exception {

		String response;
		Gson gson;
		List<Post> result;

		response = getResponse(url);

		// This will be used for the parsing of the JSON objects
		gson = new Gson();

		// This list will contain the posts to be returned when mapped
		result = new LinkedList<Post>();

		try {

			// If there is more than one JSON in the response, it is an array
			if (checkResponse(response)) {
				// This array is created from the response, and contains all the
				// JSON objects to be mapped
				JSONArray jsonArray;
				jsonArray = new JSONArray(response);

				for (int i = 0; i < jsonArray.length(); i++) {

					JSONObject finalObject;
					Post post;
					// Each JSON in the array is dealed with
					finalObject = jsonArray.getJSONObject(i);
					// And each one of them is mapped into a Post object
					post = gson.fromJson(finalObject.toString(), Post.class);
					// Each post is added to the returning list
					result.add(post);

				}
				// If there is only one JSON in the response, it is not an array
			} else {

				// The single JSON is mapped into a Post object
				Post post;
				post = gson.fromJson(response, Post.class);
				// It is added to the returning object
				result.add(post);

			}

			// Finally, the list with the mapped posts from the JSON objects is
			// returned
			return result;

		} catch (JSONException e) {
			throw new RESTServiceException("Wrong JSON format", i18nCodeRoot
					+ ".queryerror", "RESTService", e);
		}

	}

	// HTTP GET request
	public String getByJsonPath(String url, String jsonPath) throws Exception {

		String result;

		// result = this.noMapping(url);
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

	}

	// This method tells if the response contains an array with many JSON
	// objects or just one JSON object
	private Boolean checkResponse(String response) {

		char character;

		character = response.charAt(0);

		return character == '[';

	}

}
