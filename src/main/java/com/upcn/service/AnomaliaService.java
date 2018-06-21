package com.upcn.service;

import com.upcn.domain.Anomalia;
import java.util.List;

/**
 * Service Interface for managing Anomalia.
 */
public interface AnomaliaService {

    /**
     * Save a anomalia.
     *
     * @param anomalia the entity to save
     * @return the persisted entity
     */
    Anomalia save(Anomalia anomalia);

    /**
     * Get all the anomalias.
     *
     * @return the list of entities
     */
    List<Anomalia> findAll();

    /**
     * Get the "id" anomalia.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Anomalia findOne(Long id);

    /**
     * Delete the "id" anomalia.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
