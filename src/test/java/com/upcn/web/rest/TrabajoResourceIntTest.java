package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Trabajo;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.TrabajoRepository;
import com.upcn.service.TrabajoService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.TrabajoCriteria;
import com.upcn.service.TrabajoQueryService;

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
 * Test class for the TrabajoResource REST controller.
 *
 * @see TrabajoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class TrabajoResourceIntTest {

    private static final String DEFAULT_DESCRIPCION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPCION = "BBBBBBBBBB";

    private static final Float DEFAULT_COSTO = 1F;
    private static final Float UPDATED_COSTO = 2F;

    private static final Boolean DEFAULT_USA_MEDIDOR = false;
    private static final Boolean UPDATED_USA_MEDIDOR = true;

    private static final Boolean DEFAULT_USA_CABLE = false;
    private static final Boolean UPDATED_USA_CABLE = true;

    private static final Float DEFAULT_COSTO_TRIF = 1F;
    private static final Float UPDATED_COSTO_TRIF = 2F;

    @Autowired
    private TrabajoRepository trabajoRepository;

    @Autowired
    private TrabajoService trabajoService;

    @Autowired
    private TrabajoQueryService trabajoQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTrabajoMockMvc;

    private Trabajo trabajo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TrabajoResource trabajoResource = new TrabajoResource(trabajoService, trabajoQueryService);
        this.restTrabajoMockMvc = MockMvcBuilders.standaloneSetup(trabajoResource)
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
    public static Trabajo createEntity(EntityManager em) {
        Trabajo trabajo = new Trabajo()
            .descripcion(DEFAULT_DESCRIPCION)
            .costo(DEFAULT_COSTO)
            .usaMedidor(DEFAULT_USA_MEDIDOR)
            .usaCable(DEFAULT_USA_CABLE)
            .costoTrif(DEFAULT_COSTO_TRIF);
        return trabajo;
    }

    @Before
    public void initTest() {
        trabajo = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrabajo() throws Exception {
        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();

        // Create the Trabajo
        restTrabajoMockMvc.perform(post("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isCreated());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate + 1);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getDescripcion()).isEqualTo(DEFAULT_DESCRIPCION);
        assertThat(testTrabajo.getCosto()).isEqualTo(DEFAULT_COSTO);
        assertThat(testTrabajo.isUsaMedidor()).isEqualTo(DEFAULT_USA_MEDIDOR);
        assertThat(testTrabajo.isUsaCable()).isEqualTo(DEFAULT_USA_CABLE);
        assertThat(testTrabajo.getCostoTrif()).isEqualTo(DEFAULT_COSTO_TRIF);
    }

    @Test
    @Transactional
    public void createTrabajoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = trabajoRepository.findAll().size();

        // Create the Trabajo with an existing ID
        trabajo.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTrabajoMockMvc.perform(post("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isBadRequest());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrabajos() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList
        restTrabajoMockMvc.perform(get("/api/trabajos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].usaMedidor").value(hasItem(DEFAULT_USA_MEDIDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].usaCable").value(hasItem(DEFAULT_USA_CABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].costoTrif").value(hasItem(DEFAULT_COSTO_TRIF.doubleValue())));
    }

    @Test
    @Transactional
    public void getTrabajo() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get the trabajo
        restTrabajoMockMvc.perform(get("/api/trabajos/{id}", trabajo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trabajo.getId().intValue()))
            .andExpect(jsonPath("$.descripcion").value(DEFAULT_DESCRIPCION.toString()))
            .andExpect(jsonPath("$.costo").value(DEFAULT_COSTO.doubleValue()))
            .andExpect(jsonPath("$.usaMedidor").value(DEFAULT_USA_MEDIDOR.booleanValue()))
            .andExpect(jsonPath("$.usaCable").value(DEFAULT_USA_CABLE.booleanValue()))
            .andExpect(jsonPath("$.costoTrif").value(DEFAULT_COSTO_TRIF.doubleValue()));
    }

    @Test
    @Transactional
    public void getAllTrabajosByDescripcionIsEqualToSomething() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where descripcion equals to DEFAULT_DESCRIPCION
        defaultTrabajoShouldBeFound("descripcion.equals=" + DEFAULT_DESCRIPCION);

        // Get all the trabajoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTrabajoShouldNotBeFound("descripcion.equals=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTrabajosByDescripcionIsInShouldWork() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where descripcion in DEFAULT_DESCRIPCION or UPDATED_DESCRIPCION
        defaultTrabajoShouldBeFound("descripcion.in=" + DEFAULT_DESCRIPCION + "," + UPDATED_DESCRIPCION);

        // Get all the trabajoList where descripcion equals to UPDATED_DESCRIPCION
        defaultTrabajoShouldNotBeFound("descripcion.in=" + UPDATED_DESCRIPCION);
    }

    @Test
    @Transactional
    public void getAllTrabajosByDescripcionIsNullOrNotNull() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where descripcion is not null
        defaultTrabajoShouldBeFound("descripcion.specified=true");

        // Get all the trabajoList where descripcion is null
        defaultTrabajoShouldNotBeFound("descripcion.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoIsEqualToSomething() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costo equals to DEFAULT_COSTO
        defaultTrabajoShouldBeFound("costo.equals=" + DEFAULT_COSTO);

        // Get all the trabajoList where costo equals to UPDATED_COSTO
        defaultTrabajoShouldNotBeFound("costo.equals=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoIsInShouldWork() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costo in DEFAULT_COSTO or UPDATED_COSTO
        defaultTrabajoShouldBeFound("costo.in=" + DEFAULT_COSTO + "," + UPDATED_COSTO);

        // Get all the trabajoList where costo equals to UPDATED_COSTO
        defaultTrabajoShouldNotBeFound("costo.in=" + UPDATED_COSTO);
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoIsNullOrNotNull() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costo is not null
        defaultTrabajoShouldBeFound("costo.specified=true");

        // Get all the trabajoList where costo is null
        defaultTrabajoShouldNotBeFound("costo.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaMedidorIsEqualToSomething() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaMedidor equals to DEFAULT_USA_MEDIDOR
        defaultTrabajoShouldBeFound("usaMedidor.equals=" + DEFAULT_USA_MEDIDOR);

        // Get all the trabajoList where usaMedidor equals to UPDATED_USA_MEDIDOR
        defaultTrabajoShouldNotBeFound("usaMedidor.equals=" + UPDATED_USA_MEDIDOR);
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaMedidorIsInShouldWork() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaMedidor in DEFAULT_USA_MEDIDOR or UPDATED_USA_MEDIDOR
        defaultTrabajoShouldBeFound("usaMedidor.in=" + DEFAULT_USA_MEDIDOR + "," + UPDATED_USA_MEDIDOR);

        // Get all the trabajoList where usaMedidor equals to UPDATED_USA_MEDIDOR
        defaultTrabajoShouldNotBeFound("usaMedidor.in=" + UPDATED_USA_MEDIDOR);
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaMedidorIsNullOrNotNull() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaMedidor is not null
        defaultTrabajoShouldBeFound("usaMedidor.specified=true");

        // Get all the trabajoList where usaMedidor is null
        defaultTrabajoShouldNotBeFound("usaMedidor.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaCableIsEqualToSomething() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaCable equals to DEFAULT_USA_CABLE
        defaultTrabajoShouldBeFound("usaCable.equals=" + DEFAULT_USA_CABLE);

        // Get all the trabajoList where usaCable equals to UPDATED_USA_CABLE
        defaultTrabajoShouldNotBeFound("usaCable.equals=" + UPDATED_USA_CABLE);
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaCableIsInShouldWork() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaCable in DEFAULT_USA_CABLE or UPDATED_USA_CABLE
        defaultTrabajoShouldBeFound("usaCable.in=" + DEFAULT_USA_CABLE + "," + UPDATED_USA_CABLE);

        // Get all the trabajoList where usaCable equals to UPDATED_USA_CABLE
        defaultTrabajoShouldNotBeFound("usaCable.in=" + UPDATED_USA_CABLE);
    }

    @Test
    @Transactional
    public void getAllTrabajosByUsaCableIsNullOrNotNull() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where usaCable is not null
        defaultTrabajoShouldBeFound("usaCable.specified=true");

        // Get all the trabajoList where usaCable is null
        defaultTrabajoShouldNotBeFound("usaCable.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoTrifIsEqualToSomething() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costoTrif equals to DEFAULT_COSTO_TRIF
        defaultTrabajoShouldBeFound("costoTrif.equals=" + DEFAULT_COSTO_TRIF);

        // Get all the trabajoList where costoTrif equals to UPDATED_COSTO_TRIF
        defaultTrabajoShouldNotBeFound("costoTrif.equals=" + UPDATED_COSTO_TRIF);
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoTrifIsInShouldWork() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costoTrif in DEFAULT_COSTO_TRIF or UPDATED_COSTO_TRIF
        defaultTrabajoShouldBeFound("costoTrif.in=" + DEFAULT_COSTO_TRIF + "," + UPDATED_COSTO_TRIF);

        // Get all the trabajoList where costoTrif equals to UPDATED_COSTO_TRIF
        defaultTrabajoShouldNotBeFound("costoTrif.in=" + UPDATED_COSTO_TRIF);
    }

    @Test
    @Transactional
    public void getAllTrabajosByCostoTrifIsNullOrNotNull() throws Exception {
        // Initialize the database
        trabajoRepository.saveAndFlush(trabajo);

        // Get all the trabajoList where costoTrif is not null
        defaultTrabajoShouldBeFound("costoTrif.specified=true");

        // Get all the trabajoList where costoTrif is null
        defaultTrabajoShouldNotBeFound("costoTrif.specified=false");
    }

    @Test
    @Transactional
    public void getAllTrabajosByInspeccionIsEqualToSomething() throws Exception {
        // Initialize the database
        Inspeccion inspeccion = InspeccionResourceIntTest.createEntity(em);
        em.persist(inspeccion);
        em.flush();
        trabajo.addInspeccion(inspeccion);
        trabajoRepository.saveAndFlush(trabajo);
        Long inspeccionId = inspeccion.getId();

        // Get all the trabajoList where inspeccion equals to inspeccionId
        defaultTrabajoShouldBeFound("inspeccionId.equals=" + inspeccionId);

        // Get all the trabajoList where inspeccion equals to inspeccionId + 1
        defaultTrabajoShouldNotBeFound("inspeccionId.equals=" + (inspeccionId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultTrabajoShouldBeFound(String filter) throws Exception {
        restTrabajoMockMvc.perform(get("/api/trabajos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trabajo.getId().intValue())))
            .andExpect(jsonPath("$.[*].descripcion").value(hasItem(DEFAULT_DESCRIPCION.toString())))
            .andExpect(jsonPath("$.[*].costo").value(hasItem(DEFAULT_COSTO.doubleValue())))
            .andExpect(jsonPath("$.[*].usaMedidor").value(hasItem(DEFAULT_USA_MEDIDOR.booleanValue())))
            .andExpect(jsonPath("$.[*].usaCable").value(hasItem(DEFAULT_USA_CABLE.booleanValue())))
            .andExpect(jsonPath("$.[*].costoTrif").value(hasItem(DEFAULT_COSTO_TRIF.doubleValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultTrabajoShouldNotBeFound(String filter) throws Exception {
        restTrabajoMockMvc.perform(get("/api/trabajos?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingTrabajo() throws Exception {
        // Get the trabajo
        restTrabajoMockMvc.perform(get("/api/trabajos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrabajo() throws Exception {
        // Initialize the database
        trabajoService.save(trabajo);

        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Update the trabajo
        Trabajo updatedTrabajo = trabajoRepository.findOne(trabajo.getId());
        // Disconnect from session so that the updates on updatedTrabajo are not directly saved in db
        em.detach(updatedTrabajo);
        updatedTrabajo
            .descripcion(UPDATED_DESCRIPCION)
            .costo(UPDATED_COSTO)
            .usaMedidor(UPDATED_USA_MEDIDOR)
            .usaCable(UPDATED_USA_CABLE)
            .costoTrif(UPDATED_COSTO_TRIF);

        restTrabajoMockMvc.perform(put("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedTrabajo)))
            .andExpect(status().isOk());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate);
        Trabajo testTrabajo = trabajoList.get(trabajoList.size() - 1);
        assertThat(testTrabajo.getDescripcion()).isEqualTo(UPDATED_DESCRIPCION);
        assertThat(testTrabajo.getCosto()).isEqualTo(UPDATED_COSTO);
        assertThat(testTrabajo.isUsaMedidor()).isEqualTo(UPDATED_USA_MEDIDOR);
        assertThat(testTrabajo.isUsaCable()).isEqualTo(UPDATED_USA_CABLE);
        assertThat(testTrabajo.getCostoTrif()).isEqualTo(UPDATED_COSTO_TRIF);
    }

    @Test
    @Transactional
    public void updateNonExistingTrabajo() throws Exception {
        int databaseSizeBeforeUpdate = trabajoRepository.findAll().size();

        // Create the Trabajo

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTrabajoMockMvc.perform(put("/api/trabajos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(trabajo)))
            .andExpect(status().isCreated());

        // Validate the Trabajo in the database
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrabajo() throws Exception {
        // Initialize the database
        trabajoService.save(trabajo);

        int databaseSizeBeforeDelete = trabajoRepository.findAll().size();

        // Get the trabajo
        restTrabajoMockMvc.perform(delete("/api/trabajos/{id}", trabajo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trabajo> trabajoList = trabajoRepository.findAll();
        assertThat(trabajoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trabajo.class);
        Trabajo trabajo1 = new Trabajo();
        trabajo1.setId(1L);
        Trabajo trabajo2 = new Trabajo();
        trabajo2.setId(trabajo1.getId());
        assertThat(trabajo1).isEqualTo(trabajo2);
        trabajo2.setId(2L);
        assertThat(trabajo1).isNotEqualTo(trabajo2);
        trabajo1.setId(null);
        assertThat(trabajo1).isNotEqualTo(trabajo2);
    }
}
