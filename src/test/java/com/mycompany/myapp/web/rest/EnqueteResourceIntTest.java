package com.mycompany.myapp.web.rest;

import com.mycompany.myapp.TaaGliProjectApp;

import com.mycompany.myapp.domain.Enquete;
import com.mycompany.myapp.repository.EnqueteRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the EnqueteResource REST controller.
 *
 * @see EnqueteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TaaGliProjectApp.class)
public class EnqueteResourceIntTest {

    private static final String DEFAULT_NOM_SOCIETE = "AAAAA";
    private static final String UPDATED_NOM_SOCIETE = "BBBBB";

    private static final String DEFAULT_PHONE = "AAAAA";
    private static final String UPDATED_PHONE = "BBBBB";

    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";

    private static final Boolean DEFAULT_STAY = false;
    private static final Boolean UPDATED_STAY = true;

    private static final Double DEFAULT_INTERNSHIP_SALARY = 1D;
    private static final Double UPDATED_INTERNSHIP_SALARY = 2D;

    private static final Double DEFAULT_SALARY = 1D;
    private static final Double UPDATED_SALARY = 2D;

    private static final String DEFAULT_COMMENT = "AAAAA";
    private static final String UPDATED_COMMENT = "BBBBB";

    @Inject
    private EnqueteRepository enqueteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Inject
    private EntityManager em;

    private MockMvc restEnqueteMockMvc;

    private Enquete enquete;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        EnqueteResource enqueteResource = new EnqueteResource();
        ReflectionTestUtils.setField(enqueteResource, "enqueteRepository", enqueteRepository);
        this.restEnqueteMockMvc = MockMvcBuilders.standaloneSetup(enqueteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Enquete createEntity(EntityManager em) {
        Enquete enquete = new Enquete()
                .nomSociete(DEFAULT_NOM_SOCIETE)
                .phone(DEFAULT_PHONE)
                .email(DEFAULT_EMAIL)
                .stay(DEFAULT_STAY)
                .internshipSalary(DEFAULT_INTERNSHIP_SALARY)
                .salary(DEFAULT_SALARY)
                .comment(DEFAULT_COMMENT);
        return enquete;
    }

    @Before
    public void initTest() {
        enquete = createEntity(em);
    }

    @Test
    @Transactional
    public void createEnquete() throws Exception {
        int databaseSizeBeforeCreate = enqueteRepository.findAll().size();

        // Create the Enquete

        restEnqueteMockMvc.perform(post("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(enquete)))
                .andExpect(status().isCreated());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeCreate + 1);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getNomSociete()).isEqualTo(DEFAULT_NOM_SOCIETE);
        assertThat(testEnquete.getPhone()).isEqualTo(DEFAULT_PHONE);
        assertThat(testEnquete.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testEnquete.isStay()).isEqualTo(DEFAULT_STAY);
        assertThat(testEnquete.getInternshipSalary()).isEqualTo(DEFAULT_INTERNSHIP_SALARY);
        assertThat(testEnquete.getSalary()).isEqualTo(DEFAULT_SALARY);
        assertThat(testEnquete.getComment()).isEqualTo(DEFAULT_COMMENT);
    }

    @Test
    @Transactional
    public void getAllEnquetes() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get all the enquetes
        restEnqueteMockMvc.perform(get("/api/enquetes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.[*].id").value(hasItem(enquete.getId().intValue())))
                .andExpect(jsonPath("$.[*].nomSociete").value(hasItem(DEFAULT_NOM_SOCIETE.toString())))
                .andExpect(jsonPath("$.[*].phone").value(hasItem(DEFAULT_PHONE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].stay").value(hasItem(DEFAULT_STAY.booleanValue())))
                .andExpect(jsonPath("$.[*].internshipSalary").value(hasItem(DEFAULT_INTERNSHIP_SALARY.doubleValue())))
                .andExpect(jsonPath("$.[*].salary").value(hasItem(DEFAULT_SALARY.doubleValue())))
                .andExpect(jsonPath("$.[*].comment").value(hasItem(DEFAULT_COMMENT.toString())));
    }

    @Test
    @Transactional
    public void getEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);

        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", enquete.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(enquete.getId().intValue()))
            .andExpect(jsonPath("$.nomSociete").value(DEFAULT_NOM_SOCIETE.toString()))
            .andExpect(jsonPath("$.phone").value(DEFAULT_PHONE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.stay").value(DEFAULT_STAY.booleanValue()))
            .andExpect(jsonPath("$.internshipSalary").value(DEFAULT_INTERNSHIP_SALARY.doubleValue()))
            .andExpect(jsonPath("$.salary").value(DEFAULT_SALARY.doubleValue()))
            .andExpect(jsonPath("$.comment").value(DEFAULT_COMMENT.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingEnquete() throws Exception {
        // Get the enquete
        restEnqueteMockMvc.perform(get("/api/enquetes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeUpdate = enqueteRepository.findAll().size();

        // Update the enquete
        Enquete updatedEnquete = enqueteRepository.findOne(enquete.getId());
        updatedEnquete
                .nomSociete(UPDATED_NOM_SOCIETE)
                .phone(UPDATED_PHONE)
                .email(UPDATED_EMAIL)
                .stay(UPDATED_STAY)
                .internshipSalary(UPDATED_INTERNSHIP_SALARY)
                .salary(UPDATED_SALARY)
                .comment(UPDATED_COMMENT);

        restEnqueteMockMvc.perform(put("/api/enquetes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedEnquete)))
                .andExpect(status().isOk());

        // Validate the Enquete in the database
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeUpdate);
        Enquete testEnquete = enquetes.get(enquetes.size() - 1);
        assertThat(testEnquete.getNomSociete()).isEqualTo(UPDATED_NOM_SOCIETE);
        assertThat(testEnquete.getPhone()).isEqualTo(UPDATED_PHONE);
        assertThat(testEnquete.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testEnquete.isStay()).isEqualTo(UPDATED_STAY);
        assertThat(testEnquete.getInternshipSalary()).isEqualTo(UPDATED_INTERNSHIP_SALARY);
        assertThat(testEnquete.getSalary()).isEqualTo(UPDATED_SALARY);
        assertThat(testEnquete.getComment()).isEqualTo(UPDATED_COMMENT);
    }

    @Test
    @Transactional
    public void deleteEnquete() throws Exception {
        // Initialize the database
        enqueteRepository.saveAndFlush(enquete);
        int databaseSizeBeforeDelete = enqueteRepository.findAll().size();

        // Get the enquete
        restEnqueteMockMvc.perform(delete("/api/enquetes/{id}", enquete.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Enquete> enquetes = enqueteRepository.findAll();
        assertThat(enquetes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
