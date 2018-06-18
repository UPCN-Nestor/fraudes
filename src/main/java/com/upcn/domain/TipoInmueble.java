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
 * A TipoInmueble.
 */
@Entity
@Table(name = "tipo_inmueble")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TipoInmueble implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion")
    private String descripcion;

    @OneToMany(mappedBy = "tipoInmueble")
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

    public TipoInmueble descripcion(String descripcion) {
        this.descripcion = descripcion;
        return this;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Set<Inspeccion> getInspeccions() {
        return inspeccions;
    }

    public TipoInmueble inspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
        return this;
    }

    public TipoInmueble addInspeccion(Inspeccion inspeccion) {
        this.inspeccions.add(inspeccion);
        inspeccion.setTipoInmueble(this);
        return this;
    }

    public TipoInmueble removeInspeccion(Inspeccion inspeccion) {
        this.inspeccions.remove(inspeccion);
        inspeccion.setTipoInmueble(null);
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
        TipoInmueble tipoInmueble = (TipoInmueble) o;
        if (tipoInmueble.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), tipoInmueble.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "TipoInmueble{" +
            "id=" + getId() +
            ", descripcion='" + getDescripcion() + "'" +
            "}";
    }
}
