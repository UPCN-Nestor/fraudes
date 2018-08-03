package com.upcn.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Insumo.
 */
@Entity
@Table(name = "insumo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Insumo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cantidad")
    private Float cantidad;

    @ManyToOne
    private Trabajo trabajo;

    @ManyToOne
    private Material material;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getCantidad() {
        return cantidad;
    }

    public Insumo cantidad(Float cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public void setCantidad(Float cantidad) {
        this.cantidad = cantidad;
    }

    public Trabajo getTrabajo() {
        return trabajo;
    }

    public Insumo trabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
        return this;
    }

    public void setTrabajo(Trabajo trabajo) {
        this.trabajo = trabajo;
    }

    public Material getMaterial() {
        return material;
    }

    public Insumo material(Material material) {
        this.material = material;
        return this;
    }

    public void setMaterial(Material material) {
        this.material = material;
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
        Insumo insumo = (Insumo) o;
        if (insumo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), insumo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Insumo{" +
            "id=" + getId() +
            ", cantidad=" + getCantidad() +
            "}";
    }
}
