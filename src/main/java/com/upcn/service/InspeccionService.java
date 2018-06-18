package com.upcn.service;

import com.upcn.domain.Inspeccion;
import com.upcn.repository.InspeccionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Inspeccion.
 */
@Service
@Transactional
public class InspeccionService {

    private final Logger log = LoggerFactory.getLogger(InspeccionService.class);

    private final InspeccionRepository inspeccionRepository;

    public InspeccionService(InspeccionRepository inspeccionRepository) {
        this.inspeccionRepository = inspeccionRepository;
    }

    /**
     * Save a inspeccion.
     *
     * @param inspeccion the entity to save
     * @return the persisted entity
     */
    public Inspeccion save(Inspeccion inspeccion) {
        log.debug("Request to save Inspeccion : {}", inspeccion);
        return inspeccionRepository.save(inspeccion);
    }

    /**
     * Get all the inspeccions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Inspeccion> findAll(Pageable pageable) {
        log.debug("Request to get all Inspeccions");
        return inspeccionRepository.findAll(pageable);
    }

    /**
     * Get one inspeccion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Transactional(readOnly = true)
    public Inspeccion findOne(Long id) {
        log.debug("Request to get Inspeccion : {}", id);
        return inspeccionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the inspeccion by id.
     *
     * @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Inspeccion : {}", id);
        inspeccionRepository.delete(id);
    }
}
