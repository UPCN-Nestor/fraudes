package com.upcn.service.impl;

import com.upcn.service.EstadoService;
import com.upcn.domain.Estado;
import com.upcn.repository.EstadoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Estado.
 */
@Service
@Transactional
public class EstadoServiceImpl implements EstadoService {

    private final Logger log = LoggerFactory.getLogger(EstadoServiceImpl.class);

    private final EstadoRepository estadoRepository;

    public EstadoServiceImpl(EstadoRepository estadoRepository) {
        this.estadoRepository = estadoRepository;
    }

    /**
     * Save a estado.
     *
     * @param estado the entity to save
     * @return the persisted entity
     */
    @Override
    public Estado save(Estado estado) {
        log.debug("Request to save Estado : {}", estado);
        return estadoRepository.save(estado);
    }

    /**
     * Get all the estados.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Estado> findAll() {
        log.debug("Request to get all Estados");
        return estadoRepository.findAll();
    }

    /**
     * Get one estado by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Estado findOne(Long id) {
        log.debug("Request to get Estado : {}", id);
        return estadoRepository.findOne(id);
    }

    /**
     * Delete the estado by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Estado : {}", id);
        estadoRepository.delete(id);
    }
}
