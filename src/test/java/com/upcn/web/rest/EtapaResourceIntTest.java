package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Etapa;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.EtapaRepository;
import com.upcn.service.EtapaService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.EtapaCriteria;
import com.upcn.service.EtapaQueryService;

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
 * Test class for the EtapaResource REST controller.
 *
 * @see EtapaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class EtapaResourceIntTest {

    private static final Long DEFAULT_NUMERO = 1L;
    private static final Long UPDATED_NUMERO = 2L;

    private static final String DEFAULT_DESCRIPCION_CORTA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_CORTA = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPCION_LARGA = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION_LARGA = "BBBBBBBBBB";

    @Autowired
    private EtapaRepository etapaRepository;

    @Autowired
    private EtapaService etapaService;

    @Autowired
    private EtapaQueryService etapaQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restEtapaMockMvc;

    private Etapa etapa;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EtapaResource etapaResource = new EtapaResource(etapaService, etapaQueryService);
        this.restEtapaMockMvc = MockMvcBuilders.standaloneSetup(etapaResource)
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
    public static Etapa createEntity(EntityManager em) {
        Etapa etapa = new Etapa()
            .numero(DEFAULT_NUMERO)
            .descripcionCorta(DEFAULT_DESCRIPCION_CORTA)
            .descripcionLarga(DEFAULT_DESCRIPCION_LARGA);
        return etapa;
    }

    @Before
    public void initTest() {
        etapa = createEntity(em);
    }

    @Test
    @Transactional
    public void createEtapa() throws Exception {
        int databaseSizeBeforeCreate = etapaRepository.findAll().size();

        // Create the Etapa
        restEtapaMockMvc.perform(post("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa)))
            .andExpect(status().isCreated());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeCreate + 1);
        Etapa testEtapa = etapaList.get(etapaList.size() - 1);
        assertThat(testEtapa.getNumero()).isEqualTo(DEFAULT_NUMERO);
        assertThat(testEtapa.getDescripcionCorta()).isEqualTo(DEFAULT_DESCRIPCION_CORTA);
        assertThat(testEtapa.getDescripcionLarga()).isEqualTo(DEFAULT_DESCRIPCION_LARGA);
    }

    @Test
    @Transactional
    public void createEtapaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = etapaRepository.findAll().size();

        // Create the Etapa with an existing ID
        etapa.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEtapaMockMvc.perform(post("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa)))
            .andExpect(status().isBadRequest());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllEtapas() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList
        restEtapaMockMvc.perform(get("/api/etapas?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapa.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].descripcionCorta").value(hasItem(DEFAULT_DESCRIPCION_CORTA.toString())))
            .andExpect(jsonPath("$.[*].descripcionLarga").value(hasItem(DEFAULT_DESCRIPCION_LARGA.toString())));
    }

    @Test
    @Transactional
    public void getEtapa() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get the etapa
        restEtapaMockMvc.perform(get("/api/etapas/{id}", etapa.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(etapa.getId().intValue()))
            .andExpect(jsonPath("$.numero").value(DEFAULT_NUMERO.intValue()))
            .andExpect(jsonPath("$.descripcionCorta").value(DEFAULT_DESCRIPCION_CORTA.toString()))
            .andExpect(jsonPath("$.descripcionLarga").value(DEFAULT_DESCRIPCION_LARGA.toString()));
    }

    @Test
    @Transactional
    public void getAllEtapasByNumeroIsEqualToSomething() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where numero equals to DEFAULT_NUMERO
        defaultEtapaShouldBeFound("numero.equals=" + DEFAULT_NUMERO);

        // Get all the etapaList where numero equals to UPDATED_NUMERO
        defaultEtapaShouldNotBeFound("numero.equals=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEtapasByNumeroIsInShouldWork() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where numero in DEFAULT_NUMERO or UPDATED_NUMERO
        defaultEtapaShouldBeFound("numero.in=" + DEFAULT_NUMERO + "," + UPDATED_NUMERO);

        // Get all the etapaList where numero equals to UPDATED_NUMERO
        defaultEtapaShouldNotBeFound("numero.in=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEtapasByNumeroIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where numero is not null
        defaultEtapaShouldBeFound("numero.specified=true");

        // Get all the etapaList where numero is null
        defaultEtapaShouldNotBeFound("numero.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtapasByNumeroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where numero greater than or equals to DEFAULT_NUMERO
        defaultEtapaShouldBeFound("numero.greaterOrEqualThan=" + DEFAULT_NUMERO);

        // Get all the etapaList where numero greater than or equals to UPDATED_NUMERO
        defaultEtapaShouldNotBeFound("numero.greaterOrEqualThan=" + UPDATED_NUMERO);
    }

    @Test
    @Transactional
    public void getAllEtapasByNumeroIsLessThanSomething() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where numero less than or equals to DEFAULT_NUMERO
        defaultEtapaShouldNotBeFound("numero.lessThan=" + DEFAULT_NUMERO);

        // Get all the etapaList where numero less than or equals to UPDATED_NUMERO
        defaultEtapaShouldBeFound("numero.lessThan=" + UPDATED_NUMERO);
    }


    @Test
    @Transactional
    public void getAllEtapasByDescripcionCortaIsEqualToSomething() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionCorta equals to DEFAULT_DESCRIPCION_CORTA
        defaultEtapaShouldBeFound("descripcionCorta.equals=" + DEFAULT_DESCRIPCION_CORTA);

        // Get all the etapaList where descripcionCorta equals to UPDATED_DESCRIPCION_CORTA
        defaultEtapaShouldNotBeFound("descripcionCorta.equals=" + UPDATED_DESCRIPCION_CORTA);
    }

    @Test
    @Transactional
    public void getAllEtapasByDescripcionCortaIsInShouldWork() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionCorta in DEFAULT_DESCRIPCION_CORTA or UPDATED_DESCRIPCION_CORTA
        defaultEtapaShouldBeFound("descripcionCorta.in=" + DEFAULT_DESCRIPCION_CORTA + "," + UPDATED_DESCRIPCION_CORTA);

        // Get all the etapaList where descripcionCorta equals to UPDATED_DESCRIPCION_CORTA
        defaultEtapaShouldNotBeFound("descripcionCorta.in=" + UPDATED_DESCRIPCION_CORTA);
    }

    @Test
    @Transactional
    public void getAllEtapasByDescripcionCortaIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionCorta is not null
        defaultEtapaShouldBeFound("descripcionCorta.specified=true");

        // Get all the etapaList where descripcionCorta is null
        defaultEtapaShouldNotBeFound("descripcionCorta.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtapasByDescripcionLargaIsEqualToSomething() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionLarga equals to DEFAULT_DESCRIPCION_LARGA
        defaultEtapaShouldBeFound("descripcionLarga.equals=" + DEFAULT_DESCRIPCION_LARGA);

        // Get all the etapaList where descripcionLarga equals to UPDATED_DESCRIPCION_LARGA
        defaultEtapaShouldNotBeFound("descripcionLarga.equals=" + UPDATED_DESCRIPCION_LARGA);
    }

    @Test
    @Transactional
    public void getAllEtapasByDescripcionLargaIsInShouldWork() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionLarga in DEFAULT_DESCRIPCION_LARGA or UPDATED_DESCRIPCION_LARGA
        defaultEtapaShouldBeFound("descripcionLarga.in=" + DEFAULT_DESCRIPCION_LARGA + "," + UPDATED_DESCRIPCION_LARGA);

        // Get all the etapaList where descripcionLarga equals to UPDATED_DESCRIPCION_LARGA
        defaultEtapaShouldNotBeFound("descripcionLarga.in=" + UPDATED_DESCRIPCION_LARGA);
    }

    @Test
    @Transactional
    public void getAllEtapasByDescripcionLargaIsNullOrNotNull() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);

        // Get all the etapaList where descripcionLarga is not null
        defaultEtapaShouldBeFound("descripcionLarga.specified=true");

        // Get all the etapaList where descripcionLarga is null
        defaultEtapaShouldNotBeFound("descripcionLarga.specified=false");
    }

    @Test
    @Transactional
    public void getAllEtapasByInspeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        Inspeccion inspeccion = InspeccionResourceIntTest.createEntity(em);
        em.persist(inspeccion);
        em.flush();
        etapa.addInspeccion(inspeccion);
        etapaRepository.saveAndFlush(etapa);
        Long inspeccionId = inspeccion.getId();

        // Get all the etapaList where inspeccion equals to inspeccionId
        defaultEtapaShouldBeFound("inspeccionId.equals=" + inspeccionId);

        // Get all the etapaList where inspeccion equals to inspeccionId + 1
        defaultEtapaShouldNotBeFound("inspeccionId.equals=" + (inspeccionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultEtapaShouldBeFound(String filter) throws Exception {
        restEtapaMockMvc.perform(get("/api/etapas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(etapa.getId().intValue())))
            .andExpect(jsonPath("$.[*].numero").value(hasItem(DEFAULT_NUMERO.intValue())))
            .andExpect(jsonPath("$.[*].descripcionCorta").value(hasItem(DEFAULT_DESCRIPCION_CORTA.toString())))
            .andExpect(jsonPath("$.[*].descripcionLarga").value(hasItem(DEFAULT_DESCRIPCION_LARGA.toString())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultEtapaShouldNotBeFound(String filter) throws Exception {
        restEtapaMockMvc.perform(get("/api/etapas?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingEtapa() throws Exception {
        // Get the etapa
        restEtapaMockMvc.perform(get("/api/etapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapa() throws Exception {
        // Initialize the database
        etapaService.save(etapa);

        int databaseSizeBeforeUpdate = etapaRepository.findAll().size();

        // Update the etapa
        Etapa updatedEtapa = etapaRepository.findOne(etapa.getId());
        // Disconnect from session so that the updates on updatedEtapa are not directly saved in db
        em.detach(updatedEtapa);
        updatedEtapa
            .numero(UPDATED_NUMERO)
            .descripcionCorta(UPDATED_DESCRIPCION_CORTA)
            .descripcionLarga(UPDATED_DESCRIPCION_LARGA);

        restEtapaMockMvc.perform(put("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedEtapa)))
            .andExpect(status().isOk());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeUpdate);
        Etapa testEtapa = etapaList.get(etapaList.size() - 1);
        assertThat(testEtapa.getNumero()).isEqualTo(UPDATED_NUMERO);
        assertThat(testEtapa.getDescripcionCorta()).isEqualTo(UPDATED_DESCRIPCION_CORTA);
        assertThat(testEtapa.getDescripcionLarga()).isEqualTo(UPDATED_DESCRIPCION_LARGA);
    }

    @Test
    @Transactional
    public void updateNonExistingEtapa() throws Exception {
        int databaseSizeBeforeUpdate = etapaRepository.findAll().size();

        // Create the Etapa

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restEtapaMockMvc.perform(put("/api/etapas")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(etapa)))
            .andExpect(status().isCreated());

        // Validate the Etapa in the database
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteEtapa() throws Exception {
        // Initialize the database
        etapaService.save(etapa);

        int databaseSizeBeforeDelete = etapaRepository.findAll().size();

        // Get the etapa
        restEtapaMockMvc.perform(delete("/api/etapas/{id}", etapa.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Etapa> etapaList = etapaRepository.findAll();
        assertThat(etapaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Etapa.class);
        Etapa etapa1 = new Etapa();
        etapa1.setId(1L);
        Etapa etapa2 = new Etapa();
        etapa2.setId(etapa1.getId());
        assertThat(etapa1).isEqualTo(etapa2);
        etapa2.setId(2L);
        assertThat(etapa1).isNotEqualTo(etapa2);
        etapa1.setId(null);
        assertThat(etapa1).isNotEqualTo(etapa2);
    }
}
