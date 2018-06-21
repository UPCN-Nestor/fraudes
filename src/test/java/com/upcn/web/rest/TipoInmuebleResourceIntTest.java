package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.TipoInmueble;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.TipoInmuebleRepository;
import com.upcn.service.TipoInmuebleService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.TipoInmuebleCriteria;
import com.upcn.service.TipoInmuebleQueryService;

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
 * Test class for the TipoInmuebleResource REST controller.
 *
 * @see TipoInmuebleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class TipoInmuebleResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private TipoInmuebleRepository tipoInmuebleRepository;

    @Autowired
    private TipoInmuebleService tipoInmuebleService;

    @Autowired
    private TipoInmuebleQueryService tipoInmuebleQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTipoInmuebleMockMvc;

    private TipoInmueble tipoInmueble;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TipoInmuebleResource tipoInmuebleResource = new TipoInmuebleResource(tipoInmuebleService, tipoInmuebleQueryService);
        this.restTipoInmuebleMockMvc = MockMvcBuilders.standaloneSetup(tipoInmuebleResource)
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
    public static TipoInmueble createEntity(EntityManager em) {
        TipoInmueble tipoInmueble = new TipoInmueble()
            .descripcion(DEFAULT_DESCRIPCION);
        return tipoInmueble;
    }

    @Before
    public void initTest() {
        tipoInmueble = createEntity(em);
    }

    @Test
    @Transactional
    public void createTipoInmueble() throws Exception {
        int databaseSizeBeforeCreate = tipoInmuebleRepository.findAll().size();

        // Create the TipoInmueble
        restTipoInmuebleMockMvc.perform(post("/api/tipo-inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInmueble)))
            .andExpect(status().isCreated());

        // Validate the TipoInmueble in the database
        List<TipoInmueble> tipoInmuebleList = tipoInmuebleRepository.findAll();
        assertThat(tipoInmuebleList).hasSize(databaseSizeBeforeCreate + 1);
        TipoInmueble testTipoInmueble = tipoInmuebleList.get(tipoInmuebleList.size() - 1);
        assertThat(testTipoInmueble.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createTipoInmuebleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tipoInmuebleRepository.findAll().size();

        // Create the TipoInmueble with an existing ID
        tipoInmueble.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTipoInmuebleMockMvc.perform(post("/api/tipo-inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInmueble)))
            .andExpect(status().isBadRequest());

        // Validate the TipoInmueble in the database
        List<TipoInmueble> tipoInmuebleList = tipoInmuebleRepository.findAll();
        assertThat(tipoInmuebleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTipoInmuebles() throws Exception {
        // Initialize the database
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);

        // Get all the tipoInmuebleList
        restTipoInmuebleMockMvc.perform(get("/api/tipo-inmuebles?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoInmueble.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getTipoInmueble() throws Exception {
        // Initialize the database
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);

        // Get the tipoInmueble
        restTipoInmuebleMockMvc.perform(get("/api/tipo-inmuebles/{id}", tipoInmueble.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tipoInmueble.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getAllTipoInmueblesByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);

        // Get all the tipoInmuebleList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTipoInmuebleShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the tipoInmuebleList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoInmuebleShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoInmueblesByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);

        // Get all the tipoInmuebleList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTipoInmuebleShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the tipoInmuebleList where descripcion equals to UPDATED_DESCRIPCION
        defaultTipoInmuebleShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTipoInmueblesByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);

        // Get all the tipoInmuebleList where descripcion is not null
        defaultTipoInmuebleShouldBeFound("descripcion.specified=true");

        // Get all the tipoInmuebleList where descripcion is null
        defaultTipoInmuebleShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTipoInmueblesByInspeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        Inspeccion inspeccion = InspeccionResourceIntTest.createEntity(em);
        em.persist(inspeccion);
        em.flush();
        tipoInmueble.addInspeccion(inspeccion);
        tipoInmuebleRepository.saveAndFlush(tipoInmueble);
        Long inspeccionId = inspeccion.getId();

        // Get all the tipoInmuebleList where inspeccion equals to inspeccionId
        defaultTipoInmuebleShouldBeFound("inspeccionId.equals=" + inspeccionId);

        // Get all the tipoInmuebleList where inspeccion equals to inspeccionId + 1
        defaultTipoInmuebleShouldNotBeFound("inspeccionId.equals=" + (inspeccionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTipoInmuebleShouldBeFound(String filter) throws Exception {
        restTipoInmuebleMockMvc.perform(get("/api/tipo-inmuebles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tipoInmueble.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTipoInmuebleShouldNotBeFound(String filter) throws Exception {
        restTipoInmuebleMockMvc.perform(get("/api/tipo-inmuebles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTipoInmueble() throws Exception {
        // Get the tipoInmueble
        restTipoInmuebleMockMvc.perform(get("/api/tipo-inmuebles/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTipoInmueble() throws Exception {
        // Initialize the database
        tipoInmuebleService.save(tipoInmueble);

        int databaseSizeBeforeUpdate = tipoInmuebleRepository.findAll().size();

        // Update the tipoInmueble
        TipoInmueble updatedTipoInmueble = tipoInmuebleRepository.findOne(tipoInmueble.getId());
        // Disconnect from session so that the updates on updatedTipoInmueble are not directly saved in db
        em.detach(updatedTipoInmueble);
        updatedTipoInmueble
            .descripcion(UPDATED_DESCRIPCION);

        restTipoInmuebleMockMvc.perform(put("/api/tipo-inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTipoInmueble)))
            .andExpect(status().isOk());

        // Validate the TipoInmueble in the database
        List<TipoInmueble> tipoInmuebleList = tipoInmuebleRepository.findAll();
        assertThat(tipoInmuebleList).hasSize(databaseSizeBeforeUpdate);
        TipoInmueble testTipoInmueble = tipoInmuebleList.get(tipoInmuebleList.size() - 1);
        assertThat(testTipoInmueble.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingTipoInmueble() throws Exception {
        int databaseSizeBeforeUpdate = tipoInmuebleRepository.findAll().size();

        // Create the TipoInmueble

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTipoInmuebleMockMvc.perform(put("/api/tipo-inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tipoInmueble)))
            .andExpect(status().isCreated());

        // Validate the TipoInmueble in the database
        List<TipoInmueble> tipoInmuebleList = tipoInmuebleRepository.findAll();
        assertThat(tipoInmuebleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTipoInmueble() throws Exception {
        // Initialize the database
        tipoInmuebleService.save(tipoInmueble);

        int databaseSizeBeforeDelete = tipoInmuebleRepository.findAll().size();

        // Get the tipoInmueble
        restTipoInmuebleMockMvc.perform(delete("/api/tipo-inmuebles/{id}", tipoInmueble.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TipoInmueble> tipoInmuebleList = tipoInmuebleRepository.findAll();
        assertThat(tipoInmuebleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TipoInmueble.class);
        TipoInmueble tipoInmueble1 = new TipoInmueble();
        tipoInmueble1.setId(1L);
        TipoInmueble tipoInmueble2 = new TipoInmueble();
        tipoInmueble2.setId(tipoInmueble1.getId());
        assertThat(tipoInmueble1).isEqualTo(tipoInmueble2);
        tipoInmueble2.setId(2L);
        assertThat(tipoInmueble1).isNotEqualTo(tipoInmueble2);
        tipoInmueble1.setId(null);
        assertThat(tipoInmueble1).isNotEqualTo(tipoInmueble2);
    }
}
