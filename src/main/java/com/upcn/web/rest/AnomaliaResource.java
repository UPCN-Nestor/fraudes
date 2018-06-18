package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Anomalia;

import com.upcn.repository.AnomaliaRepository;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Anomalia.
 */
@RestController
@RequestMapping("/api")
public class AnomaliaResource {

    private final Logger log = LoggerFactory.getLogger(AnomaliaResource.class);

    private static final String ENTITY_NAME = "anomalia";

    private final AnomaliaRepository anomaliaRepository;

    public AnomaliaResource(AnomaliaRepository anomaliaRepository) {
        this.anomaliaRepository = anomaliaRepository;
    }

    /**
     * POST  /anomalias : Create a new anomalia.
     *
     * @param anomalia the anomalia to create
     * @return the ResponseEntity with status 201 (Created) and with body the new anomalia, or with status 400 (Bad Request) if the anomalia has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/anomalias")
    @Timed
    public ResponseEntity<Anomalia> createAnomalia(@RequestBody Anomalia anomalia) throws URISyntaxException {
        log.debug("REST request to save Anomalia : {}", anomalia);
        if (anomalia.getId() != null) {
            throw new BadRequestAlertException("A new anomalia cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Anomalia result = anomaliaRepository.save(anomalia);
        return ResponseEntity.created(new URI("/api/anomalias/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /anomalias : Updates an existing anomalia.
     *
     * @param anomalia the anomalia to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated anomalia,
     * or with status 400 (Bad Request) if the anomalia is not valid,
     * or with status 500 (Internal Server Error) if the anomalia couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/anomalias")
    @Timed
    public ResponseEntity<Anomalia> updateAnomalia(@RequestBody Anomalia anomalia) throws URISyntaxException {
        log.debug("REST request to update Anomalia : {}", anomalia);
        if (anomalia.getId() == null) {
            return createAnomalia(anomalia);
        }
        Anomalia result = anomaliaRepository.save(anomalia);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, anomalia.getId().toString()))
            .body(result);
    }

    /**
     * GET  /anomalias : get all the anomalias.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of anomalias in body
     */
    @GetMapping("/anomalias")
    @Timed
    public List<Anomalia> getAllAnomalias() {
        log.debug("REST request to get all Anomalias");
        return anomaliaRepository.findAll();
        }

    /**
     * GET  /anomalias/:id : get the "id" anomalia.
     *
     * @param id the id of the anomalia to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the anomalia, or with status 404 (Not Found)
     */
    @GetMapping("/anomalias/{id}")
    @Timed
    public ResponseEntity<Anomalia> getAnomalia(@PathVariable Long id) {
        log.debug("REST request to get Anomalia : {}", id);
        Anomalia anomalia = anomaliaRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(anomalia));
    }

    /**
     * DELETE  /anomalias/:id : delete the "id" anomalia.
     *
     * @param id the id of the anomalia to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/anomalias/{id}")
    @Timed
    public ResponseEntity<Void> deleteAnomalia(@PathVariable Long id) {
        log.debug("REST request to delete Anomalia : {}", id);
        anomaliaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
