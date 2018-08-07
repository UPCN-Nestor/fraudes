package com.upcn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Trabajo.
 */
@Entity
@Table(name = "trabajo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Trabajo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "costo")
    private Float costo;

    @Column(name = "usa_medidor")
    private Boolean usaMedidor;

    @ManyToMany(mappedBy = "trabajos")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Inspeccion> inspeccions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Trabajo descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Float getCosto() {
        return costo;
    }

    public Trabajo costo(Float costo) {
        this.costo = costo;
        return this;
    }

    public void setCosto(Float costo) {
        this.costo = costo;
    }

    public Boolean isUsaMedidor() {
        return usaMedidor;
    }

    public Trabajo usaMedidor(Boolean usaMedidor) {
        this.usaMedidor = usaMedidor;
        return this;
    }

    public void setUsaMedidor(Boolean usaMedidor) {
        this.usaMedidor = usaMedidor;
    }

    public Set<Inspeccion> getInspeccions() {
        return inspeccions;
    }

    public Trabajo inspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
        return this;
    }

    public Trabajo addInspeccion(Inspeccion inspeccion) {
        this.inspeccions.add(inspeccion);
        inspeccion.getTrabajos().add(this);
        return this;
    }

    public Trabajo removeInspeccion(Inspeccion inspeccion) {
        this.inspeccions.remove(inspeccion);
        inspeccion.getTrabajos().remove(this);
        return this;
    }

    public void setInspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
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
        Trabajo trabajo = (Trabajo) o;
        if (trabajo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), trabajo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Trabajo{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            ", costo=" + getCosto() +
            ", usaMedidor='" + isUsaMedidor() + "'" +
            "}";
    }
}
