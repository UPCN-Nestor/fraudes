package com.upcn.service.impl;

import com.upcn.service.TipoInmuebleService;
import com.upcn.domain.TipoInmueble;
import com.upcn.repository.TipoInmuebleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing TipoInmueble.
 */
@Service
@Transactional
public class TipoInmuebleServiceImpl implements TipoInmuebleService {

    private final Logger log = LoggerFactory.getLogger(TipoInmuebleServiceImpl.class);

    private final TipoInmuebleRepository tipoInmuebleRepository;

    public TipoInmuebleServiceImpl(TipoInmuebleRepository tipoInmuebleRepository) {
        this.tipoInmuebleRepository = tipoInmuebleRepository;
    }

    /**
     * Save a tipoInmueble.
     *
     * @param tipoInmueble the entity to save
     * @return the persisted entity
     */
    @Override
    public TipoInmueble save(TipoInmueble tipoInmueble) {
        log.debug("Request to save TipoInmueble : {}", tipoInmueble);
        return tipoInmuebleRepository.save(tipoInmueble);
    }

    /**
     * Get all the tipoInmuebles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<TipoInmueble> findAll() {
        log.debug("Request to get all TipoInmuebles");
        return tipoInmuebleRepository.findAll();
    }

    /**
     * Get one tipoInmueble by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public TipoInmueble findOne(Long id) {
        log.debug("Request to get TipoInmueble : {}", id);
        return tipoInmuebleRepository.findOne(id);
    }

    /**
     * Delete the tipoInmueble by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TipoInmueble : {}", id);
        tipoInmuebleRepository.delete(id);
    }
}
