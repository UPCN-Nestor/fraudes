package com.upcn.service;

import com.upcn.domain.Inspeccion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;
import java.util.Date;

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
    Iterable<Inspeccion> findAll();

    Map<String, List<Object[]>> byTipoTrabajo(Long etapa_id);
    
    Map<String, List<Object[]>> byAnomalia(Long etapa_id);
    
    Map<String, List<Object[]>> byFecha(Long etapa_id);
    
    List<Object[]> byEtapaDesdeHasta(Long etapa_id, String desde, String hasta);
    
    List<Object[]> materialesByEtapaDesdeHasta(Long etapa_id, String desde, String hasta);

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
