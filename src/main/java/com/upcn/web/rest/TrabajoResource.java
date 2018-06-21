package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Trabajo;
import com.upcn.service.TrabajoService;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import com.upcn.service.dto.TrabajoCriteria;
import com.upcn.service.TrabajoQueryService;
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
 * REST controller for managing Trabajo.
 */
@RestController
@RequestMapping("/api")
public class TrabajoResource {

    private final Logger log = LoggerFactory.getLogger(TrabajoResource.class);

    private static final String ENTITY_NAME = "trabajo";

    private final TrabajoService trabajoService;

    private final TrabajoQueryService trabajoQueryService;

    public TrabajoResource(TrabajoService trabajoService, TrabajoQueryService trabajoQueryService) {
        this.trabajoService = trabajoService;
        this.trabajoQueryService = trabajoQueryService;
    }

    /**
     * POST  /trabajos : Create a new trabajo.
     *
     * @param trabajo the trabajo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new trabajo, or with status 400 (Bad Request) if the trabajo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/trabajos")
    @Timed
    public ResponseEntity<Trabajo> createTrabajo(@RequestBody Trabajo trabajo) throws URISyntaxException {
        log.debug("REST request to save Trabajo : {}", trabajo);
        if (trabajo.getId() != null) {
            throw new BadRequestAlertException("A new trabajo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Trabajo result = trabajoService.save(trabajo);
        return ResponseEntity.created(new URI("/api/trabajos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trabajos : Updates an existing trabajo.
     *
     * @param trabajo the trabajo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated trabajo,
     * or with status 400 (Bad Request) if the trabajo is not valid,
     * or with status 500 (Internal Server Error) if the trabajo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/trabajos")
    @Timed
    public ResponseEntity<Trabajo> updateTrabajo(@RequestBody Trabajo trabajo) throws URISyntaxException {
        log.debug("REST request to update Trabajo : {}", trabajo);
        if (trabajo.getId() == null) {
            return createTrabajo(trabajo);
        }
        Trabajo result = trabajoService.save(trabajo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, trabajo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trabajos : get all the trabajos.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of trabajos in body
     */
    @GetMapping("/trabajos")
    @Timed
    public ResponseEntity<List<Trabajo>> getAllTrabajos(TrabajoCriteria criteria) {
        log.debug("REST request to get Trabajos by criteria: {}", criteria);
        List<Trabajo> entityList = trabajoQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /trabajos/:id : get the "id" trabajo.
     *
     * @param id the id of the trabajo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the trabajo, or with status 404 (Not Found)
     */
    @GetMapping("/trabajos/{id}")
    @Timed
    public ResponseEntity<Trabajo> getTrabajo(@PathVariable Long id) {
        log.debug("REST request to get Trabajo : {}", id);
        Trabajo trabajo = trabajoService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(trabajo));
    }

    /**
     * DELETE  /trabajos/:id : delete the "id" trabajo.
     *
     * @param id the id of the trabajo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/trabajos/{id}")
    @Timed
    public ResponseEntity<Void> deleteTrabajo(@PathVariable Long id) {
        log.debug("REST request to delete Trabajo : {}", id);
        trabajoService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
