package com.upcn.service;

import com.upcn.domain.Etapa;
import java.util.List;

/**
 * Service Interface for managing Etapa.
 */
public interface EtapaService {

    /**
     * Save a etapa.
     *
     * @param etapa the entity to save
     * @return the persisted entity
     */
    Etapa save(Etapa etapa);

    /**
     * Get all the etapas.
     *
     * @return the list of entities
     */
    List<Etapa> findAll();

    /**
     * Get the "id" etapa.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Etapa findOne(Long id);

    /**
     * Delete the "id" etapa.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
