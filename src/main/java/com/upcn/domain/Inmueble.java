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
 * A Inmueble.
 */
@Entity
@Table(name = "inmueble")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Inmueble implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "calle")
    private String calle;

    @Column(name = "altura")
    private String altura;

    @Column(name = "piso")
    private String piso;

    @Column(name = "depto")
    private String depto;

    @Column(name = "anexo")
    private String anexo;

    @OneToMany(mappedBy = "inmueble")
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

    public String getCalle() {
        return calle;
    }

    public Inmueble calle(String calle) {
        this.calle = calle;
        return this;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getAltura() {
        return altura;
    }

    public Inmueble altura(String altura) {
        this.altura = altura;
        return this;
    }

    public void setAltura(String altura) {
        this.altura = altura;
    }

    public String getPiso() {
        return piso;
    }

    public Inmueble piso(String piso) {
        this.piso = piso;
        return this;
    }

    public void setPiso(String piso) {
        this.piso = piso;
    }

    public String getDepto() {
        return depto;
    }

    public Inmueble depto(String depto) {
        this.depto = depto;
        return this;
    }

    public void setDepto(String depto) {
        this.depto = depto;
    }

    public String getAnexo() {
        return anexo;
    }

    public Inmueble anexo(String anexo) {
        this.anexo = anexo;
        return this;
    }

    public void setAnexo(String anexo) {
        this.anexo = anexo;
    }

    public Set<Inspeccion> getInspeccions() {
        return inspeccions;
    }

    public Inmueble inspeccions(Set<Inspeccion> inspeccions) {
        this.inspeccions = inspeccions;
        return this;
    }

    public Inmueble addInspeccion(Inspeccion inspeccion) {
        this.inspeccions.add(inspeccion);
        inspeccion.setInmueble(this);
        return this;
    }

    public Inmueble removeInspeccion(Inspeccion inspeccion) {
        this.inspeccions.remove(inspeccion);
        inspeccion.setInmueble(null);
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
        Inmueble inmueble = (Inmueble) o;
        if (inmueble.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), inmueble.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Inmueble{" +
            "id=" + getId() +
            ", calle='" + getCalle() + "'" +
            ", altura='" + getAltura() + "'" +
            ", piso='" + getPiso() + "'" +
            ", depto='" + getDepto() + "'" +
            ", anexo='" + getAnexo() + "'" +
            "}";
    }
}
