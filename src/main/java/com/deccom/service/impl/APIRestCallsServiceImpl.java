package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.deccom.domain.Book;
import com.deccom.service.APIRestCallsService;

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
		http.sendGet();
	}

	// HTTP GET request
	public void sendGet() throws Exception {

		// String url = "https://jsonplaceholder.typicode.com/comments";
		// String url = "https://jsonplaceholder.typicode.com/comments/1";
		String url = "http://127.0.0.1:8080/books/";
		// String url = "http://127.0.0.1:8080/books/1";

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

		String[] booksArray = response.toString().split("}");
		LinkedList<String> booksList = new LinkedList<String>(Arrays.asList(booksArray));
		booksList.remove(booksList.size() - 1);
		List<Book> books = new ArrayList<Book>();

		// System.out.println(response.toString());
		// log.debug(response.toString());
		for (String book : booksList) {
			book = book.substring(2);
		}

	}

}
