package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Precinto;
import com.upcn.repository.PrecintoRepository;
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
 * Test class for the PrecintoResource REST controller.
 *
 * @see PrecintoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class PrecintoResourceIntTest {

    private static final String DEFAULT_NUMERO = "AAAAAAAAAA";
    private static final String UPDATED_NUMERO = "BBBBBBBBBB";

    private static final String DEFAULT_COLOR = "AAAAAAAAAA";
    private static final String UPDATED_COLOR = "BBBBBBBBBB";

    @Autowired
    private PrecintoRepository precintoRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPrecintoMockMvc;

    private Precinto precinto;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrecintoResource precintoResource = new PrecintoResource(precintoRepository);
        this.restPrecintoMockMvc = MockMvcBuilders.standaloneSetup(precintoResource)
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
    public static Precinto createEntity(EntityManager em) {
        Precinto precinto = new Precinto()
            .numero(DEFAULT_NUMERO)
            .color(DEFAULT_COLOR);
        return precinto;
    }

    @Before
    public void initTest() {
        precinto = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrecinto() throws Exception {
        int databaseSizeBeforeCreate = precintoRepository.findAll().size();

        // Create the Precinto
        restPrecintoMockMvc.perform(post("/api/precintos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precinto)))
            .andExpect(status().isCreated());

        // Validate the Precinto in the database
        List<Precinto> precintoList = precintoRepository.findAll();
        assertThat(precintoList).hasSize(databaseSizeBeforeCreate + 1);
        Precinto testPrecinto = precintoList.get(precintoList.size() - 1);
        assertThat(testPrecinto.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testPrecinto.getColor()).isEqualTo(DEFAULT_COLOR);
    }

    @Test
    @Transactional
    public void createPrecintoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = precintoRepository.findAll().size();

        // Create the Precinto with an existing ID
        precinto.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrecintoMockMvc.perform(post("/api/precintos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precinto)))
            .andExpect(status().isBadRequest());

        // Validate the Precinto in the database
        List<Precinto> precintoList = precintoRepository.findAll();
        assertThat(precintoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPrecintos() throws Exception {
        // Initialize the database
        precintoRepository.saveAndFlush(precinto);

        // Get all the precintoList
        restPrecintoMockMvc.perform(get("/api/precintos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(precinto.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.toString())))
            .andExpect(jsonPath("$.[*].color").value(hasItem(DEFAULT_COLOR.toString())));
    }

    @Test
    @Transactional
    public void getPrecinto() throws Exception {
        // Initialize the database
        precintoRepository.saveAndFlush(precinto);

        // Get the precinto
        restPrecintoMockMvc.perform(get("/api/precintos/{id}", precinto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(precinto.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.toString()))
            .andExpect(jsonPath("$.color").value(DEFAULT_COLOR.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPrecinto() throws Exception {
        // Get the precinto
        restPrecintoMockMvc.perform(get("/api/precintos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrecinto() throws Exception {
        // Initialize the database
        precintoRepository.saveAndFlush(precinto);
        int databaseSizeBeforeUpdate = precintoRepository.findAll().size();

        // Update the precinto
        Precinto updatedPrecinto = precintoRepository.findOne(precinto.getId());
        // Disconnect from session so that the updates on updatedPrecinto are not directly saved in db
        em.detach(updatedPrecinto);
        updatedPrecinto
            .numero(UPDATED_NUMERO)
            .color(UPDATED_COLOR);

        restPrecintoMockMvc.perform(put("/api/precintos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrecinto)))
            .andExpect(status().isOk());

        // Validate the Precinto in the database
        List<Precinto> precintoList = precintoRepository.findAll();
        assertThat(precintoList).hasSize(databaseSizeBeforeUpdate);
        Precinto testPrecinto = precintoList.get(precintoList.size() - 1);
        assertThat(testPrecinto.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testPrecinto.getColor()).isEqualTo(UPDATED_COLOR);
    }

    @Test
    @Transactional
    public void updateNonExistingPrecinto() throws Exception {
        int databaseSizeBeforeUpdate = precintoRepository.findAll().size();

        // Create the Precinto

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPrecintoMockMvc.perform(put("/api/precintos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(precinto)))
            .andExpect(status().isCreated());

        // Validate the Precinto in the database
        List<Precinto> precintoList = precintoRepository.findAll();
        assertThat(precintoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePrecinto() throws Exception {
        // Initialize the database
        precintoRepository.saveAndFlush(precinto);
        int databaseSizeBeforeDelete = precintoRepository.findAll().size();

        // Get the precinto
        restPrecintoMockMvc.perform(delete("/api/precintos/{id}", precinto.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Precinto> precintoList = precintoRepository.findAll();
        assertThat(precintoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Precinto.class);
        Precinto precinto1 = new Precinto();
        precinto1.setId(1L);
        Precinto precinto2 = new Precinto();
        precinto2.setId(precinto1.getId());
        assertThat(precinto1).isEqualTo(precinto2);
        precinto2.setId(2L);
        assertThat(precinto1).isNotEqualTo(precinto2);
        precinto1.setId(null);
        assertThat(precinto1).isNotEqualTo(precinto2);
    }
}
