package com.upcn.service;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specifications;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.upcn.domain.TipoInmueble;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.TipoInmuebleRepository;
import com.upcn.service.dto.TipoInmuebleCriteria;


/**
 * Service for executing complex queries for TipoInmueble entities in the database.
 * The main input is a {@link TipoInmuebleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TipoInmueble} or a {@link Page} of {@link TipoInmueble} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TipoInmuebleQueryService extends QueryService<TipoInmueble> {

    private final Logger log = LoggerFactory.getLogger(TipoInmuebleQueryService.class);


    private final TipoInmuebleRepository tipoInmuebleRepository;

    public TipoInmuebleQueryService(TipoInmuebleRepository tipoInmuebleRepository) {
        this.tipoInmuebleRepository = tipoInmuebleRepository;
    }

    /**
     * Return a {@link List} of {@link TipoInmueble} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TipoInmueble> findByCriteria(TipoInmuebleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<TipoInmueble> specification = createSpecification(criteria);
        return tipoInmuebleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link TipoInmueble} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TipoInmueble> findByCriteria(TipoInmuebleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<TipoInmueble> specification = createSpecification(criteria);
        return tipoInmuebleRepository.findAll(specification, page);
    }

    /**
     * Function to convert TipoInmuebleCriteria to a {@link Specifications}
     */
    private Specifications<TipoInmueble> createSpecification(TipoInmuebleCriteria criteria) {
        Specifications<TipoInmueble> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TipoInmueble_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), TipoInmueble_.descripcion));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), TipoInmueble_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
