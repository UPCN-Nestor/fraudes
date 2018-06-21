package com.upcn.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;






/**
 * Criteria class for the Inmueble entity. This class is used in InmuebleResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /inmuebles?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InmuebleCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter calle;

    private StringFilter altura;

    private StringFilter piso;

    private StringFilter depto;

    private StringFilter anexo;

    private LongFilter inspeccionId;

    public InmuebleCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCalle() {
        return calle;
    }

    public void setCalle(StringFilter calle) {
        this.calle = calle;
    }

    public StringFilter getAltura() {
        return altura;
    }

    public void setAltura(StringFilter altura) {
        this.altura = altura;
    }

    public StringFilter getPiso() {
        return piso;
    }

    public void setPiso(StringFilter piso) {
        this.piso = piso;
    }

    public StringFilter getDepto() {
        return depto;
    }

    public void setDepto(StringFilter depto) {
        this.depto = depto;
    }

    public StringFilter getAnexo() {
        return anexo;
    }

    public void setAnexo(StringFilter anexo) {
        this.anexo = anexo;
    }

    public LongFilter getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(LongFilter inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    @Override
    public String toString() {
        return "InmuebleCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (calle != null ? "calle=" + calle + ", " : "") +
                (altura != null ? "altura=" + altura + ", " : "") +
                (piso != null ? "piso=" + piso + ", " : "") +
                (depto != null ? "depto=" + depto + ", " : "") +
                (anexo != null ? "anexo=" + anexo + ", " : "") +
                (inspeccionId != null ? "inspeccionId=" + inspeccionId + ", " : "") +
            "}";
    }

}
