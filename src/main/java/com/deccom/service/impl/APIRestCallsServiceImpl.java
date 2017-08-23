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

	public static void main(String[] args) throws Exception {

		APIRestCallsServiceImpl http = new APIRestCallsServiceImpl();

		System.out.println("Testing 1 - Send Http GET request");
		http.noMapping();

	}

	private final String USER_AGENT = "Chrome/60.0.3112.101";

	// HTTP GET request
	public List<Map<String, Object>> noMapping() throws Exception {

		String url = "https://jsonplaceholder.typicode.com/posts";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// print result
		System.out.println(response.toString());

		JSONArray jsonArray = new JSONArray(response.toString());
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		ObjectMapper mapper = new ObjectMapper();

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject finalObject = jsonArray.getJSONObject(i);
			Map<String, Object> map = mapper.readValue(finalObject.toString(), new TypeReference<Map<String, String>>(){});
			result.add(map);
		}
		
		// This array is created from the response, and contains all the JSON
		// objects to be returned
		return result;

	}

	// HTTP GET request
	public List<Post> mapping() throws Exception {

		String url = "https://jsonplaceholder.typicode.com/posts?userId=1";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		log.debug("\nSending 'GET' request to URL : " + url);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		log.debug(response.toString());

		// This list will contain the posts to be returned when mapped
		List<Post> result = new LinkedList<Post>();

		// This array is created from the response, and contains all the JSON
		// objects to be mapped
		JSONArray jsonArray = new JSONArray(response.toString());

		// This will be used for the parsing of the JSON objects
		Gson gson = new Gson();
		for (int i = 0; i < jsonArray.length(); i++) {
			// Each JSON in the array is dealed with
			JSONObject finalObject = jsonArray.getJSONObject(i);
			// And each one of them is mapped into a Post object
			Post post = gson.fromJson(finalObject.toString(), Post.class);
			// Each post is added to the list
			result.add(post);
		}

		// Finally, the list with the mapped posts from the JSON objects is
		// returned
		return result;

	}

}
