package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.deccom.domain.Post;
import com.deccom.service.APIRestCallsService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

@Service
public class APIRestCallsServiceImpl implements APIRestCallsService {

	private final Logger log = LoggerFactory
			.getLogger(APIRestCallsServiceImpl.class);

	public APIRestCallsServiceImpl() {

	}

	// Client
	private final String USER_AGENT = "Chrome/60.0.3112.101";

	// HTTP GET request
	public List<Map<String, String>> noMapping() throws Exception {

		String url, response;

		url = "https://jsonplaceholder.typicode.com/posts";
		response = getResponse(url);

		// The data structure to be used for each document is Map<String,
		// Object>
		List<Map<String, String>> result = new ArrayList<Map<String, String>>();
		ObjectMapper mapper = new ObjectMapper();

		// If there is more than one JSON in the response, it is an array
		if (checkResponse(response) == true) {

			// This array is created from the response, and contains all the
			// JSON objects to be returned
			JSONArray jsonArray = new JSONArray(response);

			for (int i = 0; i < jsonArray.length(); i++) {

				// Obtaining a JSON object from each document in the JSON array
				JSONObject finalObject = jsonArray.getJSONObject(i);
				// Each document is turned into a Map<String, Object>
				Map<String, String> map = mapper.readValue(
						finalObject.toString(),
						new TypeReference<Map<String, String>>() {
						});
				// A list of them will contain all the documents
				result.add(map);

			}
			// If there is only one JSON in the response, it is not an array
		} else {
			// The JSON is turned into a map
			Map<String, String> map = mapper.readValue(response,
					new TypeReference<Map<String, String>>() {
					});
			// The single JSON is added to the returning list
			result.add(map);
		}

		// Finally, this list contains all the maps representing the documents
		// from the response
		return result;

	}

	// HTTP GET request
	public List<Post> mapping() throws Exception {

		String url, response;

		url = "https://jsonplaceholder.typicode.com/posts";
		response = getResponse(url);

		// This list will contain the posts to be returned when mapped
		List<Post> result = new LinkedList<Post>();

		// This will be used for the parsing of the JSON objects
		Gson gson = new Gson();

		// If there is more than one JSON in the response, it is an array
		if (checkResponse(response) == true) {
			// This array is created from the response, and contains all the
			// JSON objects to be mapped
			JSONArray jsonArray = new JSONArray(response);

			for (int i = 0; i < jsonArray.length(); i++) {

				// Each JSON in the array is dealed with
				JSONObject finalObject = jsonArray.getJSONObject(i);
				// And each one of them is mapped into a Post object
				Post post = gson.fromJson(finalObject.toString(), Post.class);
				// Each post is added to the returning list
				result.add(post);

			}
			// If there is only one JSON in the response, it is not an array
		} else {

			// The single JSON is mapped into a Post object
			Post post = gson.fromJson(response, Post.class);
			// It is added to the returning list
			result.add(post);

		}

		// Finally, the list with the mapped posts from the JSON objects is
		// returned
		return result;

	}

	private String getResponse(String url) throws Exception {

		// The path is transformed into an URL object to establish the
		// connection
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// Optional default is GET
		con.setRequestMethod("GET");

		// Adding request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		// Here we will know if the response is positive or not
		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);

		// Now, it is time to read the data as a string using BufferedReader
		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer result = new StringBuffer();

		// This string will contain the JSON sent as response line by line
		while ((inputLine = in.readLine()) != null) {
			result.append(inputLine);
		}
		in.close();

		// Here is the full response. Now we have to deal with it
		return result.toString();

	}

	// This method tells if the response contains an array with many JSON
	// objects or just one JSON object
	private Boolean checkResponse(String response) {

		char character;

		character = response.charAt(0);

		return character == '[';

	}

}
