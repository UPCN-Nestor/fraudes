package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Anomalia;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.AnomaliaRepository;
import com.upcn.service.AnomaliaService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.AnomaliaCriteria;
import com.upcn.service.AnomaliaQueryService;

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
 * Test class for the AnomaliaResource REST controller.
 *
 * @see AnomaliaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class AnomaliaResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    @Autowired
    private AnomaliaRepository anomaliaRepository;

    @Autowired
    private AnomaliaService anomaliaService;

    @Autowired
    private AnomaliaQueryService anomaliaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAnomaliaMockMvc;

    private Anomalia anomalia;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AnomaliaResource anomaliaResource = new AnomaliaResource(anomaliaService, anomaliaQueryService);
        this.restAnomaliaMockMvc = MockMvcBuilders.standaloneSetup(anomaliaResource)
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
    public static Anomalia createEntity(EntityManager em) {
        Anomalia anomalia = new Anomalia()
            .descripcion(DEFAULT_DESCRIPCION);
        return anomalia;
    }

    @Before
    public void initTest() {
        anomalia = createEntity(em);
    }

    @Test
    @Transactional
    public void createAnomalia() throws Exception {
        int databaseSizeBeforeCreate = anomaliaRepository.findAll().size();

        // Create the Anomalia
        restAnomaliaMockMvc.perform(post("/api/anomalias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anomalia)))
            .andExpect(status().isCreated());

        // Validate the Anomalia in the database
        List<Anomalia> anomaliaList = anomaliaRepository.findAll();
        assertThat(anomaliaList).hasSize(databaseSizeBeforeCreate + 1);
        Anomalia testAnomalia = anomaliaList.get(anomaliaList.size() - 1);
        assertThat(testAnomalia.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
    }

    @Test
    @Transactional
    public void createAnomaliaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = anomaliaRepository.findAll().size();

        // Create the Anomalia with an existing ID
        anomalia.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAnomaliaMockMvc.perform(post("/api/anomalias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anomalia)))
            .andExpect(status().isBadRequest());

        // Validate the Anomalia in the database
        List<Anomalia> anomaliaList = anomaliaRepository.findAll();
        assertThat(anomaliaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAnomalias() throws Exception {
        // Initialize the database
        anomaliaRepository.saveAndFlush(anomalia);

        // Get all the anomaliaList
        restAnomaliaMockMvc.perform(get("/api/anomalias?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomalia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    @Test
    @Transactional
    public void getAnomalia() throws Exception {
        // Initialize the database
        anomaliaRepository.saveAndFlush(anomalia);

        // Get the anomalia
        restAnomaliaMockMvc.perform(get("/api/anomalias/{id}", anomalia.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(anomalia.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()));
    }

    @Test
    @Transactional
    public void getAllAnomaliasByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        anomaliaRepository.saveAndFlush(anomalia);

        // Get all the anomaliaList where descripcion equals to DEFAULT_DESCRIPCION
        defaultAnomaliaShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the anomaliaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAnomaliaShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAnomaliasByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        anomaliaRepository.saveAndFlush(anomalia);

        // Get all the anomaliaList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultAnomaliaShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the anomaliaList where descripcion equals to UPDATED_DESCRIPCION
        defaultAnomaliaShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllAnomaliasByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        anomaliaRepository.saveAndFlush(anomalia);

        // Get all the anomaliaList where descripcion is not null
        defaultAnomaliaShouldBeFound("descripcion.specified=true");

        // Get all the anomaliaList where descripcion is null
        defaultAnomaliaShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllAnomaliasByInspeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        Inspeccion inspeccion = InspeccionResourceIntTest.createEntity(em);
        em.persist(inspeccion);
        em.flush();
        anomalia.addInspeccion(inspeccion);
        anomaliaRepository.saveAndFlush(anomalia);
        Long inspeccionId = inspeccion.getId();

        // Get all the anomaliaList where inspeccion equals to inspeccionId
        defaultAnomaliaShouldBeFound("inspeccionId.equals=" + inspeccionId);

        // Get all the anomaliaList where inspeccion equals to inspeccionId + 1
        defaultAnomaliaShouldNotBeFound("inspeccionId.equals=" + (inspeccionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultAnomaliaShouldBeFound(String filter) throws Exception {
        restAnomaliaMockMvc.perform(get("/api/anomalias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(anomalia.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultAnomaliaShouldNotBeFound(String filter) throws Exception {
        restAnomaliaMockMvc.perform(get("/api/anomalias?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingAnomalia() throws Exception {
        // Get the anomalia
        restAnomaliaMockMvc.perform(get("/api/anomalias/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAnomalia() throws Exception {
        // Initialize the database
        anomaliaService.save(anomalia);

        int databaseSizeBeforeUpdate = anomaliaRepository.findAll().size();

        // Update the anomalia
        Anomalia updatedAnomalia = anomaliaRepository.findOne(anomalia.getId());
        // Disconnect from session so that the updates on updatedAnomalia are not directly saved in db
        em.detach(updatedAnomalia);
        updatedAnomalia
            .descripcion(UPDATED_DESCRIPCION);

        restAnomaliaMockMvc.perform(put("/api/anomalias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedAnomalia)))
            .andExpect(status().isOk());

        // Validate the Anomalia in the database
        List<Anomalia> anomaliaList = anomaliaRepository.findAll();
        assertThat(anomaliaList).hasSize(databaseSizeBeforeUpdate);
        Anomalia testAnomalia = anomaliaList.get(anomaliaList.size() - 1);
        assertThat(testAnomalia.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void updateNonExistingAnomalia() throws Exception {
        int databaseSizeBeforeUpdate = anomaliaRepository.findAll().size();

        // Create the Anomalia

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAnomaliaMockMvc.perform(put("/api/anomalias")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(anomalia)))
            .andExpect(status().isCreated());

        // Validate the Anomalia in the database
        List<Anomalia> anomaliaList = anomaliaRepository.findAll();
        assertThat(anomaliaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAnomalia() throws Exception {
        // Initialize the database
        anomaliaService.save(anomalia);

        int databaseSizeBeforeDelete = anomaliaRepository.findAll().size();

        // Get the anomalia
        restAnomaliaMockMvc.perform(delete("/api/anomalias/{id}", anomalia.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Anomalia> anomaliaList = anomaliaRepository.findAll();
        assertThat(anomaliaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Anomalia.class);
        Anomalia anomalia1 = new Anomalia();
        anomalia1.setId(1L);
        Anomalia anomalia2 = new Anomalia();
        anomalia2.setId(anomalia1.getId());
        assertThat(anomalia1).isEqualTo(anomalia2);
        anomalia2.setId(2L);
        assertThat(anomalia1).isNotEqualTo(anomalia2);
        anomalia1.setId(null);
        assertThat(anomalia1).isNotEqualTo(anomalia2);
    }
}
