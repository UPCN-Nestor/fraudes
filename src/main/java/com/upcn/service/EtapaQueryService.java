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

import com.upcn.domain.Etapa;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.EtapaRepository;
import com.upcn.service.dto.EtapaCriteria;


/**
 * Service for executing complex queries for Etapa entities in the database.
 * The main input is a {@link EtapaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Etapa} or a {@link Page} of {@link Etapa} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class EtapaQueryService extends QueryService<Etapa> {

    private final Logger log = LoggerFactory.getLogger(EtapaQueryService.class);


    private final EtapaRepository etapaRepository;

    public EtapaQueryService(EtapaRepository etapaRepository) {
        this.etapaRepository = etapaRepository;
    }

    /**
     * Return a {@link List} of {@link Etapa} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Etapa> findByCriteria(EtapaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Etapa> specification = createSpecification(criteria);
        return etapaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Etapa} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Etapa> findByCriteria(EtapaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Etapa> specification = createSpecification(criteria);
        return etapaRepository.findAll(specification, page);
    }

    /**
     * Function to convert EtapaCriteria to a {@link Specifications}
     */
    private Specifications<Etapa> createSpecification(EtapaCriteria criteria) {
        Specifications<Etapa> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Etapa_.id));
            }
            if (criteria.getNumero() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNumero(), Etapa_.numero));
            }
            if (criteria.getDescripcionCorta() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcionCorta(), Etapa_.descripcionCorta));
            }
            if (criteria.getDescripcionLarga() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcionLarga(), Etapa_.descripcionLarga));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), Etapa_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
