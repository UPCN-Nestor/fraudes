package com.upcn.service;

import com.upcn.domain.Inspeccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Inspeccion.
 */
public interface InspeccionService {

    /**
     * Save a inspeccion.
     *
     * @param inspeccion the entity to save
     * @return the persisted entity
     */
    Inspeccion save(Inspeccion inspeccion);

    /**
     * Get all the inspeccions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Inspeccion> findAll(Pageable pageable);

    /**
     * Get the "id" inspeccion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Inspeccion findOne(Long id);

    /**
     * Delete the "id" inspeccion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
