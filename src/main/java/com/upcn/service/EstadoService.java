package com.upcn.service;

import com.upcn.domain.Estado;
import java.util.List;

/**
 * Service Interface for managing Estado.
 */
public interface EstadoService {

    /**
     * Save a estado.
     *
     * @param estado the entity to save
     * @return the persisted entity
     */
    Estado save(Estado estado);

    /**
     * Get all the estados.
     *
     * @return the list of entities
     */
    List<Estado> findAll();

    /**
     * Get the "id" estado.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Estado findOne(Long id);

    /**
     * Delete the "id" estado.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
