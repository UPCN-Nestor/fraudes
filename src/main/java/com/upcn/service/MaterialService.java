package com.upcn.service;

import com.upcn.domain.Material;
import java.util.List;

/**
 * Service Interface for managing Material.
 */
public interface MaterialService {

    /**
     * Save a material.
     *
     * @param material the entity to save
     * @return the persisted entity
     */
    Material save(Material material);

    /**
     * Get all the materials.
     *
     * @return the list of entities
     */
    List<Material> findAll();

    /**
     * Get the "id" material.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Material findOne(Long id);

    /**
     * Delete the "id" material.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
