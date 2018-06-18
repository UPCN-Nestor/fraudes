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
 * A Etapa.
 */
@Entity
@Table(name = "etapa")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Etapa implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private Long numero;

    @Column(name = "descripcion_corta")
    private String descripcionCorta;

    @Column(name = "descripcion_larga")
    private String descripcionLarga;

    @OneToMany(mappedBy = "etapa")
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

    public Long getNumero() {
        return numero;
    }

    public Etapa numero(Long numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(Long numero) {
        this.numero = numero;
    }

    public String getDescripcionCorta() {
        return descripcionCorta;
    }

    public Etapa descripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
        return this;
    }

    public void setDescripcionCorta(String descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public String getDescripcionLarga() {
        return descripcionLarga;
    }

    public Etapa descripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
        return this;
    }

    public void setDescripcionLarga(String descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public Set<Inspeccion> getInspeccions() {
        return inspeccions;
    }

    public Etapa inspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
        return this;
    }

    public Etapa addInspeccion(Inspeccion inspeccion) {
        this.inspeccions.add(inspeccion);
        inspeccion.setEtapa(this);
        return this;
    }

    public Etapa removeInspeccion(Inspeccion inspeccion) {
        this.inspeccions.remove(inspeccion);
        inspeccion.setEtapa(null);
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
        Etapa etapa = (Etapa) o;
        if (etapa.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), etapa.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Etapa{" +
            "id=" + getId() +
            ", numero=" + getNumero() +
            ", descripcionCorta='" + getDescripcionCorta() + "'" +
            ", descripcionLarga='" + getDescripcionLarga() + "'" +
            "}";
    }
}
