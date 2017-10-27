package com.deccom.core;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.deccom.service.impl.util.RESTServiceException;
import com.jayway.jsonpath.JsonPath;

public class RESTExtractor implements DataExtractor{
	
	private String url;
	private String jsonpath;
	
	private final String i18nCodeRoot = "operations.REST";
	private final String USER_AGENT = "Chrome/60.0.3112.101";
	
	public RESTExtractor() {
		this.url = "";
		this.jsonpath = "";
	}
	
	public RESTExtractor(String url, String jsonpath) {
		this.url = url;
		this.jsonpath = jsonpath;
	}

	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getJsonpath() {
		return jsonpath;
	}
	public void setJsonpath(String jsonpath) {
		this.jsonpath = jsonpath;
	}

	public String getData() {
		String data;
		
		data = getByJsonPath(this.url, this.jsonpath);
		
		return data;
	}
	
	public String getByJsonPath(String url, String jsonPath){

		String result;

		result = this.getJSON(url);

		return JsonPath.parse(result).read(jsonPath).toString();

	}
	
	@SuppressWarnings("unused")
	private String getJSON(String url){
		String response;

		try {
			response = getResponse(url);
			// If there is more than one JSON in the response, it is an array
			if (checkResponse(response)) {
				JSONArray jsonArray = new JSONArray(response);
			} else {
				JSONObject finalObject = new JSONObject(response);
			}
			return response;

		} catch (JSONException e) {
			throw new RESTServiceException("Wrong JSON format", i18nCodeRoot
					+ ".jsonerror", "RESTService", e);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new RESTServiceException("Error getting the response", i18nCodeRoot
					+ ".jsonerror", "RESTService", e);
		}

	}
	
	private String getResponse(String url) throws Exception {

		URL obj;
		HttpURLConnection con;
		@SuppressWarnings("unused")
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
