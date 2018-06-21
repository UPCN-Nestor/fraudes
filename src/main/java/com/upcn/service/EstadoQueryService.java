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

import com.upcn.domain.Estado;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.EstadoRepository;
import com.upcn.service.dto.EstadoCriteria;


/**
 * Service for executing complex queries for Estado entities in the database.
 * The main input is a {@link EstadoCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Estado} or a {@link Page} of {@link Estado} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EstadoQueryService extends QueryService<Estado> {

    private final Logger log = LoggerFactory.getLogger(EstadoQueryService.class);


    private final EstadoRepository estadoRepository;

    public EstadoQueryService(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Return a {@link List} of {@link Estado} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Estado> findByCriteria(EstadoCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Estado> specification = createSpecification(criteria);
        return estadoRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Estado} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Estado> findByCriteria(EstadoCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Estado> specification = createSpecification(criteria);
        return estadoRepository.findAll(specification, page);
    }

    /**
     * Function to convert EstadoCriteria to a {@link Specifications}
     */
    private Specifications<Estado> createSpecification(EstadoCriteria criteria) {
        Specifications<Estado> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Estado_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Estado_.descripcion));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), Estado_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
