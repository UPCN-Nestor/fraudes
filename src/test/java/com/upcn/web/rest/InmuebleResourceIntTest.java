package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Inmueble;
import com.upcn.repository.InmuebleRepository;
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
 * Test class for the InmuebleResource REST controller.
 *
 * @see InmuebleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class InmuebleResourceIntTest {

    private static final String DEFAULT_DIRECCION = "AAAAAAAAAA";
    private static final String UPDATED_DIRECCION = "BBBBBBBBBB";

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInmuebleMockMvc;

    private Inmueble inmueble;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InmuebleResource inmuebleResource = new InmuebleResource(inmuebleRepository);
        this.restInmuebleMockMvc = MockMvcBuilders.standaloneSetup(inmuebleResource)
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
    public static Inmueble createEntity(EntityManager em) {
        Inmueble inmueble = new Inmueble()
            .direccion(DEFAULT_DIRECCION);
        return inmueble;
    }

    @Before
    public void initTest() {
        inmueble = createEntity(em);
    }

    @Test
    @Transactional
    public void createInmueble() throws Exception {
        int databaseSizeBeforeCreate = inmuebleRepository.findAll().size();

        // Create the Inmueble
        restInmuebleMockMvc.perform(post("/api/inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inmueble)))
            .andExpect(status().isCreated());

        // Validate the Inmueble in the database
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeCreate + 1);
        Inmueble testInmueble = inmuebleList.get(inmuebleList.size() - 1);
        assertThat(testInmueble.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
    }

    @Test
    @Transactional
    public void createInmuebleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inmuebleRepository.findAll().size();

        // Create the Inmueble with an existing ID
        inmueble.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInmuebleMockMvc.perform(post("/api/inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inmueble)))
            .andExpect(status().isBadRequest());

        // Validate the Inmueble in the database
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInmuebles() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList
        restInmuebleMockMvc.perform(get("/api/inmuebles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inmueble.getId().intValue())))
            .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())));
    }

    @Test
    @Transactional
    public void getInmueble() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get the inmueble
        restInmuebleMockMvc.perform(get("/api/inmuebles/{id}", inmueble.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inmueble.getId().intValue()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInmueble() throws Exception {
        // Get the inmueble
        restInmuebleMockMvc.perform(get("/api/inmuebles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInmueble() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);
        int databaseSizeBeforeUpdate = inmuebleRepository.findAll().size();

        // Update the inmueble
        Inmueble updatedInmueble = inmuebleRepository.findOne(inmueble.getId());
        // Disconnect from session so that the updates on updatedInmueble are not directly saved in db
        em.detach(updatedInmueble);
        updatedInmueble
            .direccion(UPDATED_DIRECCION);

        restInmuebleMockMvc.perform(put("/api/inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInmueble)))
            .andExpect(status().isOk());

        // Validate the Inmueble in the database
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeUpdate);
        Inmueble testInmueble = inmuebleList.get(inmuebleList.size() - 1);
        assertThat(testInmueble.getDireccion()).isEqualTo(UPDATED_DIRECCION);
    }

    @Test
    @Transactional
    public void updateNonExistingInmueble() throws Exception {
        int databaseSizeBeforeUpdate = inmuebleRepository.findAll().size();

        // Create the Inmueble

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInmuebleMockMvc.perform(put("/api/inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inmueble)))
            .andExpect(status().isCreated());

        // Validate the Inmueble in the database
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInmueble() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);
        int databaseSizeBeforeDelete = inmuebleRepository.findAll().size();

        // Get the inmueble
        restInmuebleMockMvc.perform(delete("/api/inmuebles/{id}", inmueble.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inmueble.class);
        Inmueble inmueble1 = new Inmueble();
        inmueble1.setId(1L);
        Inmueble inmueble2 = new Inmueble();
        inmueble2.setId(inmueble1.getId());
        assertThat(inmueble1).isEqualTo(inmueble2);
        inmueble2.setId(2L);
        assertThat(inmueble1).isNotEqualTo(inmueble2);
        inmueble1.setId(null);
        assertThat(inmueble1).isNotEqualTo(inmueble2);
    }
}
