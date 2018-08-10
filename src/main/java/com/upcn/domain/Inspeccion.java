package com.upcn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Inspeccion.
 */
@Entity
@Table(name = "inspeccion")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inspeccion implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orden")
    private Long orden;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "deshabitada")
    private Boolean deshabitada;

    @Column(name = "usuario")
    private String usuario;

    @Column(name = "fechahora")
    private Instant fechahora;

    @Column(name = "medidor_instalado")
    private String medidorInstalado;

    @Column(name = "ultima_lectura")
    private Float ultimaLectura;

    @Column(name = "medidor_retirado")
    private String medidorRetirado;

    @Column(name = "socio")
    private Integer socio;

    @Column(name = "suministro")
    private Integer suministro;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "tarifa")
    private String tarifa;

    @Column(name = "mts_cable")
    private Float mtsCable;

    @Column(name = "lectura_nuevo")
    private Float lecturaNuevo;

    @Lob
    @Column(name = "foto")
    private byte[] foto;

    @Column(name = "foto_content_type")
    private String fotoContentType;

    @Column(name = "estado_glm")
    private String estadoGLM;

    @Column(name = "lectura_actual")
    private Float lecturaActual;

    @Column(name = "fecha_toma")
    private LocalDate fechaToma;

    @Column(name = "medidor_nuevo_libre")
    private String medidorNuevoLibre;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "inspeccion_anomalia_medidor",
               joinColumns = @JoinColumn(name="inspeccions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="anomalia_medidors_id", referencedColumnName="id"))
    private Set<Anomalia> anomaliaMedidors = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "inspeccion_trabajo",
               joinColumns = @JoinColumn(name="inspeccions_id", referencedColumnName="id"),
               inverseJoinColumns = @JoinColumn(name="trabajos_id", referencedColumnName="id"))
    private Set<Trabajo> trabajos = new HashSet<>();

    @ManyToOne
    private Inmueble inmueble;

    @ManyToOne
    private Etapa etapa;

    @ManyToOne
    private Estado estado;

    @ManyToOne
    private TipoInmueble tipoInmueble;

    @OneToOne
    @JoinColumn(unique = true)
    private Medidor medidorNuevo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrden() {
        return orden;
    }

    public Inspeccion orden(Long orden) {
        this.orden = orden;
        return this;
    }

    public void setOrden(Long orden) {
        this.orden = orden;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public Inspeccion observaciones(String observaciones) {
        this.observaciones = observaciones;
        return this;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Boolean isDeshabitada() {
        return deshabitada;
    }

    public Inspeccion deshabitada(Boolean deshabitada) {
        this.deshabitada = deshabitada;
        return this;
    }

    public void setDeshabitada(Boolean deshabitada) {
        this.deshabitada = deshabitada;
    }

    public String getUsuario() {
        return usuario;
    }

    public Inspeccion usuario(String usuario) {
        this.usuario = usuario;
        return this;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public Instant getFechahora() {
        return fechahora;
    }

    public Inspeccion fechahora(Instant fechahora) {
        this.fechahora = fechahora;
        return this;
    }

    public void setFechahora(Instant fechahora) {
        this.fechahora = fechahora;
    }

    public String getMedidorInstalado() {
        return medidorInstalado;
    }

    public Inspeccion medidorInstalado(String medidorInstalado) {
        this.medidorInstalado = medidorInstalado;
        return this;
    }

    public void setMedidorInstalado(String medidorInstalado) {
        this.medidorInstalado = medidorInstalado;
    }

    public Float getUltimaLectura() {
        return ultimaLectura;
    }

    public Inspeccion ultimaLectura(Float ultimaLectura) {
        this.ultimaLectura = ultimaLectura;
        return this;
    }

    public void setUltimaLectura(Float ultimaLectura) {
        this.ultimaLectura = ultimaLectura;
    }

    public String getMedidorRetirado() {
        return medidorRetirado;
    }

    public Inspeccion medidorRetirado(String medidorRetirado) {
        this.medidorRetirado = medidorRetirado;
        return this;
    }

    public void setMedidorRetirado(String medidorRetirado) {
        this.medidorRetirado = medidorRetirado;
    }

    public Integer getSocio() {
        return socio;
    }

    public Inspeccion socio(Integer socio) {
        this.socio = socio;
        return this;
    }

    public void setSocio(Integer socio) {
        this.socio = socio;
    }

    public Integer getSuministro() {
        return suministro;
    }

    public Inspeccion suministro(Integer suministro) {
        this.suministro = suministro;
        return this;
    }

    public void setSuministro(Integer suministro) {
        this.suministro = suministro;
    }

    public String getNombre() {
        return nombre;
    }

    public Inspeccion nombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTarifa() {
        return tarifa;
    }

    public Inspeccion tarifa(String tarifa) {
        this.tarifa = tarifa;
        return this;
    }

    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }

    public Float getMtsCable() {
        return mtsCable;
    }

    public Inspeccion mtsCable(Float mtsCable) {
        this.mtsCable = mtsCable;
        return this;
    }

    public void setMtsCable(Float mtsCable) {
        this.mtsCable = mtsCable;
    }

    public Float getLecturaNuevo() {
        return lecturaNuevo;
    }

    public Inspeccion lecturaNuevo(Float lecturaNuevo) {
        this.lecturaNuevo = lecturaNuevo;
        return this;
    }

    public void setLecturaNuevo(Float lecturaNuevo) {
        this.lecturaNuevo = lecturaNuevo;
    }

    public byte[] getFoto() {
        return foto;
    }

    public Inspeccion foto(byte[] foto) {
        this.foto = foto;
        return this;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public String getFotoContentType() {
        return fotoContentType;
    }

    public Inspeccion fotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
        return this;
    }

    public void setFotoContentType(String fotoContentType) {
        this.fotoContentType = fotoContentType;
    }

    public String getEstadoGLM() {
        return estadoGLM;
    }

    public Inspeccion estadoGLM(String estadoGLM) {
        this.estadoGLM = estadoGLM;
        return this;
    }

    public void setEstadoGLM(String estadoGLM) {
        this.estadoGLM = estadoGLM;
    }

    public Float getLecturaActual() {
        return lecturaActual;
    }

    public Inspeccion lecturaActual(Float lecturaActual) {
        this.lecturaActual = lecturaActual;
        return this;
    }

    public void setLecturaActual(Float lecturaActual) {
        this.lecturaActual = lecturaActual;
    }

    public LocalDate getFechaToma() {
        return fechaToma;
    }

    public Inspeccion fechaToma(LocalDate fechaToma) {
        this.fechaToma = fechaToma;
        return this;
    }

    public void setFechaToma(LocalDate fechaToma) {
        this.fechaToma = fechaToma;
    }

    public String getMedidorNuevoLibre() {
        return medidorNuevoLibre;
    }

    public Inspeccion medidorNuevoLibre(String medidorNuevoLibre) {
        this.medidorNuevoLibre = medidorNuevoLibre;
        return this;
    }

    public void setMedidorNuevoLibre(String medidorNuevoLibre) {
        this.medidorNuevoLibre = medidorNuevoLibre;
    }

    public Set<Anomalia> getAnomaliaMedidors() {
        return anomaliaMedidors;
    }

    public Inspeccion anomaliaMedidors(Set<Anomalia> anomalias) {
        this.anomaliaMedidors = anomalias;
        return this;
    }

    public Inspeccion addAnomaliaMedidor(Anomalia anomalia) {
        this.anomaliaMedidors.add(anomalia);
        anomalia.getInspeccions().add(this);
        return this;
    }

    public Inspeccion removeAnomaliaMedidor(Anomalia anomalia) {
        this.anomaliaMedidors.remove(anomalia);
        anomalia.getInspeccions().remove(this);
        return this;
    }

    public void setAnomaliaMedidors(Set<Anomalia> anomalias) {
        this.anomaliaMedidors = anomalias;
    }

    public Set<Trabajo> getTrabajos() {
        return trabajos;
    }

    public Inspeccion trabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
        return this;
    }

    public Inspeccion addTrabajo(Trabajo trabajo) {
        this.trabajos.add(trabajo);
        trabajo.getInspeccions().add(this);
        return this;
    }

    public Inspeccion removeTrabajo(Trabajo trabajo) {
        this.trabajos.remove(trabajo);
        trabajo.getInspeccions().remove(this);
        return this;
    }

    public void setTrabajos(Set<Trabajo> trabajos) {
        this.trabajos = trabajos;
    }

    public Inmueble getInmueble() {
        return inmueble;
    }

    public Inspeccion inmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
        return this;
    }

    public void setInmueble(Inmueble inmueble) {
        this.inmueble = inmueble;
    }

    public Etapa getEtapa() {
        return etapa;
    }

    public Inspeccion etapa(Etapa etapa) {
        this.etapa = etapa;
        return this;
    }

    public void setEtapa(Etapa etapa) {
        this.etapa = etapa;
    }

    public Estado getEstado() {
        return estado;
    }

    public Inspeccion estado(Estado estado) {
        this.estado = estado;
        return this;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public TipoInmueble getTipoInmueble() {
        return tipoInmueble;
    }

    public Inspeccion tipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
        return this;
    }

    public void setTipoInmueble(TipoInmueble tipoInmueble) {
        this.tipoInmueble = tipoInmueble;
    }

    public Medidor getMedidorNuevo() {
        return medidorNuevo;
    }

    public Inspeccion medidorNuevo(Medidor medidor) {
        this.medidorNuevo = medidor;
        return this;
    }

    public void setMedidorNuevo(Medidor medidor) {
        this.medidorNuevo = medidor;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Inspeccion inspeccion = (Inspeccion) o;
        if (inspeccion.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inspeccion.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inspeccion{" +
            "id=" + getId() +
            ", orden=" + getOrden() +
            ", observaciones='" + getObservaciones() + "'" +
            ", deshabitada='" + isDeshabitada() + "'" +
            ", usuario='" + getUsuario() + "'" +
            ", fechahora='" + getFechahora() + "'" +
            ", medidorInstalado='" + getMedidorInstalado() + "'" +
            ", ultimaLectura=" + getUltimaLectura() +
            ", medidorRetirado='" + getMedidorRetirado() + "'" +
            ", socio=" + getSocio() +
            ", suministro=" + getSuministro() +
            ", nombre='" + getNombre() + "'" +
            ", tarifa='" + getTarifa() + "'" +
            ", mtsCable=" + getMtsCable() +
            ", lecturaNuevo=" + getLecturaNuevo() +
            ", foto='" + getFoto() + "'" +
            ", fotoContentType='" + getFotoContentType() + "'" +
            ", estadoGLM='" + getEstadoGLM() + "'" +
            ", lecturaActual=" + getLecturaActual() +
            ", fechaToma='" + getFechaToma() + "'" +
            ", medidorNuevoLibre='" + getMedidorNuevoLibre() + "'" +
            "}";
    }
}
