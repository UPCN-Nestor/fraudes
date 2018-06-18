package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Etapa;
import com.upcn.repository.EtapaRepository;
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
        final EtapaResource etapaResource = new EtapaResource(etapaRepository);
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
    public void getNonExistingEtapa() throws Exception {
        // Get the etapa
        restEtapaMockMvc.perform(get("/api/etapas/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEtapa() throws Exception {
        // Initialize the database
        etapaRepository.saveAndFlush(etapa);
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
        etapaRepository.saveAndFlush(etapa);
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
