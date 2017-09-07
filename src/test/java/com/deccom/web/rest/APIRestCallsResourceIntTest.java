package com.deccom.web.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.deccom.DeccomApp;
import com.deccom.service.APIRestCallsService;
import com.deccom.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the APIRestCallsResource REST controller.
 *
 * @see APIRestCallsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeccomApp.class)
public class APIRestCallsResourceIntTest {

	private static final String URL_1 = "https://jsonplaceholder.typicode.com/posts/1";
	private static final String URL_2 = "https://jsonplaceholder.typicode.com/posts";
	private static final String URL_3 = "https://jsonplaceholder.typicode.com/users";

	@Autowired
	private APIRestCallsService apiRestCallsService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	private MockMvc restAPIRestCallsMockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		APIRestCallsResource apiRestCallsResource = new APIRestCallsResource(
				apiRestCallsService);
		this.restAPIRestCallsMockMvc = MockMvcBuilders
				.standaloneSetup(apiRestCallsResource)
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setControllerAdvice(exceptionTranslator)
				.setMessageConverters(jacksonMessageConverter).build();

	}

	@Test
	public void sendGETRequestJSON() throws Exception {

		// The response contains a single JSON
		restAPIRestCallsMockMvc
				.perform(get("/api/apirestcalls/nomapping?url=" + URL_1))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void sendGETRequestJSONArray() throws Exception {

		// The response contains a JSON array
		restAPIRestCallsMockMvc
				.perform(get("/api/apirestcalls/nomapping?url=" + URL_2))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void sendGETRequestJSONs() throws Exception {

		// The response contains a JSON with attributes that are JSON objects
		// themselves
		restAPIRestCallsMockMvc
				.perform(get("/api/apirestcalls/nomapping?url=" + URL_3))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

}
