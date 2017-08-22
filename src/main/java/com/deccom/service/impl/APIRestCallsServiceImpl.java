package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.Post;
import com.deccom.service.APIRestCallsService;
import com.google.gson.Gson;

public class APIRestCallsServiceImpl implements APIRestCallsService {

	private final Logger log = LoggerFactory
			.getLogger(APIRestCallsServiceImpl.class);

	public APIRestCallsServiceImpl() {

	}

	// private final String USER_AGENT = "Mozilla/5.0";
	private final String USER_AGENT = "Chrome/60.0.3112.101";

	public static void main(String[] args) throws Exception {
		APIRestCallsServiceImpl http = new APIRestCallsServiceImpl();

		System.out.println("Testing 1 - Send Http GET request");
		http.sendGetBooks();
		// http.sendGetPosts();
	}

	// HTTP GET request
	public void sendGetBooks() throws Exception {

		String url = "http://127.0.0.1:8080/books/";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL : " + url);
		log.debug("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// System.out.println(response.toString());
		// log.debug(response.toString());

		JSONArray jsonArray = new JSONArray(response.toString());

		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject finalObject = jsonArray.getJSONObject(i);
			System.out.println("Book: " + finalObject.toString());
		}

	}

	// HTTP GET request
	public void sendGetPosts() throws Exception {

		String url = "https://jsonplaceholder.typicode.com/posts?userId=1";

		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();

		// optional default is GET
		con.setRequestMethod("GET");

		// add request header
		con.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = con.getResponseCode();
		// System.out.println("\nSending 'GET' request to URL : " + url);
		log.debug("\nSending 'GET' request to URL : " + url);
		// System.out.println("Response Code : " + responseCode);
		log.debug("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(
				con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		// System.out.println(response.toString());
		// log.debug(response.toString());

		List<Post> posts = new LinkedList<Post>();

		JSONArray jsonArray = new JSONArray(response.toString());

		Gson gson = new Gson();
		for (int i = 0; i < jsonArray.length(); i++) {
			JSONObject finalObject = jsonArray.getJSONObject(i);
			Post post = gson.fromJson(finalObject.toString(), Post.class);
			posts.add(post);
		}

		// And this is how the mapped books look like
		for (Post post : posts) {
			System.out.println("User1 posts: " + post);
		}
	}
}
