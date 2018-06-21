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
 * Criteria class for the Etapa entity. This class is used in EtapaResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /etapas?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EtapaCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter numero;

    private StringFilter descripcionCorta;

    private StringFilter descripcionLarga;

    private LongFilter inspeccionId;

    public EtapaCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getNumero() {
        return numero;
    }

    public void setNumero(LongFilter numero) {
        this.numero = numero;
    }

    public StringFilter getDescripcionCorta() {
        return descripcionCorta;
    }

    public void setDescripcionCorta(StringFilter descripcionCorta) {
        this.descripcionCorta = descripcionCorta;
    }

    public StringFilter getDescripcionLarga() {
        return descripcionLarga;
    }

    public void setDescripcionLarga(StringFilter descripcionLarga) {
        this.descripcionLarga = descripcionLarga;
    }

    public LongFilter getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(LongFilter inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    @Override
    public String toString() {
        return "EtapaCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (numero != null ? "numero=" + numero + ", " : "") +
                (descripcionCorta != null ? "descripcionCorta=" + descripcionCorta + ", " : "") +
                (descripcionLarga != null ? "descripcionLarga=" + descripcionLarga + ", " : "") +
                (inspeccionId != null ? "inspeccionId=" + inspeccionId + ", " : "") +
            "}";
    }

}
