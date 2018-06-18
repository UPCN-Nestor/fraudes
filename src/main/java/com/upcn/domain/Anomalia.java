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
 * A Anomalia.
 */
@Entity
@Table(name = "anomalia")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Anomalia implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @ManyToMany(mappedBy = "anomaliaMedidors")
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

    public Anomalia descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Inspeccion> getInspeccions() {
        return inspeccions;
    }

    public Anomalia inspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
        return this;
    }

    public Anomalia addInspeccion(Inspeccion inspeccion) {
        this.inspeccions.add(inspeccion);
        inspeccion.getAnomaliaMedidors().add(this);
        return this;
    }

    public Anomalia removeInspeccion(Inspeccion inspeccion) {
        this.inspeccions.remove(inspeccion);
        inspeccion.getAnomaliaMedidors().remove(this);
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
        Anomalia anomalia = (Anomalia) o;
        if (anomalia.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), anomalia.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Anomalia{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
