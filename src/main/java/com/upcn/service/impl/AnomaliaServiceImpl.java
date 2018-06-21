package com.upcn.service.impl;

import com.upcn.service.AnomaliaService;
import com.upcn.domain.Anomalia;
import com.upcn.repository.AnomaliaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service Implementation for managing Anomalia.
 */
@Service
@Transactional
public class AnomaliaServiceImpl implements AnomaliaService {

    private final Logger log = LoggerFactory.getLogger(AnomaliaServiceImpl.class);

    private final AnomaliaRepository anomaliaRepository;

    public AnomaliaServiceImpl(AnomaliaRepository anomaliaRepository) {
        this.anomaliaRepository = anomaliaRepository;
    }

    /**
     * Save a anomalia.
     *
     * @param anomalia the entity to save
     * @return the persisted entity
     */
    @Override
    public Anomalia save(Anomalia anomalia) {
        log.debug("Request to save Anomalia : {}", anomalia);
        return anomaliaRepository.save(anomalia);
    }

    /**
     * Get all the anomalias.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Anomalia> findAll() {
        log.debug("Request to get all Anomalias");
        return anomaliaRepository.findAll();
    }

    /**
     * Get one anomalia by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Anomalia findOne(Long id) {
        log.debug("Request to get Anomalia : {}", id);
        return anomaliaRepository.findOne(id);
    }

    /**
     * Delete the anomalia by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Anomalia : {}", id);
        anomaliaRepository.delete(id);
    }
}
