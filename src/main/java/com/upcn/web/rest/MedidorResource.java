package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Medidor;

import com.upcn.repository.MedidorRepository;
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
 * REST controller for managing Medidor.
 */
@RestController
@RequestMapping("/api")
public class MedidorResource {

    private final Logger log = LoggerFactory.getLogger(MedidorResource.class);

    private static final String ENTITY_NAME = "medidor";

    private final MedidorRepository medidorRepository;

    public MedidorResource(MedidorRepository medidorRepository) {
        this.medidorRepository = medidorRepository;
    }

    /**
     * POST  /medidors : Create a new medidor.
     *
     * @param medidor the medidor to create
     * @return the ResponseEntity with status 201 (Created) and with body the new medidor, or with status 400 (Bad Request) if the medidor has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/medidors")
    @Timed
    public ResponseEntity<Medidor> createMedidor(@RequestBody Medidor medidor) throws URISyntaxException {
        log.debug("REST request to save Medidor : {}", medidor);
        if (medidor.getId() != null) {
            throw new BadRequestAlertException("A new medidor cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medidor result = medidorRepository.save(medidor);
        return ResponseEntity.created(new URI("/api/medidors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /medidors : Updates an existing medidor.
     *
     * @param medidor the medidor to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated medidor,
     * or with status 400 (Bad Request) if the medidor is not valid,
     * or with status 500 (Internal Server Error) if the medidor couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/medidors")
    @Timed
    public ResponseEntity<Medidor> updateMedidor(@RequestBody Medidor medidor) throws URISyntaxException {
        log.debug("REST request to update Medidor : {}", medidor);
        if (medidor.getId() == null) {
            return createMedidor(medidor);
        }
        Medidor result = medidorRepository.save(medidor);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, medidor.getId().toString()))
            .body(result);
    }

    /**
     * GET  /medidors : get all the medidors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of medidors in body
     */
    @GetMapping("/medidors")
    @Timed
    public List<Medidor> getAllMedidors() {
        log.debug("REST request to get all Medidors");
        return medidorRepository.findAll();
        }

    /**
     * GET  /medidors/:id : get the "id" medidor.
     *
     * @param id the id of the medidor to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the medidor, or with status 404 (Not Found)
     */
    @GetMapping("/medidors/{id}")
    @Timed
    public ResponseEntity<Medidor> getMedidor(@PathVariable Long id) {
        log.debug("REST request to get Medidor : {}", id);
        Medidor medidor = medidorRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(medidor));
    }

    /**
     * DELETE  /medidors/:id : delete the "id" medidor.
     *
     * @param id the id of the medidor to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/medidors/{id}")
    @Timed
    public ResponseEntity<Void> deleteMedidor(@PathVariable Long id) {
        log.debug("REST request to delete Medidor : {}", id);
        medidorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
