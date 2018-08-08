package com.upcn.service.dto;

import java.io.Serializable;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

import io.github.jhipster.service.filter.InstantFilter;




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

    private StringFilter observaciones;

    private BooleanFilter deshabitada;

    private StringFilter usuario;

    private InstantFilter fechahora;

    private StringFilter medidorInstalado;

    private FloatFilter ultimaLectura;

    private StringFilter medidorRetirado;

    private IntegerFilter socio;

    private IntegerFilter suministro;

    private StringFilter nombre;

    private StringFilter tarifa;

    private FloatFilter mtsCable;

    private FloatFilter lecturaNuevo;

    private LongFilter anomaliaMedidorId;

    private LongFilter trabajoId;

    private LongFilter inmuebleId;

    private LongFilter etapaId;

    private LongFilter estadoId;

    private LongFilter tipoInmuebleId;

    private LongFilter medidorNuevoId;

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

    public StringFilter getUsuario() {
        return usuario;
    }

    public void setUsuario(StringFilter usuario) {
        this.usuario = usuario;
    }

    public InstantFilter getFechahora() {
        return fechahora;
    }

    public void setFechahora(InstantFilter fechahora) {
        this.fechahora = fechahora;
    }

    public StringFilter getMedidorInstalado() {
        return medidorInstalado;
    }

    public void setMedidorInstalado(StringFilter medidorInstalado) {
        this.medidorInstalado = medidorInstalado;
    }

    public FloatFilter getUltimaLectura() {
        return ultimaLectura;
    }

    public void setUltimaLectura(FloatFilter ultimaLectura) {
        this.ultimaLectura = ultimaLectura;
    }

    public StringFilter getMedidorRetirado() {
        return medidorRetirado;
    }

    public void setMedidorRetirado(StringFilter medidorRetirado) {
        this.medidorRetirado = medidorRetirado;
    }

    public IntegerFilter getSocio() {
        return socio;
    }

    public void setSocio(IntegerFilter socio) {
        this.socio = socio;
    }

    public IntegerFilter getSuministro() {
        return suministro;
    }

    public void setSuministro(IntegerFilter suministro) {
        this.suministro = suministro;
    }

    public StringFilter getNombre() {
        return nombre;
    }

    public void setNombre(StringFilter nombre) {
        this.nombre = nombre;
    }

    public StringFilter getTarifa() {
        return tarifa;
    }

    public void setTarifa(StringFilter tarifa) {
        this.tarifa = tarifa;
    }

    public FloatFilter getMtsCable() {
        return mtsCable;
    }

    public void setMtsCable(FloatFilter mtsCable) {
        this.mtsCable = mtsCable;
    }

    public FloatFilter getLecturaNuevo() {
        return lecturaNuevo;
    }

    public void setLecturaNuevo(FloatFilter lecturaNuevo) {
        this.lecturaNuevo = lecturaNuevo;
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

    public LongFilter getMedidorNuevoId() {
        return medidorNuevoId;
    }

    public void setMedidorNuevoId(LongFilter medidorNuevoId) {
        this.medidorNuevoId = medidorNuevoId;
    }

    @Override
    public String toString() {
        return "InspeccionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (orden != null ? "orden=" + orden + ", " : "") +
                (observaciones != null ? "observaciones=" + observaciones + ", " : "") +
                (deshabitada != null ? "deshabitada=" + deshabitada + ", " : "") +
                (usuario != null ? "usuario=" + usuario + ", " : "") +
                (fechahora != null ? "fechahora=" + fechahora + ", " : "") +
                (medidorInstalado != null ? "medidorInstalado=" + medidorInstalado + ", " : "") +
                (ultimaLectura != null ? "ultimaLectura=" + ultimaLectura + ", " : "") +
                (medidorRetirado != null ? "medidorRetirado=" + medidorRetirado + ", " : "") +
                (socio != null ? "socio=" + socio + ", " : "") +
                (suministro != null ? "suministro=" + suministro + ", " : "") +
                (nombre != null ? "nombre=" + nombre + ", " : "") +
                (tarifa != null ? "tarifa=" + tarifa + ", " : "") +
                (mtsCable != null ? "mtsCable=" + mtsCable + ", " : "") +
                (lecturaNuevo != null ? "lecturaNuevo=" + lecturaNuevo + ", " : "") +
                (anomaliaMedidorId != null ? "anomaliaMedidorId=" + anomaliaMedidorId + ", " : "") +
                (trabajoId != null ? "trabajoId=" + trabajoId + ", " : "") +
                (inmuebleId != null ? "inmuebleId=" + inmuebleId + ", " : "") +
                (etapaId != null ? "etapaId=" + etapaId + ", " : "") +
                (estadoId != null ? "estadoId=" + estadoId + ", " : "") +
                (tipoInmuebleId != null ? "tipoInmuebleId=" + tipoInmuebleId + ", " : "") +
                (medidorNuevoId != null ? "medidorNuevoId=" + medidorNuevoId + ", " : "") +
            "}";
    }

}
