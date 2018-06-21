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
 * Criteria class for the Trabajo entity. This class is used in TrabajoResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /trabajos?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class TrabajoCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private StringFilter descripcion;

    private FloatFilter costo;

    private LongFilter materialId;

    private LongFilter inspeccionId;

    public TrabajoCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(StringFilter descripcion) {
        this.descripcion = descripcion;
    }

    public FloatFilter getCosto() {
        return costo;
    }

    public void setCosto(FloatFilter costo) {
        this.costo = costo;
    }

    public LongFilter getMaterialId() {
        return materialId;
    }

    public void setMaterialId(LongFilter materialId) {
        this.materialId = materialId;
    }

    public LongFilter getInspeccionId() {
        return inspeccionId;
    }

    public void setInspeccionId(LongFilter inspeccionId) {
        this.inspeccionId = inspeccionId;
    }

    @Override
    public String toString() {
        return "TrabajoCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (descripcion != null ? "descripcion=" + descripcion + ", " : "") +
                (costo != null ? "costo=" + costo + ", " : "") +
                (materialId != null ? "materialId=" + materialId + ", " : "") +
                (inspeccionId != null ? "inspeccionId=" + inspeccionId + ", " : "") +
            "}";
    }

}
