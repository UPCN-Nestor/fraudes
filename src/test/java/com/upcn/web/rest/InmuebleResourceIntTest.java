package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Inmueble;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.InmuebleRepository;
import com.upcn.service.InmuebleService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.InmuebleCriteria;
import com.upcn.service.InmuebleQueryService;

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

    private static final String DEFAULT_CALLE = "AAAAAAAAAA";
    private static final String UPDATED_CALLE = "BBBBBBBBBB";

    private static final String DEFAULT_ALTURA = "AAAAAAAAAA";
    private static final String UPDATED_ALTURA = "BBBBBBBBBB";

    private static final String DEFAULT_PISO = "AAAAAAAAAA";
    private static final String UPDATED_PISO = "BBBBBBBBBB";

    private static final String DEFAULT_DEPTO = "AAAAAAAAAA";
    private static final String UPDATED_DEPTO = "BBBBBBBBBB";

    private static final String DEFAULT_ANEXO = "AAAAAAAAAA";
    private static final String UPDATED_ANEXO = "BBBBBBBBBB";

    private static final Integer DEFAULT_ID_GLM = 1;
    private static final Integer UPDATED_ID_GLM = 2;

    @Autowired
    private InmuebleRepository inmuebleRepository;

    @Autowired
    private InmuebleService inmuebleService;

    @Autowired
    private InmuebleQueryService inmuebleQueryService;

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
        final InmuebleResource inmuebleResource = new InmuebleResource(inmuebleService, inmuebleQueryService);
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
            .calle(DEFAULT_CALLE)
            .altura(DEFAULT_ALTURA)
            .piso(DEFAULT_PISO)
            .depto(DEFAULT_DEPTO)
            .anexo(DEFAULT_ANEXO)
            .id_glm(DEFAULT_ID_GLM);
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
        assertThat(testInmueble.getCalle()).isEqualTo(DEFAULT_CALLE);
        assertThat(testInmueble.getAltura()).isEqualTo(DEFAULT_ALTURA);
        assertThat(testInmueble.getPiso()).isEqualTo(DEFAULT_PISO);
        assertThat(testInmueble.getDepto()).isEqualTo(DEFAULT_DEPTO);
        assertThat(testInmueble.getAnexo()).isEqualTo(DEFAULT_ANEXO);
        assertThat(testInmueble.getId_glm()).isEqualTo(DEFAULT_ID_GLM);
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
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].depto").value(hasItem(DEFAULT_DEPTO.toString())))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(DEFAULT_ANEXO.toString())))
            .andExpect(jsonPath("$.[*].id_glm").value(hasItem(DEFAULT_ID_GLM)));
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
            .andExpect(jsonPath("$.calle").value(DEFAULT_CALLE.toString()))
            .andExpect(jsonPath("$.altura").value(DEFAULT_ALTURA.toString()))
            .andExpect(jsonPath("$.piso").value(DEFAULT_PISO.toString()))
            .andExpect(jsonPath("$.depto").value(DEFAULT_DEPTO.toString()))
            .andExpect(jsonPath("$.anexo").value(DEFAULT_ANEXO.toString()))
            .andExpect(jsonPath("$.id_glm").value(DEFAULT_ID_GLM));
    }

    @Test
    @Transactional
    public void getAllInmueblesByCalleIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where calle equals to DEFAULT_CALLE
        defaultInmuebleShouldBeFound("calle.equals=" + DEFAULT_CALLE);

        // Get all the inmuebleList where calle equals to UPDATED_CALLE
        defaultInmuebleShouldNotBeFound("calle.equals=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllInmueblesByCalleIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where calle in DEFAULT_CALLE or UPDATED_CALLE
        defaultInmuebleShouldBeFound("calle.in=" + DEFAULT_CALLE + "," + UPDATED_CALLE);

        // Get all the inmuebleList where calle equals to UPDATED_CALLE
        defaultInmuebleShouldNotBeFound("calle.in=" + UPDATED_CALLE);
    }

    @Test
    @Transactional
    public void getAllInmueblesByCalleIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where calle is not null
        defaultInmuebleShouldBeFound("calle.specified=true");

        // Get all the inmuebleList where calle is null
        defaultInmuebleShouldNotBeFound("calle.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesByAlturaIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where altura equals to DEFAULT_ALTURA
        defaultInmuebleShouldBeFound("altura.equals=" + DEFAULT_ALTURA);

        // Get all the inmuebleList where altura equals to UPDATED_ALTURA
        defaultInmuebleShouldNotBeFound("altura.equals=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    public void getAllInmueblesByAlturaIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where altura in DEFAULT_ALTURA or UPDATED_ALTURA
        defaultInmuebleShouldBeFound("altura.in=" + DEFAULT_ALTURA + "," + UPDATED_ALTURA);

        // Get all the inmuebleList where altura equals to UPDATED_ALTURA
        defaultInmuebleShouldNotBeFound("altura.in=" + UPDATED_ALTURA);
    }

    @Test
    @Transactional
    public void getAllInmueblesByAlturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where altura is not null
        defaultInmuebleShouldBeFound("altura.specified=true");

        // Get all the inmuebleList where altura is null
        defaultInmuebleShouldNotBeFound("altura.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesByPisoIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where piso equals to DEFAULT_PISO
        defaultInmuebleShouldBeFound("piso.equals=" + DEFAULT_PISO);

        // Get all the inmuebleList where piso equals to UPDATED_PISO
        defaultInmuebleShouldNotBeFound("piso.equals=" + UPDATED_PISO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByPisoIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where piso in DEFAULT_PISO or UPDATED_PISO
        defaultInmuebleShouldBeFound("piso.in=" + DEFAULT_PISO + "," + UPDATED_PISO);

        // Get all the inmuebleList where piso equals to UPDATED_PISO
        defaultInmuebleShouldNotBeFound("piso.in=" + UPDATED_PISO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByPisoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where piso is not null
        defaultInmuebleShouldBeFound("piso.specified=true");

        // Get all the inmuebleList where piso is null
        defaultInmuebleShouldNotBeFound("piso.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesByDeptoIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where depto equals to DEFAULT_DEPTO
        defaultInmuebleShouldBeFound("depto.equals=" + DEFAULT_DEPTO);

        // Get all the inmuebleList where depto equals to UPDATED_DEPTO
        defaultInmuebleShouldNotBeFound("depto.equals=" + UPDATED_DEPTO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByDeptoIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where depto in DEFAULT_DEPTO or UPDATED_DEPTO
        defaultInmuebleShouldBeFound("depto.in=" + DEFAULT_DEPTO + "," + UPDATED_DEPTO);

        // Get all the inmuebleList where depto equals to UPDATED_DEPTO
        defaultInmuebleShouldNotBeFound("depto.in=" + UPDATED_DEPTO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByDeptoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where depto is not null
        defaultInmuebleShouldBeFound("depto.specified=true");

        // Get all the inmuebleList where depto is null
        defaultInmuebleShouldNotBeFound("depto.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesByAnexoIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where anexo equals to DEFAULT_ANEXO
        defaultInmuebleShouldBeFound("anexo.equals=" + DEFAULT_ANEXO);

        // Get all the inmuebleList where anexo equals to UPDATED_ANEXO
        defaultInmuebleShouldNotBeFound("anexo.equals=" + UPDATED_ANEXO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByAnexoIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where anexo in DEFAULT_ANEXO or UPDATED_ANEXO
        defaultInmuebleShouldBeFound("anexo.in=" + DEFAULT_ANEXO + "," + UPDATED_ANEXO);

        // Get all the inmuebleList where anexo equals to UPDATED_ANEXO
        defaultInmuebleShouldNotBeFound("anexo.in=" + UPDATED_ANEXO);
    }

    @Test
    @Transactional
    public void getAllInmueblesByAnexoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where anexo is not null
        defaultInmuebleShouldBeFound("anexo.specified=true");

        // Get all the inmuebleList where anexo is null
        defaultInmuebleShouldNotBeFound("anexo.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesById_glmIsEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where id_glm equals to DEFAULT_ID_GLM
        defaultInmuebleShouldBeFound("id_glm.equals=" + DEFAULT_ID_GLM);

        // Get all the inmuebleList where id_glm equals to UPDATED_ID_GLM
        defaultInmuebleShouldNotBeFound("id_glm.equals=" + UPDATED_ID_GLM);
    }

    @Test
    @Transactional
    public void getAllInmueblesById_glmIsInShouldWork() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where id_glm in DEFAULT_ID_GLM or UPDATED_ID_GLM
        defaultInmuebleShouldBeFound("id_glm.in=" + DEFAULT_ID_GLM + "," + UPDATED_ID_GLM);

        // Get all the inmuebleList where id_glm equals to UPDATED_ID_GLM
        defaultInmuebleShouldNotBeFound("id_glm.in=" + UPDATED_ID_GLM);
    }

    @Test
    @Transactional
    public void getAllInmueblesById_glmIsNullOrNotNull() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where id_glm is not null
        defaultInmuebleShouldBeFound("id_glm.specified=true");

        // Get all the inmuebleList where id_glm is null
        defaultInmuebleShouldNotBeFound("id_glm.specified=false");
    }

    @Test
    @Transactional
    public void getAllInmueblesById_glmIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where id_glm greater than or equals to DEFAULT_ID_GLM
        defaultInmuebleShouldBeFound("id_glm.greaterOrEqualThan=" + DEFAULT_ID_GLM);

        // Get all the inmuebleList where id_glm greater than or equals to UPDATED_ID_GLM
        defaultInmuebleShouldNotBeFound("id_glm.greaterOrEqualThan=" + UPDATED_ID_GLM);
    }

    @Test
    @Transactional
    public void getAllInmueblesById_glmIsLessThanSomething() throws Exception {
        // Initialize the database
        inmuebleRepository.saveAndFlush(inmueble);

        // Get all the inmuebleList where id_glm less than or equals to DEFAULT_ID_GLM
        defaultInmuebleShouldNotBeFound("id_glm.lessThan=" + DEFAULT_ID_GLM);

        // Get all the inmuebleList where id_glm less than or equals to UPDATED_ID_GLM
        defaultInmuebleShouldBeFound("id_glm.lessThan=" + UPDATED_ID_GLM);
    }


    @Test
    @Transactional
    public void getAllInmueblesByInspeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        Inspeccion inspeccion = InspeccionResourceIntTest.createEntity(em);
        em.persist(inspeccion);
        em.flush();
        inmueble.addInspeccion(inspeccion);
        inmuebleRepository.saveAndFlush(inmueble);
        Long inspeccionId = inspeccion.getId();

        // Get all the inmuebleList where inspeccion equals to inspeccionId
        defaultInmuebleShouldBeFound("inspeccionId.equals=" + inspeccionId);

        // Get all the inmuebleList where inspeccion equals to inspeccionId + 1
        defaultInmuebleShouldNotBeFound("inspeccionId.equals=" + (inspeccionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInmuebleShouldBeFound(String filter) throws Exception {
        restInmuebleMockMvc.perform(get("/api/inmuebles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inmueble.getId().intValue())))
            .andExpect(jsonPath("$.[*].calle").value(hasItem(DEFAULT_CALLE.toString())))
            .andExpect(jsonPath("$.[*].altura").value(hasItem(DEFAULT_ALTURA.toString())))
            .andExpect(jsonPath("$.[*].piso").value(hasItem(DEFAULT_PISO.toString())))
            .andExpect(jsonPath("$.[*].depto").value(hasItem(DEFAULT_DEPTO.toString())))
            .andExpect(jsonPath("$.[*].anexo").value(hasItem(DEFAULT_ANEXO.toString())))
            .andExpect(jsonPath("$.[*].id_glm").value(hasItem(DEFAULT_ID_GLM)));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInmuebleShouldNotBeFound(String filter) throws Exception {
        restInmuebleMockMvc.perform(get("/api/inmuebles?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
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
        inmuebleService.save(inmueble);

        int databaseSizeBeforeUpdate = inmuebleRepository.findAll().size();

        // Update the inmueble
        Inmueble updatedInmueble = inmuebleRepository.findOne(inmueble.getId());
        // Disconnect from session so that the updates on updatedInmueble are not directly saved in db
        em.detach(updatedInmueble);
        updatedInmueble
            .calle(UPDATED_CALLE)
            .altura(UPDATED_ALTURA)
            .piso(UPDATED_PISO)
            .depto(UPDATED_DEPTO)
            .anexo(UPDATED_ANEXO)
            .id_glm(UPDATED_ID_GLM);

        restInmuebleMockMvc.perform(put("/api/inmuebles")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInmueble)))
            .andExpect(status().isOk());

        // Validate the Inmueble in the database
        List<Inmueble> inmuebleList = inmuebleRepository.findAll();
        assertThat(inmuebleList).hasSize(databaseSizeBeforeUpdate);
        Inmueble testInmueble = inmuebleList.get(inmuebleList.size() - 1);
        assertThat(testInmueble.getCalle()).isEqualTo(UPDATED_CALLE);
        assertThat(testInmueble.getAltura()).isEqualTo(UPDATED_ALTURA);
        assertThat(testInmueble.getPiso()).isEqualTo(UPDATED_PISO);
        assertThat(testInmueble.getDepto()).isEqualTo(UPDATED_DEPTO);
        assertThat(testInmueble.getAnexo()).isEqualTo(UPDATED_ANEXO);
        assertThat(testInmueble.getId_glm()).isEqualTo(UPDATED_ID_GLM);
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
        inmuebleService.save(inmueble);

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
