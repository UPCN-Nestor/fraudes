package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Medidor;
import com.upcn.repository.MedidorRepository;
import com.upcn.web.rest.errors.ExceptionTranslator;

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
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static com.upcn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the MedidorResource REST controller.
 *
 * @see MedidorResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class MedidorResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    @Autowired
    private MedidorRepository medidorRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMedidorMockMvc;

    private Medidor medidor;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MedidorResource medidorResource = new MedidorResource(medidorRepository);
        this.restMedidorMockMvc = MockMvcBuilders.standaloneSetup(medidorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medidor createEntity(EntityManager em) {
        Medidor medidor = new Medidor()
            .numero(DEFAULT_NUMERO);
        return medidor;
    }

    @Before
    public void initTest() {
        medidor = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedidor() throws Exception {
        int databaseSizeBeforeCreate = medidorRepository.findAll().size();

        // Create the Medidor
        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidor)))
            .andExpect(status().isCreated());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeCreate + 1);
        Medidor testMedidor = medidorList.get(medidorList.size() - 1);
        assertThat(testMedidor.getNumero()).isEqualTo(DEFAULT_NUMERO);
    }

    @Test
    @Transactional
    public void createMedidorWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medidorRepository.findAll().size();

        // Create the Medidor with an existing ID
        medidor.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedidorMockMvc.perform(post("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidor)))
            .andExpect(status().isBadRequest());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedidors() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);

        // Get all the medidorList
        restMedidorMockMvc.perform(get("/api/medidors?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medidor.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())));
    }

    @Test
    @Transactional
    public void getMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);

        // Get the medidor
        restMedidorMockMvc.perform(get("/api/medidors/{id}", medidor.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(medidor.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMedidor() throws Exception {
        // Get the medidor
        restMedidorMockMvc.perform(get("/api/medidors/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);
        int databaseSizeBeforeUpdate = medidorRepository.findAll().size();

        // Update the medidor
        Medidor updatedMedidor = medidorRepository.findOne(medidor.getId());
        // Disconnect from session so that the updates on updatedMedidor are not directly saved in db
        em.detach(updatedMedidor);
        updatedMedidor
            .numero(UPDATED_NUMERO);

        restMedidorMockMvc.perform(put("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedidor)))
            .andExpect(status().isOk());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeUpdate);
        Medidor testMedidor = medidorList.get(medidorList.size() - 1);
        assertThat(testMedidor.getNumero()).isEqualTo(UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void updateNonExistingMedidor() throws Exception {
        int databaseSizeBeforeUpdate = medidorRepository.findAll().size();

        // Create the Medidor

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMedidorMockMvc.perform(put("/api/medidors")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(medidor)))
            .andExpect(status().isCreated());

        // Validate the Medidor in the database
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedidor() throws Exception {
        // Initialize the database
        medidorRepository.saveAndFlush(medidor);
        int databaseSizeBeforeDelete = medidorRepository.findAll().size();

        // Get the medidor
        restMedidorMockMvc.perform(delete("/api/medidors/{id}", medidor.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Medidor> medidorList = medidorRepository.findAll();
        assertThat(medidorList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Medidor.class);
        Medidor medidor1 = new Medidor();
        medidor1.setId(1L);
        Medidor medidor2 = new Medidor();
        medidor2.setId(medidor1.getId());
        assertThat(medidor1).isEqualTo(medidor2);
        medidor2.setId(2L);
        assertThat(medidor1).isNotEqualTo(medidor2);
        medidor1.setId(null);
        assertThat(medidor1).isNotEqualTo(medidor2);
    }
}
