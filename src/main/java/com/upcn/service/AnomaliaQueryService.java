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

import com.upcn.domain.Anomalia;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.AnomaliaRepository;
import com.upcn.service.dto.AnomaliaCriteria;


/**
 * Service for executing complex queries for Anomalia entities in the database.
 * The main input is a {@link AnomaliaCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Anomalia} or a {@link Page} of {@link Anomalia} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AnomaliaQueryService extends QueryService<Anomalia> {

    private final Logger log = LoggerFactory.getLogger(AnomaliaQueryService.class);


    private final AnomaliaRepository anomaliaRepository;

    public AnomaliaQueryService(AnomaliaRepository anomaliaRepository) {
        this.anomaliaRepository = anomaliaRepository;
    }

    /**
     * Return a {@link List} of {@link Anomalia} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Anomalia> findByCriteria(AnomaliaCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Anomalia> specification = createSpecification(criteria);
        return anomaliaRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Anomalia} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Anomalia> findByCriteria(AnomaliaCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Anomalia> specification = createSpecification(criteria);
        return anomaliaRepository.findAll(specification, page);
    }

    /**
     * Function to convert AnomaliaCriteria to a {@link Specifications}
     */
    private Specifications<Anomalia> createSpecification(AnomaliaCriteria criteria) {
        Specifications<Anomalia> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Anomalia_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Anomalia_.descripcion));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), Anomalia_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
