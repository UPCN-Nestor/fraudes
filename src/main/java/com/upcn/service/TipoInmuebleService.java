package com.upcn.service;

import com.upcn.domain.TipoInmueble;
import java.util.List;

/**
 * Service Interface for managing TipoInmueble.
 */
public interface TipoInmuebleService {

    /**
     * Save a tipoInmueble.
     *
     * @param tipoInmueble the entity to save
     * @return the persisted entity
     */
    TipoInmueble save(TipoInmueble tipoInmueble);

    /**
     * Get all the tipoInmuebles.
     *
     * @return the list of entities
     */
    List<TipoInmueble> findAll();

    /**
     * Get the "id" tipoInmueble.
     *
     * @param id the id of the entity
     * @return the entity
     */
    TipoInmueble findOne(Long id);

    /**
     * Delete the "id" tipoInmueble.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
