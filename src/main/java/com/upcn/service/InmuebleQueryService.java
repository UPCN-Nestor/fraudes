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

import com.upcn.domain.Inmueble;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.InmuebleRepository;
import com.upcn.service.dto.InmuebleCriteria;


/**
 * Service for executing complex queries for Inmueble entities in the database.
 * The main input is a {@link InmuebleCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Inmueble} or a {@link Page} of {@link Inmueble} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class InmuebleQueryService extends QueryService<Inmueble> {

    private final Logger log = LoggerFactory.getLogger(InmuebleQueryService.class);


    private final InmuebleRepository inmuebleRepository;

    public InmuebleQueryService(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = inmuebleRepository;
    }

    /**
     * Return a {@link List} of {@link Inmueble} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Inmueble> findByCriteria(InmuebleCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Inmueble> specification = createSpecification(criteria);
        return inmuebleRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Inmueble} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Inmueble> findByCriteria(InmuebleCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Inmueble> specification = createSpecification(criteria);
        return inmuebleRepository.findAll(specification, page);
    }

    /**
     * Function to convert InmuebleCriteria to a {@link Specifications}
     */
    private Specifications<Inmueble> createSpecification(InmuebleCriteria criteria) {
        Specifications<Inmueble> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Inmueble_.id));
            }
            if (criteria.getCalle() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCalle(), Inmueble_.calle));
            }
            if (criteria.getAltura() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAltura(), Inmueble_.altura));
            }
            if (criteria.getPiso() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPiso(), Inmueble_.piso));
            }
            if (criteria.getDepto() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDepto(), Inmueble_.depto));
            }
            if (criteria.getAnexo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnexo(), Inmueble_.anexo));
            }
            if (criteria.getInspeccionId() != null) {
                specification = specification.and(buildReferringEntitySpecification(criteria.getInspeccionId(), Inmueble_.inspeccions, Inspeccion_.id));
            }
        }
        return specification;
    }

}
