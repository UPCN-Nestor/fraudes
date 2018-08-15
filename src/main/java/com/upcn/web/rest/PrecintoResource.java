package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Precinto;

import com.upcn.repository.PrecintoRepository;
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
 * REST controller for managing Precinto.
 */
@RestController
@RequestMapping("/api")
public class PrecintoResource {

    private final Logger log = LoggerFactory.getLogger(PrecintoResource.class);

    private static final String ENTITY_NAME = "precinto";

    private final PrecintoRepository precintoRepository;

    public PrecintoResource(PrecintoRepository precintoRepository) {
        this.precintoRepository = precintoRepository;
    }

    /**
     * POST  /precintos : Create a new precinto.
     *
     * @param precinto the precinto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new precinto, or with status 400 (Bad Request) if the precinto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/precintos")
    @Timed
    public ResponseEntity<Precinto> createPrecinto(@RequestBody Precinto precinto) throws URISyntaxException {
        log.debug("REST request to save Precinto : {}", precinto);
        if (precinto.getId() != null) {
            throw new BadRequestAlertException("A new precinto cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Precinto result = precintoRepository.save(precinto);
        return ResponseEntity.created(new URI("/api/precintos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /precintos : Updates an existing precinto.
     *
     * @param precinto the precinto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated precinto,
     * or with status 400 (Bad Request) if the precinto is not valid,
     * or with status 500 (Internal Server Error) if the precinto couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/precintos")
    @Timed
    public ResponseEntity<Precinto> updatePrecinto(@RequestBody Precinto precinto) throws URISyntaxException {
        log.debug("REST request to update Precinto : {}", precinto);
        if (precinto.getId() == null) {
            return createPrecinto(precinto);
        }
        Precinto result = precintoRepository.save(precinto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, precinto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /precintos : get all the precintos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of precintos in body
     */
    @GetMapping("/precintos")
    @Timed
    public List<Precinto> getAllPrecintos() {
        log.debug("REST request to get all Precintos");
        return precintoRepository.findAll();
        }

    /**
     * GET  /precintos/:id : get the "id" precinto.
     *
     * @param id the id of the precinto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the precinto, or with status 404 (Not Found)
     */
    @GetMapping("/precintos/{id}")
    @Timed
    public ResponseEntity<Precinto> getPrecinto(@PathVariable Long id) {
        log.debug("REST request to get Precinto : {}", id);
        Precinto precinto = precintoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(precinto));
    }

    /**
     * DELETE  /precintos/:id : delete the "id" precinto.
     *
     * @param id the id of the precinto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/precintos/{id}")
    @Timed
    public ResponseEntity<Void> deletePrecinto(@PathVariable Long id) {
        log.debug("REST request to delete Precinto : {}", id);
        precintoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
