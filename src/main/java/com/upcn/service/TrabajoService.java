package com.upcn.service;

import com.upcn.domain.Trabajo;
import java.util.List;

/**
 * Service Interface for managing Trabajo.
 */
public interface TrabajoService {

    /**
     * Save a trabajo.
     *
     * @param trabajo the entity to save
     * @return the persisted entity
     */
    Trabajo save(Trabajo trabajo);

    /**
     * Get all the trabajos.
     *
     * @return the list of entities
     */
    List<Trabajo> findAll();

    /**
     * Get the "id" trabajo.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Trabajo findOne(Long id);

    /**
     * Delete the "id" trabajo.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
