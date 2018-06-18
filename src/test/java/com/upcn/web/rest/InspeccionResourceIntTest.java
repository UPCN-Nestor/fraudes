package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Inspeccion;
import com.upcn.domain.Anomalia;
import com.upcn.domain.Trabajo;
import com.upcn.domain.Inmueble;
import com.upcn.domain.Etapa;
import com.upcn.domain.Estado;
import com.upcn.domain.TipoInmueble;
import com.upcn.repository.InspeccionRepository;
import com.upcn.service.InspeccionService;
import com.upcn.web.rest.errors.ExceptionTranslator;
import com.upcn.service.dto.InspeccionCriteria;
import com.upcn.service.InspeccionQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static com.upcn.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InspeccionResource REST controller.
 *
 * @see InspeccionResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FrApp.class)
public class InspeccionResourceIntTest {

    private static final Long DEFAULT_ORDEN = 1L;
    private static final Long UPDATED_ORDEN = 2L;

    private static final LocalDate DEFAULT_FECHA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESHABITADA = false;
    private static final Boolean UPDATED_DESHABITADA = true;

    @Autowired
    private InspeccionRepository inspeccionRepository;

    @Autowired
    private InspeccionService inspeccionService;

    @Autowired
    private InspeccionQueryService inspeccionQueryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInspeccionMockMvc;

    private Inspeccion inspeccion;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InspeccionResource inspeccionResource = new InspeccionResource(inspeccionService, inspeccionQueryService);
        this.restInspeccionMockMvc = MockMvcBuilders.standaloneSetup(inspeccionResource)
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
    public static Inspeccion createEntity(EntityManager em) {
        Inspeccion inspeccion = new Inspeccion()
            .orden(DEFAULT_ORDEN)
            .fecha(DEFAULT_FECHA)
            .observaciones(DEFAULT_OBSERVACIONES)
            .deshabitada(DEFAULT_DESHABITADA);
        return inspeccion;
    }

    @Before
    public void initTest() {
        inspeccion = createEntity(em);
    }

