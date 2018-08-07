package com.upcn.service.impl;

import com.upcn.service.InspeccionService;
import com.upcn.domain.Inspeccion;
import com.upcn.repository.EstadoRepository;
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
public class InspeccionServiceImpl implements InspeccionService {

    private final Logger log = LoggerFactory.getLogger(InspeccionServiceImpl.class);

    private final InspeccionRepository inspeccionRepository;
    private final EstadoRepository estadoRepository;

    public InspeccionServiceImpl(InspeccionRepository inspeccionRepository, EstadoRepository estadoRepository) {
        this.inspeccionRepository = inspeccionRepository;
        this.estadoRepository = estadoRepository;
    }

    /**
     * Save a inspeccion.
     *
     * @param inspeccion the entity to save
     * @return the persisted entity
     */
    @Override
    public Inspeccion save(Inspeccion inspeccion) {
        log.debug("Request to save Inspeccion : {}", inspeccion);
        if(inspeccion.getOrden()==null) {
            long max = 0;
            for(Inspeccion i : inspeccionRepository.findAll()) {
                if(i.getEtapa().getId() == inspeccion.getEtapa().getId() && i.getOrden() > max)
                    max = i.getOrden();
            }
            inspeccion.setOrden(max+1);
        }
        if(inspeccion.getEstado()==null)
            inspeccion.setEstado(estadoRepository.findOne(1l));

        return inspeccionRepository.save(inspeccion);
    }

    /**
     * Get all the inspeccions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
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
    @Override
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
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inspeccion : {}", id);
        inspeccionRepository.delete(id);
    }
}
