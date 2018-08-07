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

import com.upcn.domain.Material;
import com.upcn.domain.*; // for static metamodels
import com.upcn.repository.MaterialRepository;
import com.upcn.service.dto.MaterialCriteria;


/**
 * Service for executing complex queries for Material entities in the database.
 * The main input is a {@link MaterialCriteria} which get's converted to {@link Specifications},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Material} or a {@link Page} of {@link Material} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MaterialQueryService extends QueryService<Material> {

    private final Logger log = LoggerFactory.getLogger(MaterialQueryService.class);


    private final MaterialRepository materialRepository;

    public MaterialQueryService(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    /**
     * Return a {@link List} of {@link Material} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Material> findByCriteria(MaterialCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specifications<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Material} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Material> findByCriteria(MaterialCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specifications<Material> specification = createSpecification(criteria);
        return materialRepository.findAll(specification, page);
    }

    /**
     * Function to convert MaterialCriteria to a {@link Specifications}
     */
    private Specifications<Material> createSpecification(MaterialCriteria criteria) {
        Specifications<Material> specification = Specifications.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Material_.id));
            }
            if (criteria.getDescripcion() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescripcion(), Material_.descripcion));
            }
            if (criteria.getCodigo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCodigo(), Material_.codigo));
            }
        }
        return specification;
    }

}
