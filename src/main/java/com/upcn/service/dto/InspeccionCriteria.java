package com.upcn.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;


import io.github.jhipster.service.filter.LocalDateFilter;



/**
 * Criteria class for the Inspeccion entity. This class is used in InspeccionResource to
 * receive all the possible filtering options from the Http GET request parameters.
 * For example the following could be a valid requests:
 * <code> /inspeccions?id.greaterThan=5&amp;attr1.contains=something&amp;attr2.specified=false</code>
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class InspeccionCriteria implements Serializable {
    private static final long serialVersionUID = 1L;


    private LongFilter id;

    private LongFilter orden;

    private LocalDateFilter fecha;

    private StringFilter observaciones;

    private BooleanFilter deshabitada;

    private LongFilter anomaliaMedidorId;

    private LongFilter trabajoId;

    private LongFilter inmuebleId;

    private LongFilter etapaId;

    private LongFilter estadoId;

    private LongFilter tipoInmuebleId;

    public InspeccionCriteria() {
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LongFilter getOrden() {
        return orden;
    }

    public void setOrden(LongFilter orden) {
        this.orden = orden;
    }

    public LocalDateFilter getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateFilter fecha) {
        this.fecha = fecha;
    }

    public StringFilter getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(StringFilter observaciones) {
        this.observaciones = observaciones;
    }

    public BooleanFilter getDeshabitada() {
        return deshabitada;
    }

    public void setDeshabitada(BooleanFilter deshabitada) {
        this.deshabitada = deshabitada;
    }

    public LongFilter getAnomaliaMedidorId() {
        return anomaliaMedidorId;
    }

    public void setAnomaliaMedidorId(LongFilter anomaliaMedidorId) {
        this.anomaliaMedidorId = anomaliaMedidorId;
    }

    public LongFilter getTrabajoId() {
        return trabajoId;
    }

    public void setTrabajoId(LongFilter trabajoId) {
        this.trabajoId = trabajoId;
    }

    public LongFilter getInmuebleId() {
        return inmuebleId;
    }

    public void setInmuebleId(LongFilter inmuebleId) {
        this.inmuebleId = inmuebleId;
    }

    public LongFilter getEtapaId() {
        return etapaId;
    }

    public void setEtapaId(LongFilter etapaId) {
        this.etapaId = etapaId;
    }

    public LongFilter getEstadoId() {
        return estadoId;
    }

    public void setEstadoId(LongFilter estadoId) {
        this.estadoId = estadoId;
    }

    public LongFilter getTipoInmuebleId() {
        return tipoInmuebleId;
    }

    public void setTipoInmuebleId(LongFilter tipoInmuebleId) {
        this.tipoInmuebleId = tipoInmuebleId;
    }

    @Override
    public String toString() {
        return "InspeccionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orden != null ? "orden=" + orden + ", " : "") +
                (fecha != null ? "fecha=" + fecha + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (deshabitada != null ? "deshabitada=" + deshabitada + ", " : "") +
                (anomaliaMedidorId != null ? "anomaliaMedidorId=" + anomaliaMedidorId + ", " : "") +
                (trabajoId != null ? "trabajoId=" + trabajoId + ", " : "") +
                (inmuebleId != null ? "inmuebleId=" + inmuebleId + ", " : "") +
                (etapaId != null ? "etapaId=" + etapaId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (tipoInmuebleId != null ? "tipoInmuebleId=" + tipoInmuebleId + ", " : "") +
            "}";
    }

}
