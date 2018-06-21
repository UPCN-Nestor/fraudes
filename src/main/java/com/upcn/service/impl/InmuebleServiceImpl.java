package com.upcn.service.impl;

import com.upcn.service.InmuebleService;
import com.upcn.domain.Inmueble;
import com.upcn.repository.InmuebleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Inmueble.
 */
@Service
@Transactional
public class InmuebleServiceImpl implements InmuebleService {

    private final Logger log = LoggerFactory.getLogger(InmuebleServiceImpl.class);

    private final InmuebleRepository inmuebleRepository;

    public InmuebleServiceImpl(InmuebleRepository inmuebleRepository) {
        this.inmuebleRepository = inmuebleRepository;
    }

    /**
     * Save a inmueble.
     *
     * @param inmueble the entity to save
     * @return the persisted entity
     */
    @Override
    public Inmueble save(Inmueble inmueble) {
        log.debug("Request to save Inmueble : {}", inmueble);
        return inmuebleRepository.save(inmueble);
    }

    /**
     * Get all the inmuebles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Inmueble> findAll() {
        log.debug("Request to get all Inmuebles");
        return inmuebleRepository.findAll();
    }

    /**
     * Get one inmueble by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Inmueble findOne(Long id) {
        log.debug("Request to get Inmueble : {}", id);
        return inmuebleRepository.findOne(id);
    }

    /**
     * Delete the inmueble by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inmueble : {}", id);
        inmuebleRepository.delete(id);
    }
}
