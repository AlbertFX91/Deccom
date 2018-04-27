package com.deccom.web.rest;

import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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
import com.deccom.web.rest.core.sql.SQLResource;
import com.deccom.web.rest.errors.ExceptionTranslator;

/**
 * Test class for the AcmeResource REST controller.
 *
 * @see AcmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeccomApp.class)
public class DBResourceIntTest {

    private static final String USERNAME = "developer";
    private static final String PASSWORD = "developer";
    private static final String URL = "jdbc:mysql://localhost:3306/deccom";
    private static final String QUERY = "SELECT * FROM AUTHOR";

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAcmeMockMvc;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        SQLResource dbResource = new SQLResource();
        this.restAcmeMockMvc = MockMvcBuilders.standaloneSetup(dbResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Test
    public void getAllAuthors() throws Exception {

        // Get all the data from the table AUTHOR
    	String url="/api//db/query?username="+USERNAME
    			+"&password="+PASSWORD
    			+"&url="+URL
    			+"&query="+QUERY;
    	// {debut=2016-11-23 00:00:00.0, idauthor=1, avgbooks=10.63, name=name-1, age=18}
        restAcmeMockMvc.perform(get(url))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].idauthor").value(hasItem("1")))
            .andExpect(jsonPath("$.[*].debut").value(hasItem("2016-11-23 00:00:00.0")))
            .andExpect(jsonPath("$.[*].avgbooks").value(hasItem("10.63")))
            .andExpect(jsonPath("$.[*].name").value(hasItem("name-1")))
            .andExpect(jsonPath("$.[*].age").value(hasItem("18")));
    }
    
}
