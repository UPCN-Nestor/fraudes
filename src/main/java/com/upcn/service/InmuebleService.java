package com.upcn.service;

import com.upcn.domain.Inmueble;
import java.util.List;

/**
 * Service Interface for managing Inmueble.
 */
public interface InmuebleService {

    /**
     * Save a inmueble.
     *
     * @param inmueble the entity to save
     * @return the persisted entity
     */
    Inmueble save(Inmueble inmueble);

    /**
     * Get all the inmuebles.
     *
     * @return the list of entities
     */
    List<Inmueble> findAll();

    /**
     * Get the "id" inmueble.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Inmueble findOne(Long id);

    /**
     * Delete the "id" inmueble.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