    @Test
    @Transactional
    public void createInspeccion() throws Exception {
        int databaseSizeBeforeCreate = inspeccionRepository.findAll().size();

        // Create the Inspeccion
        restInspeccionMockMvc.perform(post("/api/inspeccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspeccion)))
            .andExpect(status().isCreated());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeCreate + 1);
        Inspeccion testInspeccion = inspeccionList.get(inspeccionList.size() - 1);
        assertThat(testInspeccion.getOrden()).isEqualTo(DEFAULT_ORDEN);
        assertThat(testInspeccion.getFecha()).isEqualTo(DEFAULT_FECHA);
        assertThat(testInspeccion.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testInspeccion.isDeshabitada()).isEqualTo(DEFAULT_DESHABITADA);
    }

    @Test
    @Transactional
    public void createInspeccionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inspeccionRepository.findAll().size();

        // Create the Inspeccion with an existing ID
        inspeccion.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInspeccionMockMvc.perform(post("/api/inspeccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspeccion)))
            .andExpect(status().isBadRequest());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInspeccions() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList
        restInspeccionMockMvc.perform(get("/api/inspeccions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].deshabitada").value(hasItem(DEFAULT_DESHABITADA.booleanValue())));
    }

    @Test
    @Transactional
    public void getInspeccion() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get the inspeccion
        restInspeccionMockMvc.perform(get("/api/inspeccions/{id}", inspeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inspeccion.getId().intValue()))
            .andExpect(jsonPath("$.orden").value(DEFAULT_ORDEN.intValue()))
            .andExpect(jsonPath("$.fecha").value(DEFAULT_FECHA.toString()))
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.deshabitada").value(DEFAULT_DESHABITADA.booleanValue()));
    }

    @Test
    @Transactional
    public void getAllInspeccionsByOrdenIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where orden equals to DEFAULT_ORDEN
        defaultInspeccionShouldBeFound("orden.equals=" + DEFAULT_ORDEN);

        // Get all the inspeccionList where orden equals to UPDATED_ORDEN
        defaultInspeccionShouldNotBeFound("orden.equals=" + UPDATED_ORDEN);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByOrdenIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where orden in DEFAULT_ORDEN or UPDATED_ORDEN
        defaultInspeccionShouldBeFound("orden.in=" + DEFAULT_ORDEN + "," + UPDATED_ORDEN);

        // Get all the inspeccionList where orden equals to UPDATED_ORDEN
        defaultInspeccionShouldNotBeFound("orden.in=" + UPDATED_ORDEN);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByOrdenIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where orden is not null
        defaultInspeccionShouldBeFound("orden.specified=true");

        // Get all the inspeccionList where orden is null
        defaultInspeccionShouldNotBeFound("orden.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByOrdenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where orden greater than or equals to DEFAULT_ORDEN
        defaultInspeccionShouldBeFound("orden.greaterOrEqualThan=" + DEFAULT_ORDEN);

        // Get all the inspeccionList where orden greater than or equals to UPDATED_ORDEN
        defaultInspeccionShouldNotBeFound("orden.greaterOrEqualThan=" + UPDATED_ORDEN);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByOrdenIsLessThanSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where orden less than or equals to DEFAULT_ORDEN
        defaultInspeccionShouldNotBeFound("orden.lessThan=" + DEFAULT_ORDEN);

        // Get all the inspeccionList where orden less than or equals to UPDATED_ORDEN
        defaultInspeccionShouldBeFound("orden.lessThan=" + UPDATED_ORDEN);
    }


    @Test
    @Transactional
    public void getAllInspeccionsByFechaIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fecha equals to DEFAULT_FECHA
        defaultInspeccionShouldBeFound("fecha.equals=" + DEFAULT_FECHA);

        // Get all the inspeccionList where fecha equals to UPDATED_FECHA
        defaultInspeccionShouldNotBeFound("fecha.equals=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fecha in DEFAULT_FECHA or UPDATED_FECHA
        defaultInspeccionShouldBeFound("fecha.in=" + DEFAULT_FECHA + "," + UPDATED_FECHA);

        // Get all the inspeccionList where fecha equals to UPDATED_FECHA
        defaultInspeccionShouldNotBeFound("fecha.in=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fecha is not null
        defaultInspeccionShouldBeFound("fecha.specified=true");

        // Get all the inspeccionList where fecha is null
        defaultInspeccionShouldNotBeFound("fecha.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fecha greater than or equals to DEFAULT_FECHA
        defaultInspeccionShouldBeFound("fecha.greaterOrEqualThan=" + DEFAULT_FECHA);

        // Get all the inspeccionList where fecha greater than or equals to UPDATED_FECHA
        defaultInspeccionShouldNotBeFound("fecha.greaterOrEqualThan=" + UPDATED_FECHA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaIsLessThanSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fecha less than or equals to DEFAULT_FECHA
        defaultInspeccionShouldNotBeFound("fecha.lessThan=" + DEFAULT_FECHA);

        // Get all the inspeccionList where fecha less than or equals to UPDATED_FECHA
        defaultInspeccionShouldBeFound("fecha.lessThan=" + UPDATED_FECHA);
    }


    @Test
    @Transactional
    public void getAllInspeccionsByObservacionesIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where observaciones equals to DEFAULT_OBSERVACIONES
        defaultInspeccionShouldBeFound("observaciones.equals=" + DEFAULT_OBSERVACIONES);

        // Get all the inspeccionList where observaciones equals to UPDATED_OBSERVACIONES
        defaultInspeccionShouldNotBeFound("observaciones.equals=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByObservacionesIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where observaciones in DEFAULT_OBSERVACIONES or UPDATED_OBSERVACIONES
        defaultInspeccionShouldBeFound("observaciones.in=" + DEFAULT_OBSERVACIONES + "," + UPDATED_OBSERVACIONES);

        // Get all the inspeccionList where observaciones equals to UPDATED_OBSERVACIONES
        defaultInspeccionShouldNotBeFound("observaciones.in=" + UPDATED_OBSERVACIONES);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByObservacionesIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where observaciones is not null
        defaultInspeccionShouldBeFound("observaciones.specified=true");

        // Get all the inspeccionList where observaciones is null
        defaultInspeccionShouldNotBeFound("observaciones.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByDeshabitadaIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where deshabitada equals to DEFAULT_DESHABITADA
        defaultInspeccionShouldBeFound("deshabitada.equals=" + DEFAULT_DESHABITADA);

        // Get all the inspeccionList where deshabitada equals to UPDATED_DESHABITADA
        defaultInspeccionShouldNotBeFound("deshabitada.equals=" + UPDATED_DESHABITADA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByDeshabitadaIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where deshabitada in DEFAULT_DESHABITADA or UPDATED_DESHABITADA
        defaultInspeccionShouldBeFound("deshabitada.in=" + DEFAULT_DESHABITADA + "," + UPDATED_DESHABITADA);

        // Get all the inspeccionList where deshabitada equals to UPDATED_DESHABITADA
        defaultInspeccionShouldNotBeFound("deshabitada.in=" + UPDATED_DESHABITADA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByDeshabitadaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where deshabitada is not null
        defaultInspeccionShouldBeFound("deshabitada.specified=true");

        // Get all the inspeccionList where deshabitada is null
        defaultInspeccionShouldNotBeFound("deshabitada.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByAnomaliaMedidorIsEqualToSomething() throws Exception {
        // Initialize the database
        Anomalia anomaliaMedidor = AnomaliaResourceIntTest.createEntity(em);
        em.persist(anomaliaMedidor);
        em.flush();
        inspeccion.addAnomaliaMedidor(anomaliaMedidor);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long anomaliaMedidorId = anomaliaMedidor.getId();

        // Get all the inspeccionList where anomaliaMedidor equals to anomaliaMedidorId
        defaultInspeccionShouldBeFound("anomaliaMedidorId.equals=" + anomaliaMedidorId);

        // Get all the inspeccionList where anomaliaMedidor equals to anomaliaMedidorId + 1
        defaultInspeccionShouldNotBeFound("anomaliaMedidorId.equals=" + (anomaliaMedidorId + 1));
    }


    @Test
    @Transactional
    public void getAllInspeccionsByTrabajoIsEqualToSomething() throws Exception {
        // Initialize the database
        Trabajo trabajo = TrabajoResourceIntTest.createEntity(em);
        em.persist(trabajo);
        em.flush();
        inspeccion.addTrabajo(trabajo);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long trabajoId = trabajo.getId();

        // Get all the inspeccionList where trabajo equals to trabajoId
        defaultInspeccionShouldBeFound("trabajoId.equals=" + trabajoId);

        // Get all the inspeccionList where trabajo equals to trabajoId + 1
        defaultInspeccionShouldNotBeFound("trabajoId.equals=" + (trabajoId + 1));
    }


    @Test
    @Transactional
    public void getAllInspeccionsByInmuebleIsEqualToSomething() throws Exception {
        // Initialize the database
        Inmueble inmueble = InmuebleResourceIntTest.createEntity(em);
        em.persist(inmueble);
        em.flush();
        inspeccion.setInmueble(inmueble);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long inmuebleId = inmueble.getId();

        // Get all the inspeccionList where inmueble equals to inmuebleId
        defaultInspeccionShouldBeFound("inmuebleId.equals=" + inmuebleId);

        // Get all the inspeccionList where inmueble equals to inmuebleId + 1
        defaultInspeccionShouldNotBeFound("inmuebleId.equals=" + (inmuebleId + 1));
    }


    @Test
    @Transactional
    public void getAllInspeccionsByEtapaIsEqualToSomething() throws Exception {
        // Initialize the database
        Etapa etapa = EtapaResourceIntTest.createEntity(em);
        em.persist(etapa);
        em.flush();
        inspeccion.setEtapa(etapa);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long etapaId = etapa.getId();

        // Get all the inspeccionList where etapa equals to etapaId
        defaultInspeccionShouldBeFound("etapaId.equals=" + etapaId);

        // Get all the inspeccionList where etapa equals to etapaId + 1
        defaultInspeccionShouldNotBeFound("etapaId.equals=" + (etapaId + 1));
    }


    @Test
    @Transactional
    public void getAllInspeccionsByEstadoIsEqualToSomething() throws Exception {
        // Initialize the database
        Estado estado = EstadoResourceIntTest.createEntity(em);
        em.persist(estado);
        em.flush();
        inspeccion.setEstado(estado);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long estadoId = estado.getId();

        // Get all the inspeccionList where estado equals to estadoId
        defaultInspeccionShouldBeFound("estadoId.equals=" + estadoId);

        // Get all the inspeccionList where estado equals to estadoId + 1
        defaultInspeccionShouldNotBeFound("estadoId.equals=" + (estadoId + 1));
    }


    @Test
    @Transactional
    public void getAllInspeccionsByTipoInmuebleIsEqualToSomething() throws Exception {
        // Initialize the database
        TipoInmueble tipoInmueble = TipoInmuebleResourceIntTest.createEntity(em);
        em.persist(tipoInmueble);
        em.flush();
        inspeccion.setTipoInmueble(tipoInmueble);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long tipoInmuebleId = tipoInmueble.getId();

        // Get all the inspeccionList where tipoInmueble equals to tipoInmuebleId
        defaultInspeccionShouldBeFound("tipoInmuebleId.equals=" + tipoInmuebleId);

        // Get all the inspeccionList where tipoInmueble equals to tipoInmuebleId + 1
        defaultInspeccionShouldNotBeFound("tipoInmuebleId.equals=" + (tipoInmuebleId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned
     */
    private void defaultInspeccionShouldBeFound(String filter) throws Exception {
        restInspeccionMockMvc.perform(get("/api/inspeccions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inspeccion.getId().intValue())))
            .andExpect(jsonPath("$.[*].orden").value(hasItem(DEFAULT_ORDEN.intValue())))
            .andExpect(jsonPath("$.[*].fecha").value(hasItem(DEFAULT_FECHA.toString())))
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].deshabitada").value(hasItem(DEFAULT_DESHABITADA.booleanValue())));
    }

    /**
     * Executes the search, and checks that the default entity is not returned
     */
    private void defaultInspeccionShouldNotBeFound(String filter) throws Exception {
        restInspeccionMockMvc.perform(get("/api/inspeccions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());
    }


    @Test
    @Transactional
    public void getNonExistingInspeccion() throws Exception {
        // Get the inspeccion
        restInspeccionMockMvc.perform(get("/api/inspeccions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInspeccion() throws Exception {
        // Initialize the database
        inspeccionService.save(inspeccion);

        int databaseSizeBeforeUpdate = inspeccionRepository.findAll().size();

        // Update the inspeccion
        Inspeccion updatedInspeccion = inspeccionRepository.findOne(inspeccion.getId());
        // Disconnect from session so that the updates on updatedInspeccion are not directly saved in db
        em.detach(updatedInspeccion);
        updatedInspeccion
            .orden(UPDATED_ORDEN)
            .fecha(UPDATED_FECHA)
            .observaciones(UPDATED_OBSERVACIONES)
            .deshabitada(UPDATED_DESHABITADA);

        restInspeccionMockMvc.perform(put("/api/inspeccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspeccion)))
            .andExpect(status().isOk());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeUpdate);
        Inspeccion testInspeccion = inspeccionList.get(inspeccionList.size() - 1);
        assertThat(testInspeccion.getOrden()).isEqualTo(UPDATED_ORDEN);
        assertThat(testInspeccion.getFecha()).isEqualTo(UPDATED_FECHA);
        assertThat(testInspeccion.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testInspeccion.isDeshabitada()).isEqualTo(UPDATED_DESHABITADA);
    }

    @Test
    @Transactional
    public void updateNonExistingInspeccion() throws Exception {
        int databaseSizeBeforeUpdate = inspeccionRepository.findAll().size();

        // Create the Inspeccion

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInspeccionMockMvc.perform(put("/api/inspeccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inspeccion)))
            .andExpect(status().isCreated());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInspeccion() throws Exception {
        // Initialize the database
        inspeccionService.save(inspeccion);

        int databaseSizeBeforeDelete = inspeccionRepository.findAll().size();

        // Get the inspeccion
        restInspeccionMockMvc.perform(delete("/api/inspeccions/{id}", inspeccion.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inspeccion.class);
        Inspeccion inspeccion1 = new Inspeccion();
        inspeccion1.setId(1L);
        Inspeccion inspeccion2 = new Inspeccion();
        inspeccion2.setId(inspeccion1.getId());
        assertThat(inspeccion1).isEqualTo(inspeccion2);
        inspeccion2.setId(2L);
        assertThat(inspeccion1).isNotEqualTo(inspeccion2);
        inspeccion1.setId(null);
        assertThat(inspeccion1).isNotEqualTo(inspeccion2);
    }
}
