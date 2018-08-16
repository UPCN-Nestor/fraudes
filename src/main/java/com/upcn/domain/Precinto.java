package com.upcn.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Precinto.
 */
@Entity
@Table(name = "precinto")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Precinto implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero")
    private String numero;

    @Column(name = "color")
    private String color;

    @OneToOne(mappedBy = "precintoHabitaculo")
    @JsonIgnore
    private Inspeccion inspeccion;

    @OneToOne(mappedBy = "precintoBornera")
    @JsonIgnore
    private Inspeccion inspeccionBornera;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public Precinto numero(String numero) {
        this.numero = numero;
        return this;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getColor() {
        return color;
    }

    public Precinto color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Inspeccion getInspeccion() {
        return inspeccion;
    }

    public Precinto inspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccion;
        return this;
    }

    public void setInspeccion(Inspeccion inspeccion) {
        this.inspeccion = inspeccion;
    }

    public Inspeccion getInspeccionBornera() {
        return inspeccionBornera;
    }

    public Precinto inspeccionBornera(Inspeccion inspeccion) {
        this.inspeccionBornera = inspeccion;
        return this;
    }

    public void setInspeccionBornera(Inspeccion inspeccion) {
        this.inspeccionBornera = inspeccion;
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
        Precinto precinto = (Precinto) o;
        if (precinto.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), precinto.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Precinto{" +
            "id=" + getId() +
            ", numero='" + getNumero() + "'" +
            ", color='" + getColor() + "'" +
            "}";
    }
}
