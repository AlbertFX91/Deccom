package com.deccom.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.deccom.service.RESTService;
import com.deccom.service.impl.util.RESTServiceException;
import com.deccom.service.impl.util.RESTUtil;

@Service
public class RESTServiceImpl implements RESTService {

	private final String i18nCodeRoot = "operations.REST";

	public RESTServiceImpl() {

	}

	// HTTP GET request
	public Page<String> noMapping(String url, Pageable pageable)
			throws Exception {

		String response;
		List<String> list;
		int pageNumber;
		int pageSize;
		int firstElement;
		Page<String> page = null;

		response = RESTUtil.getResponse(url);
		list = new LinkedList<>();
		pageNumber = pageable.getPageNumber();
		pageSize = pageable.getPageSize();
		firstElement = pageNumber * pageSize;

		try {

			// If there is more than one JSON in the response, it is an array
			if (RESTUtil.checkResponse(response)) {

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

}
