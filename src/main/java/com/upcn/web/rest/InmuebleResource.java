package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Inmueble;
import com.upcn.service.InmuebleService;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import com.upcn.service.dto.InmuebleCriteria;
import com.upcn.service.InmuebleQueryService;
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
 * REST controller for managing Inmueble.
 */
@RestController
@RequestMapping("/api")
public class InmuebleResource {

    private final Logger log = LoggerFactory.getLogger(InmuebleResource.class);

    private static final String ENTITY_NAME = "inmueble";

    private final InmuebleService inmuebleService;

    private final InmuebleQueryService inmuebleQueryService;

    public InmuebleResource(InmuebleService inmuebleService, InmuebleQueryService inmuebleQueryService) {
        this.inmuebleService = inmuebleService;
        this.inmuebleQueryService = inmuebleQueryService;
    }

    /**
     * POST  /inmuebles : Create a new inmueble.
     *
     * @param inmueble the inmueble to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inmueble, or with status 400 (Bad Request) if the inmueble has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inmuebles")
    @Timed
    public ResponseEntity<Inmueble> createInmueble(@RequestBody Inmueble inmueble) throws URISyntaxException {
        log.debug("REST request to save Inmueble : {}", inmueble);
        if (inmueble.getId() != null) {
            throw new BadRequestAlertException("A new inmueble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inmueble result = inmuebleService.save(inmueble);
        return ResponseEntity.created(new URI("/api/inmuebles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inmuebles : Updates an existing inmueble.
     *
     * @param inmueble the inmueble to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inmueble,
     * or with status 400 (Bad Request) if the inmueble is not valid,
     * or with status 500 (Internal Server Error) if the inmueble couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inmuebles")
    @Timed
    public ResponseEntity<Inmueble> updateInmueble(@RequestBody Inmueble inmueble) throws URISyntaxException {
        log.debug("REST request to update Inmueble : {}", inmueble);
        if (inmueble.getId() == null) {
            return createInmueble(inmueble);
        }
        Inmueble result = inmuebleService.save(inmueble);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inmueble.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inmuebles : get all the inmuebles.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inmuebles in body
     */
    @GetMapping("/inmuebles")
    @Timed
    public ResponseEntity<List<Inmueble>> getAllInmuebles(InmuebleCriteria criteria) {
        log.debug("REST request to get Inmuebles by criteria: {}", criteria);
        List<Inmueble> entityList = inmuebleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /inmuebles/:id : get the "id" inmueble.
     *
     * @param id the id of the inmueble to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inmueble, or with status 404 (Not Found)
     */
    @GetMapping("/inmuebles/{id}")
    @Timed
    public ResponseEntity<Inmueble> getInmueble(@PathVariable Long id) {
        log.debug("REST request to get Inmueble : {}", id);
        Inmueble inmueble = inmuebleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inmueble));
    }

    /**
     * DELETE  /inmuebles/:id : delete the "id" inmueble.
     *
     * @param id the id of the inmueble to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inmuebles/{id}")
    @Timed
    public ResponseEntity<Void> deleteInmueble(@PathVariable Long id) {
        log.debug("REST request to delete Inmueble : {}", id);
        inmuebleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
