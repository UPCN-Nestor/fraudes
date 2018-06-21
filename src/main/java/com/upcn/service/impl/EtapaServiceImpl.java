package com.upcn.service.impl;

import com.upcn.service.EtapaService;
import com.upcn.domain.Etapa;
import com.upcn.repository.EtapaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Etapa.
 */
@Service
@Transactional
public class EtapaServiceImpl implements EtapaService {

    private final Logger log = LoggerFactory.getLogger(EtapaServiceImpl.class);

    private final EtapaRepository etapaRepository;

    public EtapaServiceImpl(EtapaRepository etapaRepository) {
        this.etapaRepository = etapaRepository;
    }

    /**
     * Save a etapa.
     *
     * @param etapa the entity to save
     * @return the persisted entity
     */
    @Override
    public Etapa save(Etapa etapa) {
        log.debug("Request to save Etapa : {}", etapa);
        return etapaRepository.save(etapa);
    }

    /**
     * Get all the etapas.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Etapa> findAll() {
        log.debug("Request to get all Etapas");
        return etapaRepository.findAll();
    }

    /**
     * Get one etapa by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Etapa findOne(Long id) {
        log.debug("Request to get Etapa : {}", id);
        return etapaRepository.findOne(id);
    }

    /**
     * Delete the etapa by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Etapa : {}", id);
        etapaRepository.delete(id);
    }
}
