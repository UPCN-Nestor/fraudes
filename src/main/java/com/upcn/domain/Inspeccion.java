package com.upcn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
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

    @Column(name = "fecha")
    private LocalDate fecha;

    @Column(name = "observaciones")
    private String observaciones;

    @Column(name = "deshabitada")
    private Boolean deshabitada;

    @Column(name = "usuario")
    private String usuario;

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

    public LocalDate getFecha() {
        return fecha;
    }

    public Inspeccion fecha(LocalDate fecha) {
        this.fecha = fecha;
        return this;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
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
            ", fecha='" + getFecha() + "'" +
            ", observaciones='" + getObservaciones() + "'" +
            ", deshabitada='" + isDeshabitada() + "'" +
            ", usuario='" + getUsuario() + "'" +
            "}";
    }
}
