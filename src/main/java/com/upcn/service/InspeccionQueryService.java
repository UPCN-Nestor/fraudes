package com.upcn.service;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.upcn.domain.Inspeccion;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.InspeccionRepository;
import com.upcn.service.dto.InspeccionCriteria;


/**
 * Service for executing complex queries for Inspeccion entities in the database.
 * The main input is a {@link InspeccionCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Inspeccion} or a {@link Page} of {@link Inspeccion} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InspeccionQueryService extends QueryService<Inspeccion> {

    private final Logger log = LoggerFactory.getLogger(InspeccionQueryService.class);


    private final InspeccionRepository inspeccionRepository;

    public InspeccionQueryService(InspeccionRepository inspeccionRepository) {
        this.inspeccionRepository = inspeccionRepository;
    }

    /**
     * Return a {@link List} of {@link Inspeccion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Inspeccion> findByCriteria(InspeccionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Inspeccion> specification = createSpecification(criteria);
        return inspeccionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Inspeccion} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inspeccion> findByCriteria(InspeccionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Inspeccion> specification = createSpecification(criteria);
        return inspeccionRepository.findAll(specification, page);
    }

    /**
     * Function to convert InspeccionCriteria to a {@link Specifications}
     */
    private Specifications<Inspeccion> createSpecification(InspeccionCriteria criteria) {
        Specifications<Inspeccion> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Inspeccion_.id));
            }
            if (criteria.getOrden() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOrden(), Inspeccion_.orden));
            }
            if (criteria.getFecha() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFecha(), Inspeccion_.fecha));
            }
            if (criteria.getObservaciones() != null) {
                specification = specification.and(buildStringSpecification(criteria.getObservaciones(), Inspeccion_.observaciones));
            }
            if (criteria.getDeshabitada() != null) {
                specification = specification.and(buildSpecification(criteria.getDeshabitada(), Inspeccion_.deshabitada));
            }
            if (criteria.getAnomaliaMedidorId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getAnomaliaMedidorId(), Inspeccion_.anomaliaMedidors, Anomalia_.id));
            }
            if (criteria.getTrabajoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTrabajoId(), Inspeccion_.trabajos, Trabajo_.id));
            }
            if (criteria.getInmuebleId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInmuebleId(), Inspeccion_.inmueble, Inmueble_.id));
            }
            if (criteria.getEtapaId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEtapaId(), Inspeccion_.etapa, Etapa_.id));
            }
            if (criteria.getEstadoId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getEstadoId(), Inspeccion_.estado, Estado_.id));
            }
            if (criteria.getTipoInmuebleId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getTipoInmuebleId(), Inspeccion_.tipoInmueble, TipoInmueble_.id));
            }
        }
        return specification;
    }

}
