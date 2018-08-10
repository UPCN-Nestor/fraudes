package com.upcn.web.rest;

import com.upcn.FrApp;

import com.upcn.domain.Inspeccion;
import com.upcn.domain.Anomalia;
import com.upcn.domain.Trabajo;
import com.upcn.domain.Inmueble;
import com.upcn.domain.Etapa;
import com.upcn.domain.Estado;
import com.upcn.domain.TipoInmueble;
import com.upcn.domain.Medidor;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.Instant;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
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

    private static final String DEFAULT_OBSERVACIONES = "AAAAAAAAAA";
    private static final String UPDATED_OBSERVACIONES = "BBBBBBBBBB";

    private static final Boolean DEFAULT_DESHABITADA = false;
    private static final Boolean UPDATED_DESHABITADA = true;

    private static final String DEFAULT_USUARIO = "AAAAAAAAAA";
    private static final String UPDATED_USUARIO = "BBBBBBBBBB";

    private static final Instant DEFAULT_FECHAHORA = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_FECHAHORA = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final String DEFAULT_MEDIDOR_INSTALADO = "AAAAAAAAAA";
    private static final String UPDATED_MEDIDOR_INSTALADO = "BBBBBBBBBB";

    private static final Float DEFAULT_ULTIMA_LECTURA = 1F;
    private static final Float UPDATED_ULTIMA_LECTURA = 2F;

    private static final String DEFAULT_MEDIDOR_RETIRADO = "AAAAAAAAAA";
    private static final String UPDATED_MEDIDOR_RETIRADO = "BBBBBBBBBB";

    private static final Integer DEFAULT_SOCIO = 1;
    private static final Integer UPDATED_SOCIO = 2;

    private static final Integer DEFAULT_SUMINISTRO = 1;
    private static final Integer UPDATED_SUMINISTRO = 2;

    private static final String DEFAULT_NOMBRE = "AAAAAAAAAA";
    private static final String UPDATED_NOMBRE = "BBBBBBBBBB";

    private static final String DEFAULT_TARIFA = "AAAAAAAAAA";
    private static final String UPDATED_TARIFA = "BBBBBBBBBB";

    private static final Float DEFAULT_MTS_CABLE = 1F;
    private static final Float UPDATED_MTS_CABLE = 2F;

    private static final Float DEFAULT_LECTURA_NUEVO = 1F;
    private static final Float UPDATED_LECTURA_NUEVO = 2F;

    private static final byte[] DEFAULT_FOTO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_FOTO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_FOTO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_FOTO_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_ESTADO_GLM = "AAAAAAAAAA";
    private static final String UPDATED_ESTADO_GLM = "BBBBBBBBBB";

    private static final Float DEFAULT_LECTURA_ACTUAL = 1F;
    private static final Float UPDATED_LECTURA_ACTUAL = 2F;

    private static final LocalDate DEFAULT_FECHA_TOMA = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_FECHA_TOMA = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_MEDIDOR_NUEVO_LIBRE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIDOR_NUEVO_LIBRE = "BBBBBBBBBB";

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
            .observaciones(DEFAULT_OBSERVACIONES)
            .deshabitada(DEFAULT_DESHABITADA)
            .usuario(DEFAULT_USUARIO)
            .fechahora(DEFAULT_FECHAHORA)
            .medidorInstalado(DEFAULT_MEDIDOR_INSTALADO)
            .ultimaLectura(DEFAULT_ULTIMA_LECTURA)
            .medidorRetirado(DEFAULT_MEDIDOR_RETIRADO)
            .socio(DEFAULT_SOCIO)
            .suministro(DEFAULT_SUMINISTRO)
            .nombre(DEFAULT_NOMBRE)
            .tarifa(DEFAULT_TARIFA)
            .mtsCable(DEFAULT_MTS_CABLE)
            .lecturaNuevo(DEFAULT_LECTURA_NUEVO)
            .foto(DEFAULT_FOTO)
            .fotoContentType(DEFAULT_FOTO_CONTENT_TYPE)
            .estadoGLM(DEFAULT_ESTADO_GLM)
            .lecturaActual(DEFAULT_LECTURA_ACTUAL)
            .fechaToma(DEFAULT_FECHA_TOMA)
            .medidorNuevoLibre(DEFAULT_MEDIDOR_NUEVO_LIBRE);
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
        assertThat(testInspeccion.getObservaciones()).isEqualTo(DEFAULT_OBSERVACIONES);
        assertThat(testInspeccion.isDeshabitada()).isEqualTo(DEFAULT_DESHABITADA);
        assertThat(testInspeccion.getUsuario()).isEqualTo(DEFAULT_USUARIO);
        assertThat(testInspeccion.getFechahora()).isEqualTo(DEFAULT_FECHAHORA);
        assertThat(testInspeccion.getMedidorInstalado()).isEqualTo(DEFAULT_MEDIDOR_INSTALADO);
        assertThat(testInspeccion.getUltimaLectura()).isEqualTo(DEFAULT_ULTIMA_LECTURA);
        assertThat(testInspeccion.getMedidorRetirado()).isEqualTo(DEFAULT_MEDIDOR_RETIRADO);
        assertThat(testInspeccion.getSocio()).isEqualTo(DEFAULT_SOCIO);
        assertThat(testInspeccion.getSuministro()).isEqualTo(DEFAULT_SUMINISTRO);
        assertThat(testInspeccion.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testInspeccion.getTarifa()).isEqualTo(DEFAULT_TARIFA);
        assertThat(testInspeccion.getMtsCable()).isEqualTo(DEFAULT_MTS_CABLE);
        assertThat(testInspeccion.getLecturaNuevo()).isEqualTo(DEFAULT_LECTURA_NUEVO);
        assertThat(testInspeccion.getFoto()).isEqualTo(DEFAULT_FOTO);
        assertThat(testInspeccion.getFotoContentType()).isEqualTo(DEFAULT_FOTO_CONTENT_TYPE);
        assertThat(testInspeccion.getEstadoGLM()).isEqualTo(DEFAULT_ESTADO_GLM);
        assertThat(testInspeccion.getLecturaActual()).isEqualTo(DEFAULT_LECTURA_ACTUAL);
        assertThat(testInspeccion.getFechaToma()).isEqualTo(DEFAULT_FECHA_TOMA);
        assertThat(testInspeccion.getMedidorNuevoLibre()).isEqualTo(DEFAULT_MEDIDOR_NUEVO_LIBRE);
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
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].deshabitada").value(hasItem(DEFAULT_DESHABITADA.booleanValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())))
            .andExpect(jsonPath("$.[*].fechahora").value(hasItem(DEFAULT_FECHAHORA.toString())))
            .andExpect(jsonPath("$.[*].medidorInstalado").value(hasItem(DEFAULT_MEDIDOR_INSTALADO.toString())))
            .andExpect(jsonPath("$.[*].ultimaLectura").value(hasItem(DEFAULT_ULTIMA_LECTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].medidorRetirado").value(hasItem(DEFAULT_MEDIDOR_RETIRADO.toString())))
            .andExpect(jsonPath("$.[*].socio").value(hasItem(DEFAULT_SOCIO)))
            .andExpect(jsonPath("$.[*].suministro").value(hasItem(DEFAULT_SUMINISTRO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tarifa").value(hasItem(DEFAULT_TARIFA.toString())))
            .andExpect(jsonPath("$.[*].mtsCable").value(hasItem(DEFAULT_MTS_CABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].lecturaNuevo").value(hasItem(DEFAULT_LECTURA_NUEVO.doubleValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].estadoGLM").value(hasItem(DEFAULT_ESTADO_GLM.toString())))
            .andExpect(jsonPath("$.[*].lecturaActual").value(hasItem(DEFAULT_LECTURA_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaToma").value(hasItem(DEFAULT_FECHA_TOMA.toString())))
            .andExpect(jsonPath("$.[*].medidorNuevoLibre").value(hasItem(DEFAULT_MEDIDOR_NUEVO_LIBRE.toString())));
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
            .andExpect(jsonPath("$.observaciones").value(DEFAULT_OBSERVACIONES.toString()))
            .andExpect(jsonPath("$.deshabitada").value(DEFAULT_DESHABITADA.booleanValue()))
            .andExpect(jsonPath("$.usuario").value(DEFAULT_USUARIO.toString()))
            .andExpect(jsonPath("$.fechahora").value(DEFAULT_FECHAHORA.toString()))
            .andExpect(jsonPath("$.medidorInstalado").value(DEFAULT_MEDIDOR_INSTALADO.toString()))
            .andExpect(jsonPath("$.ultimaLectura").value(DEFAULT_ULTIMA_LECTURA.doubleValue()))
            .andExpect(jsonPath("$.medidorRetirado").value(DEFAULT_MEDIDOR_RETIRADO.toString()))
            .andExpect(jsonPath("$.socio").value(DEFAULT_SOCIO))
            .andExpect(jsonPath("$.suministro").value(DEFAULT_SUMINISTRO))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.tarifa").value(DEFAULT_TARIFA.toString()))
            .andExpect(jsonPath("$.mtsCable").value(DEFAULT_MTS_CABLE.doubleValue()))
            .andExpect(jsonPath("$.lecturaNuevo").value(DEFAULT_LECTURA_NUEVO.doubleValue()))
            .andExpect(jsonPath("$.fotoContentType").value(DEFAULT_FOTO_CONTENT_TYPE))
            .andExpect(jsonPath("$.foto").value(Base64Utils.encodeToString(DEFAULT_FOTO)))
            .andExpect(jsonPath("$.estadoGLM").value(DEFAULT_ESTADO_GLM.toString()))
            .andExpect(jsonPath("$.lecturaActual").value(DEFAULT_LECTURA_ACTUAL.doubleValue()))
            .andExpect(jsonPath("$.fechaToma").value(DEFAULT_FECHA_TOMA.toString()))
            .andExpect(jsonPath("$.medidorNuevoLibre").value(DEFAULT_MEDIDOR_NUEVO_LIBRE.toString()));
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
    public void getAllInspeccionsByUsuarioIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where usuario equals to DEFAULT_USUARIO
        defaultInspeccionShouldBeFound("usuario.equals=" + DEFAULT_USUARIO);

        // Get all the inspeccionList where usuario equals to UPDATED_USUARIO
        defaultInspeccionShouldNotBeFound("usuario.equals=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByUsuarioIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where usuario in DEFAULT_USUARIO or UPDATED_USUARIO
        defaultInspeccionShouldBeFound("usuario.in=" + DEFAULT_USUARIO + "," + UPDATED_USUARIO);

        // Get all the inspeccionList where usuario equals to UPDATED_USUARIO
        defaultInspeccionShouldNotBeFound("usuario.in=" + UPDATED_USUARIO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByUsuarioIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where usuario is not null
        defaultInspeccionShouldBeFound("usuario.specified=true");

        // Get all the inspeccionList where usuario is null
        defaultInspeccionShouldNotBeFound("usuario.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechahoraIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechahora equals to DEFAULT_FECHAHORA
        defaultInspeccionShouldBeFound("fechahora.equals=" + DEFAULT_FECHAHORA);

        // Get all the inspeccionList where fechahora equals to UPDATED_FECHAHORA
        defaultInspeccionShouldNotBeFound("fechahora.equals=" + UPDATED_FECHAHORA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechahoraIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechahora in DEFAULT_FECHAHORA or UPDATED_FECHAHORA
        defaultInspeccionShouldBeFound("fechahora.in=" + DEFAULT_FECHAHORA + "," + UPDATED_FECHAHORA);

        // Get all the inspeccionList where fechahora equals to UPDATED_FECHAHORA
        defaultInspeccionShouldNotBeFound("fechahora.in=" + UPDATED_FECHAHORA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechahoraIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechahora is not null
        defaultInspeccionShouldBeFound("fechahora.specified=true");

        // Get all the inspeccionList where fechahora is null
        defaultInspeccionShouldNotBeFound("fechahora.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorInstaladoIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorInstalado equals to DEFAULT_MEDIDOR_INSTALADO
        defaultInspeccionShouldBeFound("medidorInstalado.equals=" + DEFAULT_MEDIDOR_INSTALADO);

        // Get all the inspeccionList where medidorInstalado equals to UPDATED_MEDIDOR_INSTALADO
        defaultInspeccionShouldNotBeFound("medidorInstalado.equals=" + UPDATED_MEDIDOR_INSTALADO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorInstaladoIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorInstalado in DEFAULT_MEDIDOR_INSTALADO or UPDATED_MEDIDOR_INSTALADO
        defaultInspeccionShouldBeFound("medidorInstalado.in=" + DEFAULT_MEDIDOR_INSTALADO + "," + UPDATED_MEDIDOR_INSTALADO);

        // Get all the inspeccionList where medidorInstalado equals to UPDATED_MEDIDOR_INSTALADO
        defaultInspeccionShouldNotBeFound("medidorInstalado.in=" + UPDATED_MEDIDOR_INSTALADO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorInstaladoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorInstalado is not null
        defaultInspeccionShouldBeFound("medidorInstalado.specified=true");

        // Get all the inspeccionList where medidorInstalado is null
        defaultInspeccionShouldNotBeFound("medidorInstalado.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByUltimaLecturaIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where ultimaLectura equals to DEFAULT_ULTIMA_LECTURA
        defaultInspeccionShouldBeFound("ultimaLectura.equals=" + DEFAULT_ULTIMA_LECTURA);

        // Get all the inspeccionList where ultimaLectura equals to UPDATED_ULTIMA_LECTURA
        defaultInspeccionShouldNotBeFound("ultimaLectura.equals=" + UPDATED_ULTIMA_LECTURA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByUltimaLecturaIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where ultimaLectura in DEFAULT_ULTIMA_LECTURA or UPDATED_ULTIMA_LECTURA
        defaultInspeccionShouldBeFound("ultimaLectura.in=" + DEFAULT_ULTIMA_LECTURA + "," + UPDATED_ULTIMA_LECTURA);

        // Get all the inspeccionList where ultimaLectura equals to UPDATED_ULTIMA_LECTURA
        defaultInspeccionShouldNotBeFound("ultimaLectura.in=" + UPDATED_ULTIMA_LECTURA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByUltimaLecturaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where ultimaLectura is not null
        defaultInspeccionShouldBeFound("ultimaLectura.specified=true");

        // Get all the inspeccionList where ultimaLectura is null
        defaultInspeccionShouldNotBeFound("ultimaLectura.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorRetiradoIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorRetirado equals to DEFAULT_MEDIDOR_RETIRADO
        defaultInspeccionShouldBeFound("medidorRetirado.equals=" + DEFAULT_MEDIDOR_RETIRADO);

        // Get all the inspeccionList where medidorRetirado equals to UPDATED_MEDIDOR_RETIRADO
        defaultInspeccionShouldNotBeFound("medidorRetirado.equals=" + UPDATED_MEDIDOR_RETIRADO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorRetiradoIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorRetirado in DEFAULT_MEDIDOR_RETIRADO or UPDATED_MEDIDOR_RETIRADO
        defaultInspeccionShouldBeFound("medidorRetirado.in=" + DEFAULT_MEDIDOR_RETIRADO + "," + UPDATED_MEDIDOR_RETIRADO);

        // Get all the inspeccionList where medidorRetirado equals to UPDATED_MEDIDOR_RETIRADO
        defaultInspeccionShouldNotBeFound("medidorRetirado.in=" + UPDATED_MEDIDOR_RETIRADO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorRetiradoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorRetirado is not null
        defaultInspeccionShouldBeFound("medidorRetirado.specified=true");

        // Get all the inspeccionList where medidorRetirado is null
        defaultInspeccionShouldNotBeFound("medidorRetirado.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySocioIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where socio equals to DEFAULT_SOCIO
        defaultInspeccionShouldBeFound("socio.equals=" + DEFAULT_SOCIO);

        // Get all the inspeccionList where socio equals to UPDATED_SOCIO
        defaultInspeccionShouldNotBeFound("socio.equals=" + UPDATED_SOCIO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySocioIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where socio in DEFAULT_SOCIO or UPDATED_SOCIO
        defaultInspeccionShouldBeFound("socio.in=" + DEFAULT_SOCIO + "," + UPDATED_SOCIO);

        // Get all the inspeccionList where socio equals to UPDATED_SOCIO
        defaultInspeccionShouldNotBeFound("socio.in=" + UPDATED_SOCIO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySocioIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where socio is not null
        defaultInspeccionShouldBeFound("socio.specified=true");

        // Get all the inspeccionList where socio is null
        defaultInspeccionShouldNotBeFound("socio.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySocioIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where socio greater than or equals to DEFAULT_SOCIO
        defaultInspeccionShouldBeFound("socio.greaterOrEqualThan=" + DEFAULT_SOCIO);

        // Get all the inspeccionList where socio greater than or equals to UPDATED_SOCIO
        defaultInspeccionShouldNotBeFound("socio.greaterOrEqualThan=" + UPDATED_SOCIO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySocioIsLessThanSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where socio less than or equals to DEFAULT_SOCIO
        defaultInspeccionShouldNotBeFound("socio.lessThan=" + DEFAULT_SOCIO);

        // Get all the inspeccionList where socio less than or equals to UPDATED_SOCIO
        defaultInspeccionShouldBeFound("socio.lessThan=" + UPDATED_SOCIO);
    }


    @Test
    @Transactional
    public void getAllInspeccionsBySuministroIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where suministro equals to DEFAULT_SUMINISTRO
        defaultInspeccionShouldBeFound("suministro.equals=" + DEFAULT_SUMINISTRO);

        // Get all the inspeccionList where suministro equals to UPDATED_SUMINISTRO
        defaultInspeccionShouldNotBeFound("suministro.equals=" + UPDATED_SUMINISTRO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySuministroIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where suministro in DEFAULT_SUMINISTRO or UPDATED_SUMINISTRO
        defaultInspeccionShouldBeFound("suministro.in=" + DEFAULT_SUMINISTRO + "," + UPDATED_SUMINISTRO);

        // Get all the inspeccionList where suministro equals to UPDATED_SUMINISTRO
        defaultInspeccionShouldNotBeFound("suministro.in=" + UPDATED_SUMINISTRO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySuministroIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where suministro is not null
        defaultInspeccionShouldBeFound("suministro.specified=true");

        // Get all the inspeccionList where suministro is null
        defaultInspeccionShouldNotBeFound("suministro.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySuministroIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where suministro greater than or equals to DEFAULT_SUMINISTRO
        defaultInspeccionShouldBeFound("suministro.greaterOrEqualThan=" + DEFAULT_SUMINISTRO);

        // Get all the inspeccionList where suministro greater than or equals to UPDATED_SUMINISTRO
        defaultInspeccionShouldNotBeFound("suministro.greaterOrEqualThan=" + UPDATED_SUMINISTRO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsBySuministroIsLessThanSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where suministro less than or equals to DEFAULT_SUMINISTRO
        defaultInspeccionShouldNotBeFound("suministro.lessThan=" + DEFAULT_SUMINISTRO);

        // Get all the inspeccionList where suministro less than or equals to UPDATED_SUMINISTRO
        defaultInspeccionShouldBeFound("suministro.lessThan=" + UPDATED_SUMINISTRO);
    }


    @Test
    @Transactional
    public void getAllInspeccionsByNombreIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where nombre equals to DEFAULT_NOMBRE
        defaultInspeccionShouldBeFound("nombre.equals=" + DEFAULT_NOMBRE);

        // Get all the inspeccionList where nombre equals to UPDATED_NOMBRE
        defaultInspeccionShouldNotBeFound("nombre.equals=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByNombreIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where nombre in DEFAULT_NOMBRE or UPDATED_NOMBRE
        defaultInspeccionShouldBeFound("nombre.in=" + DEFAULT_NOMBRE + "," + UPDATED_NOMBRE);

        // Get all the inspeccionList where nombre equals to UPDATED_NOMBRE
        defaultInspeccionShouldNotBeFound("nombre.in=" + UPDATED_NOMBRE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByNombreIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where nombre is not null
        defaultInspeccionShouldBeFound("nombre.specified=true");

        // Get all the inspeccionList where nombre is null
        defaultInspeccionShouldNotBeFound("nombre.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByTarifaIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where tarifa equals to DEFAULT_TARIFA
        defaultInspeccionShouldBeFound("tarifa.equals=" + DEFAULT_TARIFA);

        // Get all the inspeccionList where tarifa equals to UPDATED_TARIFA
        defaultInspeccionShouldNotBeFound("tarifa.equals=" + UPDATED_TARIFA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByTarifaIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where tarifa in DEFAULT_TARIFA or UPDATED_TARIFA
        defaultInspeccionShouldBeFound("tarifa.in=" + DEFAULT_TARIFA + "," + UPDATED_TARIFA);

        // Get all the inspeccionList where tarifa equals to UPDATED_TARIFA
        defaultInspeccionShouldNotBeFound("tarifa.in=" + UPDATED_TARIFA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByTarifaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where tarifa is not null
        defaultInspeccionShouldBeFound("tarifa.specified=true");

        // Get all the inspeccionList where tarifa is null
        defaultInspeccionShouldNotBeFound("tarifa.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMtsCableIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where mtsCable equals to DEFAULT_MTS_CABLE
        defaultInspeccionShouldBeFound("mtsCable.equals=" + DEFAULT_MTS_CABLE);

        // Get all the inspeccionList where mtsCable equals to UPDATED_MTS_CABLE
        defaultInspeccionShouldNotBeFound("mtsCable.equals=" + UPDATED_MTS_CABLE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMtsCableIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where mtsCable in DEFAULT_MTS_CABLE or UPDATED_MTS_CABLE
        defaultInspeccionShouldBeFound("mtsCable.in=" + DEFAULT_MTS_CABLE + "," + UPDATED_MTS_CABLE);

        // Get all the inspeccionList where mtsCable equals to UPDATED_MTS_CABLE
        defaultInspeccionShouldNotBeFound("mtsCable.in=" + UPDATED_MTS_CABLE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMtsCableIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where mtsCable is not null
        defaultInspeccionShouldBeFound("mtsCable.specified=true");

        // Get all the inspeccionList where mtsCable is null
        defaultInspeccionShouldNotBeFound("mtsCable.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaNuevoIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaNuevo equals to DEFAULT_LECTURA_NUEVO
        defaultInspeccionShouldBeFound("lecturaNuevo.equals=" + DEFAULT_LECTURA_NUEVO);

        // Get all the inspeccionList where lecturaNuevo equals to UPDATED_LECTURA_NUEVO
        defaultInspeccionShouldNotBeFound("lecturaNuevo.equals=" + UPDATED_LECTURA_NUEVO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaNuevoIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaNuevo in DEFAULT_LECTURA_NUEVO or UPDATED_LECTURA_NUEVO
        defaultInspeccionShouldBeFound("lecturaNuevo.in=" + DEFAULT_LECTURA_NUEVO + "," + UPDATED_LECTURA_NUEVO);

        // Get all the inspeccionList where lecturaNuevo equals to UPDATED_LECTURA_NUEVO
        defaultInspeccionShouldNotBeFound("lecturaNuevo.in=" + UPDATED_LECTURA_NUEVO);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaNuevoIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaNuevo is not null
        defaultInspeccionShouldBeFound("lecturaNuevo.specified=true");

        // Get all the inspeccionList where lecturaNuevo is null
        defaultInspeccionShouldNotBeFound("lecturaNuevo.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByEstadoGLMIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where estadoGLM equals to DEFAULT_ESTADO_GLM
        defaultInspeccionShouldBeFound("estadoGLM.equals=" + DEFAULT_ESTADO_GLM);

        // Get all the inspeccionList where estadoGLM equals to UPDATED_ESTADO_GLM
        defaultInspeccionShouldNotBeFound("estadoGLM.equals=" + UPDATED_ESTADO_GLM);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByEstadoGLMIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where estadoGLM in DEFAULT_ESTADO_GLM or UPDATED_ESTADO_GLM
        defaultInspeccionShouldBeFound("estadoGLM.in=" + DEFAULT_ESTADO_GLM + "," + UPDATED_ESTADO_GLM);

        // Get all the inspeccionList where estadoGLM equals to UPDATED_ESTADO_GLM
        defaultInspeccionShouldNotBeFound("estadoGLM.in=" + UPDATED_ESTADO_GLM);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByEstadoGLMIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where estadoGLM is not null
        defaultInspeccionShouldBeFound("estadoGLM.specified=true");

        // Get all the inspeccionList where estadoGLM is null
        defaultInspeccionShouldNotBeFound("estadoGLM.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaActualIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaActual equals to DEFAULT_LECTURA_ACTUAL
        defaultInspeccionShouldBeFound("lecturaActual.equals=" + DEFAULT_LECTURA_ACTUAL);

        // Get all the inspeccionList where lecturaActual equals to UPDATED_LECTURA_ACTUAL
        defaultInspeccionShouldNotBeFound("lecturaActual.equals=" + UPDATED_LECTURA_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaActualIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaActual in DEFAULT_LECTURA_ACTUAL or UPDATED_LECTURA_ACTUAL
        defaultInspeccionShouldBeFound("lecturaActual.in=" + DEFAULT_LECTURA_ACTUAL + "," + UPDATED_LECTURA_ACTUAL);

        // Get all the inspeccionList where lecturaActual equals to UPDATED_LECTURA_ACTUAL
        defaultInspeccionShouldNotBeFound("lecturaActual.in=" + UPDATED_LECTURA_ACTUAL);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByLecturaActualIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where lecturaActual is not null
        defaultInspeccionShouldBeFound("lecturaActual.specified=true");

        // Get all the inspeccionList where lecturaActual is null
        defaultInspeccionShouldNotBeFound("lecturaActual.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaTomaIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechaToma equals to DEFAULT_FECHA_TOMA
        defaultInspeccionShouldBeFound("fechaToma.equals=" + DEFAULT_FECHA_TOMA);

        // Get all the inspeccionList where fechaToma equals to UPDATED_FECHA_TOMA
        defaultInspeccionShouldNotBeFound("fechaToma.equals=" + UPDATED_FECHA_TOMA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaTomaIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechaToma in DEFAULT_FECHA_TOMA or UPDATED_FECHA_TOMA
        defaultInspeccionShouldBeFound("fechaToma.in=" + DEFAULT_FECHA_TOMA + "," + UPDATED_FECHA_TOMA);

        // Get all the inspeccionList where fechaToma equals to UPDATED_FECHA_TOMA
        defaultInspeccionShouldNotBeFound("fechaToma.in=" + UPDATED_FECHA_TOMA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaTomaIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechaToma is not null
        defaultInspeccionShouldBeFound("fechaToma.specified=true");

        // Get all the inspeccionList where fechaToma is null
        defaultInspeccionShouldNotBeFound("fechaToma.specified=false");
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaTomaIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechaToma greater than or equals to DEFAULT_FECHA_TOMA
        defaultInspeccionShouldBeFound("fechaToma.greaterOrEqualThan=" + DEFAULT_FECHA_TOMA);

        // Get all the inspeccionList where fechaToma greater than or equals to UPDATED_FECHA_TOMA
        defaultInspeccionShouldNotBeFound("fechaToma.greaterOrEqualThan=" + UPDATED_FECHA_TOMA);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByFechaTomaIsLessThanSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where fechaToma less than or equals to DEFAULT_FECHA_TOMA
        defaultInspeccionShouldNotBeFound("fechaToma.lessThan=" + DEFAULT_FECHA_TOMA);

        // Get all the inspeccionList where fechaToma less than or equals to UPDATED_FECHA_TOMA
        defaultInspeccionShouldBeFound("fechaToma.lessThan=" + UPDATED_FECHA_TOMA);
    }


    @Test
    @Transactional
    public void getAllInspeccionsByMedidorNuevoLibreIsEqualToSomething() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorNuevoLibre equals to DEFAULT_MEDIDOR_NUEVO_LIBRE
        defaultInspeccionShouldBeFound("medidorNuevoLibre.equals=" + DEFAULT_MEDIDOR_NUEVO_LIBRE);

        // Get all the inspeccionList where medidorNuevoLibre equals to UPDATED_MEDIDOR_NUEVO_LIBRE
        defaultInspeccionShouldNotBeFound("medidorNuevoLibre.equals=" + UPDATED_MEDIDOR_NUEVO_LIBRE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorNuevoLibreIsInShouldWork() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorNuevoLibre in DEFAULT_MEDIDOR_NUEVO_LIBRE or UPDATED_MEDIDOR_NUEVO_LIBRE
        defaultInspeccionShouldBeFound("medidorNuevoLibre.in=" + DEFAULT_MEDIDOR_NUEVO_LIBRE + "," + UPDATED_MEDIDOR_NUEVO_LIBRE);

        // Get all the inspeccionList where medidorNuevoLibre equals to UPDATED_MEDIDOR_NUEVO_LIBRE
        defaultInspeccionShouldNotBeFound("medidorNuevoLibre.in=" + UPDATED_MEDIDOR_NUEVO_LIBRE);
    }

    @Test
    @Transactional
    public void getAllInspeccionsByMedidorNuevoLibreIsNullOrNotNull() throws Exception {
        // Initialize the database
        inspeccionRepository.saveAndFlush(inspeccion);

        // Get all the inspeccionList where medidorNuevoLibre is not null
        defaultInspeccionShouldBeFound("medidorNuevoLibre.specified=true");

        // Get all the inspeccionList where medidorNuevoLibre is null
        defaultInspeccionShouldNotBeFound("medidorNuevoLibre.specified=false");
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


    @Test
    @Transactional
    public void getAllInspeccionsByMedidorNuevoIsEqualToSomething() throws Exception {
        // Initialize the database
        Medidor medidorNuevo = MedidorResourceIntTest.createEntity(em);
        em.persist(medidorNuevo);
        em.flush();
        inspeccion.setMedidorNuevo(medidorNuevo);
        inspeccionRepository.saveAndFlush(inspeccion);
        Long medidorNuevoId = medidorNuevo.getId();

        // Get all the inspeccionList where medidorNuevo equals to medidorNuevoId
        defaultInspeccionShouldBeFound("medidorNuevoId.equals=" + medidorNuevoId);

        // Get all the inspeccionList where medidorNuevo equals to medidorNuevoId + 1
        defaultInspeccionShouldNotBeFound("medidorNuevoId.equals=" + (medidorNuevoId + 1));
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
            .andExpect(jsonPath("$.[*].observaciones").value(hasItem(DEFAULT_OBSERVACIONES.toString())))
            .andExpect(jsonPath("$.[*].deshabitada").value(hasItem(DEFAULT_DESHABITADA.booleanValue())))
            .andExpect(jsonPath("$.[*].usuario").value(hasItem(DEFAULT_USUARIO.toString())))
            .andExpect(jsonPath("$.[*].fechahora").value(hasItem(DEFAULT_FECHAHORA.toString())))
            .andExpect(jsonPath("$.[*].medidorInstalado").value(hasItem(DEFAULT_MEDIDOR_INSTALADO.toString())))
            .andExpect(jsonPath("$.[*].ultimaLectura").value(hasItem(DEFAULT_ULTIMA_LECTURA.doubleValue())))
            .andExpect(jsonPath("$.[*].medidorRetirado").value(hasItem(DEFAULT_MEDIDOR_RETIRADO.toString())))
            .andExpect(jsonPath("$.[*].socio").value(hasItem(DEFAULT_SOCIO)))
            .andExpect(jsonPath("$.[*].suministro").value(hasItem(DEFAULT_SUMINISTRO)))
            .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
            .andExpect(jsonPath("$.[*].tarifa").value(hasItem(DEFAULT_TARIFA.toString())))
            .andExpect(jsonPath("$.[*].mtsCable").value(hasItem(DEFAULT_MTS_CABLE.doubleValue())))
            .andExpect(jsonPath("$.[*].lecturaNuevo").value(hasItem(DEFAULT_LECTURA_NUEVO.doubleValue())))
            .andExpect(jsonPath("$.[*].fotoContentType").value(hasItem(DEFAULT_FOTO_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].foto").value(hasItem(Base64Utils.encodeToString(DEFAULT_FOTO))))
            .andExpect(jsonPath("$.[*].estadoGLM").value(hasItem(DEFAULT_ESTADO_GLM.toString())))
            .andExpect(jsonPath("$.[*].lecturaActual").value(hasItem(DEFAULT_LECTURA_ACTUAL.doubleValue())))
            .andExpect(jsonPath("$.[*].fechaToma").value(hasItem(DEFAULT_FECHA_TOMA.toString())))
            .andExpect(jsonPath("$.[*].medidorNuevoLibre").value(hasItem(DEFAULT_MEDIDOR_NUEVO_LIBRE.toString())));
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
            .observaciones(UPDATED_OBSERVACIONES)
            .deshabitada(UPDATED_DESHABITADA)
            .usuario(UPDATED_USUARIO)
            .fechahora(UPDATED_FECHAHORA)
            .medidorInstalado(UPDATED_MEDIDOR_INSTALADO)
            .ultimaLectura(UPDATED_ULTIMA_LECTURA)
            .medidorRetirado(UPDATED_MEDIDOR_RETIRADO)
            .socio(UPDATED_SOCIO)
            .suministro(UPDATED_SUMINISTRO)
            .nombre(UPDATED_NOMBRE)
            .tarifa(UPDATED_TARIFA)
            .mtsCable(UPDATED_MTS_CABLE)
            .lecturaNuevo(UPDATED_LECTURA_NUEVO)
            .foto(UPDATED_FOTO)
            .fotoContentType(UPDATED_FOTO_CONTENT_TYPE)
            .estadoGLM(UPDATED_ESTADO_GLM)
            .lecturaActual(UPDATED_LECTURA_ACTUAL)
            .fechaToma(UPDATED_FECHA_TOMA)
            .medidorNuevoLibre(UPDATED_MEDIDOR_NUEVO_LIBRE);

        restInspeccionMockMvc.perform(put("/api/inspeccions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedInspeccion)))
            .andExpect(status().isOk());

        // Validate the Inspeccion in the database
        List<Inspeccion> inspeccionList = inspeccionRepository.findAll();
        assertThat(inspeccionList).hasSize(databaseSizeBeforeUpdate);
        Inspeccion testInspeccion = inspeccionList.get(inspeccionList.size() - 1);
        assertThat(testInspeccion.getOrden()).isEqualTo(UPDATED_ORDEN);
        assertThat(testInspeccion.getObservaciones()).isEqualTo(UPDATED_OBSERVACIONES);
        assertThat(testInspeccion.isDeshabitada()).isEqualTo(UPDATED_DESHABITADA);
        assertThat(testInspeccion.getUsuario()).isEqualTo(UPDATED_USUARIO);
        assertThat(testInspeccion.getFechahora()).isEqualTo(UPDATED_FECHAHORA);
        assertThat(testInspeccion.getMedidorInstalado()).isEqualTo(UPDATED_MEDIDOR_INSTALADO);
        assertThat(testInspeccion.getUltimaLectura()).isEqualTo(UPDATED_ULTIMA_LECTURA);
        assertThat(testInspeccion.getMedidorRetirado()).isEqualTo(UPDATED_MEDIDOR_RETIRADO);
        assertThat(testInspeccion.getSocio()).isEqualTo(UPDATED_SOCIO);
        assertThat(testInspeccion.getSuministro()).isEqualTo(UPDATED_SUMINISTRO);
        assertThat(testInspeccion.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testInspeccion.getTarifa()).isEqualTo(UPDATED_TARIFA);
        assertThat(testInspeccion.getMtsCable()).isEqualTo(UPDATED_MTS_CABLE);
        assertThat(testInspeccion.getLecturaNuevo()).isEqualTo(UPDATED_LECTURA_NUEVO);
        assertThat(testInspeccion.getFoto()).isEqualTo(UPDATED_FOTO);
        assertThat(testInspeccion.getFotoContentType()).isEqualTo(UPDATED_FOTO_CONTENT_TYPE);
        assertThat(testInspeccion.getEstadoGLM()).isEqualTo(UPDATED_ESTADO_GLM);
        assertThat(testInspeccion.getLecturaActual()).isEqualTo(UPDATED_LECTURA_ACTUAL);
        assertThat(testInspeccion.getFechaToma()).isEqualTo(UPDATED_FECHA_TOMA);
        assertThat(testInspeccion.getMedidorNuevoLibre()).isEqualTo(UPDATED_MEDIDOR_NUEVO_LIBRE);
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
