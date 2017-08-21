package com.deccom.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
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

		// First, we divide the only string we receive into many strings, one
		// per object
		String[] booksArray1 = response.toString().split("}");
		// We can work better with a list instead of an array
		LinkedList<String> booksList1 = new LinkedList<String>(
				Arrays.asList(booksArray1));
		// We do not want the comma at the end of each object
		booksList1.remove(booksList1.size() - 1);
		String[] booksArray2 = new String[0];

		// This list will include the objects after they have been mapped
		List<Book> books = new LinkedList<Book>();

		// System.out.println(response.toString());
		// log.debug(response.toString());

		for (String book1 : booksList1) {
			// We keep eliminating chars that are not needed
			book1 = book1.substring(2);
			// Now we can get each part of a book
			booksArray2 = book1.split(",\"");
			// We only get the value of each field, not the name of the title at
			// the beginning as well (as an id, we want 3, not id: 3)
			String id = booksArray2[0]
					.substring(booksArray2[0].indexOf(":") + 1);
			String title = booksArray2[1].substring(
					booksArray2[1].indexOf(":") + 1).substring(1);
			String description = booksArray2[2].substring(
					booksArray2[2].indexOf(":") + 1).substring(1);
			// Also, the title and the description do not need those extra
			// quotation marks
			books.add(new Book(Integer.parseInt(id), title.substring(0,
					title.length() - 1), description.substring(0,
					description.length() - 1)));
		}

		// And this is how the mapped books look like
		for (Book book : books) {
			System.out.println(book);
		}

	}
}
