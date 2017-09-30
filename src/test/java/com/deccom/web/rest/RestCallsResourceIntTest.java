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
import com.deccom.service.RestCallsService;
import com.deccom.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the RestCallsResource REST controller.
 *
 * @see RestCallsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeccomApp.class)
public class RestCallsResourceIntTest {

	private static final String URL_1 = "https://jsonplaceholder.typicode.com/posts/1";
	private static final String URL_2 = "https://jsonplaceholder.typicode.com/posts";
	private static final String URL_3 = "https://jsonplaceholder.typicode.com/users";

	@Autowired
	private RestCallsService restCallsService;

	@Autowired
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Autowired
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	@Autowired
	private ExceptionTranslator exceptionTranslator;

	private MockMvc restRestCallsMockMvc;

	@Before
	public void setup() {

		MockitoAnnotations.initMocks(this);
		RestCallsResource restCallsResource = new RestCallsResource(
				restCallsService);
		this.restRestCallsMockMvc = MockMvcBuilders
				.standaloneSetup(restCallsResource)
				.setCustomArgumentResolvers(pageableArgumentResolver)
				.setControllerAdvice(exceptionTranslator)
				.setMessageConverters(jacksonMessageConverter).build();

	}

	@Test
	public void sendGETRequestJSON() throws Exception {

		// The response contains a single JSON
		restRestCallsMockMvc
				.perform(get("/api/restcalls/nomapping?url=" + URL_1))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void sendGETRequestJSONArray() throws Exception {

		// The response contains a JSON array
		restRestCallsMockMvc
				.perform(get("/api/restcalls/nomapping?url=" + URL_2))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

	@Test
	public void sendGETRequestJSONs() throws Exception {

		// The response contains a JSON with attributes that are JSON objects
		// themselves
		restRestCallsMockMvc
				.perform(get("/api/restcalls/nomapping?url=" + URL_3))
				.andExpect(status().isOk())
				.andExpect(
						content().contentType(
								MediaType.APPLICATION_JSON_UTF8_VALUE));

	}

}
