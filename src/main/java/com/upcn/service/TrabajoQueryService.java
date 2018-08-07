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

import com.upcn.domain.Trabajo;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.TrabajoRepository;
import com.upcn.service.dto.TrabajoCriteria;


/**
 * Service for executing complex queries for Trabajo entities in the database.
 * The main input is a {@link TrabajoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Trabajo} or a {@link Page} of {@link Trabajo} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TrabajoQueryService extends QueryService<Trabajo> {

    private final Logger log = LoggerFactory.getLogger(TrabajoQueryService.class);


    private final TrabajoRepository trabajoRepository;

    public TrabajoQueryService(TrabajoRepository trabajoRepository) {
        this.trabajoRepository = trabajoRepository;
    }

    /**
     * Return a {@link List} of {@link Trabajo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Trabajo> findByCriteria(TrabajoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Trabajo> specification = createSpecification(criteria);
        return trabajoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Trabajo} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Trabajo> findByCriteria(TrabajoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Trabajo> specification = createSpecification(criteria);
        return trabajoRepository.findAll(specification, page);
    }

    /**
     * Function to convert TrabajoCriteria to a {@link Specifications}
     */
    private Specifications<Trabajo> createSpecification(TrabajoCriteria criteria) {
        Specifications<Trabajo> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Trabajo_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Trabajo_.descripcion));
            }
            if (criteria.getCosto() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCosto(), Trabajo_.costo));
            }
            if (criteria.getUsaMedidor() != null) {
                specification = specification.and(buildSpecification(criteria.getUsaMedidor(), Trabajo_.usaMedidor));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), Trabajo_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
