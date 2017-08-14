package com.deccom.web.rest;

import com.deccom.DeccomApp;

import com.deccom.domain.Acme;
import com.deccom.repository.AcmeRepository;
import com.deccom.service.AcmeService;
import com.deccom.web.rest.errors.ExceptionTranslator;

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

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AcmeResource REST controller.
 *
 * @see AcmeResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DeccomApp.class)
public class AcmeResourceIntTest {

    private static final String DEFAULT_TITLE = "AAAAAAAAAA";
    private static final String UPDATED_TITLE = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PUBLICATION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PUBLICATION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Integer DEFAULT_RATING = 0;
    private static final Integer UPDATED_RATING = 1;

    @Autowired
    private AcmeRepository acmeRepository;

    @Autowired
    private AcmeService acmeService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    private MockMvc restAcmeMockMvc;

    private Acme acme;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AcmeResource acmeResource = new AcmeResource(acmeService);
        this.restAcmeMockMvc = MockMvcBuilders.standaloneSetup(acmeResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Acme createEntity() {
        Acme acme = new Acme()
            .title(DEFAULT_TITLE)
            .description(DEFAULT_DESCRIPTION)
            .publication_date(DEFAULT_PUBLICATION_DATE)
            .rating(DEFAULT_RATING);
        return acme;
    }

    @Before
    public void initTest() {
        acmeRepository.deleteAll();
        acme = createEntity();
    }

    @Test
    public void createAcme() throws Exception {
        int databaseSizeBeforeCreate = acmeRepository.findAll().size();

        // Create the Acme
        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isCreated());

        // Validate the Acme in the database
        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeCreate + 1);
        Acme testAcme = acmeList.get(acmeList.size() - 1);
        assertThat(testAcme.getTitle()).isEqualTo(DEFAULT_TITLE);
        assertThat(testAcme.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAcme.getPublication_date()).isEqualTo(DEFAULT_PUBLICATION_DATE);
        assertThat(testAcme.getRating()).isEqualTo(DEFAULT_RATING);
    }

    @Test
    public void createAcmeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = acmeRepository.findAll().size();

        // Create the Acme with an existing ID
        acme.setId("existing_id");

        // An entity with an existing ID cannot be created, so this API call must fail
        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    public void checkTitleIsRequired() throws Exception {
        int databaseSizeBeforeTest = acmeRepository.findAll().size();
        // set the field null
        acme.setTitle(null);

        // Create the Acme, which fails.

        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isBadRequest());

        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = acmeRepository.findAll().size();
        // set the field null
        acme.setDescription(null);

        // Create the Acme, which fails.

        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isBadRequest());

        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkPublication_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = acmeRepository.findAll().size();
        // set the field null
        acme.setPublication_date(null);

        // Create the Acme, which fails.

        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isBadRequest());

        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void checkRatingIsRequired() throws Exception {
        int databaseSizeBeforeTest = acmeRepository.findAll().size();
        // set the field null
        acme.setRating(null);

        // Create the Acme, which fails.

        restAcmeMockMvc.perform(post("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isBadRequest());

        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    public void getAllAcmes() throws Exception {
        // Initialize the database
        acmeRepository.save(acme);

        // Get all the acmeList
        restAcmeMockMvc.perform(get("/api/acmes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(acme.getId())))
            .andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].publication_date").value(hasItem(DEFAULT_PUBLICATION_DATE.toString())))
            .andExpect(jsonPath("$.[*].rating").value(hasItem(DEFAULT_RATING)));
    }

    @Test
    public void getAcme() throws Exception {
        // Initialize the database
        acmeRepository.save(acme);

        // Get the acme
        restAcmeMockMvc.perform(get("/api/acmes/{id}", acme.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(acme.getId()))
            .andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.publication_date").value(DEFAULT_PUBLICATION_DATE.toString()))
            .andExpect(jsonPath("$.rating").value(DEFAULT_RATING));
    }

    @Test
    public void getNonExistingAcme() throws Exception {
        // Get the acme
        restAcmeMockMvc.perform(get("/api/acmes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    public void updateAcme() throws Exception {
        // Initialize the database
        acmeService.save(acme);

        int databaseSizeBeforeUpdate = acmeRepository.findAll().size();

        // Update the acme
        Acme updatedAcme = acmeRepository.findOne(acme.getId());
        updatedAcme
            .title(UPDATED_TITLE)
            .description(UPDATED_DESCRIPTION)
            .publication_date(UPDATED_PUBLICATION_DATE)
            .rating(UPDATED_RATING);

        restAcmeMockMvc.perform(put("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAcme)))
            .andExpect(status().isOk());

        // Validate the Acme in the database
        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeUpdate);
        Acme testAcme = acmeList.get(acmeList.size() - 1);
        assertThat(testAcme.getTitle()).isEqualTo(UPDATED_TITLE);
        assertThat(testAcme.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAcme.getPublication_date()).isEqualTo(UPDATED_PUBLICATION_DATE);
        assertThat(testAcme.getRating()).isEqualTo(UPDATED_RATING);
    }

    @Test
    public void updateNonExistingAcme() throws Exception {
        int databaseSizeBeforeUpdate = acmeRepository.findAll().size();

        // Create the Acme

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAcmeMockMvc.perform(put("/api/acmes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(acme)))
            .andExpect(status().isCreated());

        // Validate the Acme in the database
        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    public void deleteAcme() throws Exception {
        // Initialize the database
        acmeService.save(acme);

        int databaseSizeBeforeDelete = acmeRepository.findAll().size();

        // Get the acme
        restAcmeMockMvc.perform(delete("/api/acmes/{id}", acme.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Acme> acmeList = acmeRepository.findAll();
        assertThat(acmeList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Acme.class);
        Acme acme1 = new Acme();
        acme1.setId("id1");
        Acme acme2 = new Acme();
        acme2.setId(acme1.getId());
        assertThat(acme1).isEqualTo(acme2);
        acme2.setId("id2");
        assertThat(acme1).isNotEqualTo(acme2);
        acme1.setId(null);
        assertThat(acme1).isNotEqualTo(acme2);
    }
}
