package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.TipoInmueble;
import com.upcn.service.TipoInmuebleService;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import com.upcn.service.dto.TipoInmuebleCriteria;
import com.upcn.service.TipoInmuebleQueryService;
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
 * REST controller for managing TipoInmueble.
 */
@RestController
@RequestMapping("/api")
public class TipoInmuebleResource {

    private final Logger log = LoggerFactory.getLogger(TipoInmuebleResource.class);

    private static final String ENTITY_NAME = "tipoInmueble";

    private final TipoInmuebleService tipoInmuebleService;

    private final TipoInmuebleQueryService tipoInmuebleQueryService;

    public TipoInmuebleResource(TipoInmuebleService tipoInmuebleService, TipoInmuebleQueryService tipoInmuebleQueryService) {
        this.tipoInmuebleService = tipoInmuebleService;
        this.tipoInmuebleQueryService = tipoInmuebleQueryService;
    }

    /**
     * POST  /tipo-inmuebles : Create a new tipoInmueble.
     *
     * @param tipoInmueble the tipoInmueble to create
     * @return the ResponseEntity with status 201 (Created) and with body the new tipoInmueble, or with status 400 (Bad Request) if the tipoInmueble has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/tipo-inmuebles")
    @Timed
    public ResponseEntity<TipoInmueble> createTipoInmueble(@RequestBody TipoInmueble tipoInmueble) throws URISyntaxException {
        log.debug("REST request to save TipoInmueble : {}", tipoInmueble);
        if (tipoInmueble.getId() != null) {
            throw new BadRequestAlertException("A new tipoInmueble cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TipoInmueble result = tipoInmuebleService.save(tipoInmueble);
        return ResponseEntity.created(new URI("/api/tipo-inmuebles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tipo-inmuebles : Updates an existing tipoInmueble.
     *
     * @param tipoInmueble the tipoInmueble to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated tipoInmueble,
     * or with status 400 (Bad Request) if the tipoInmueble is not valid,
     * or with status 500 (Internal Server Error) if the tipoInmueble couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/tipo-inmuebles")
    @Timed
    public ResponseEntity<TipoInmueble> updateTipoInmueble(@RequestBody TipoInmueble tipoInmueble) throws URISyntaxException {
        log.debug("REST request to update TipoInmueble : {}", tipoInmueble);
        if (tipoInmueble.getId() == null) {
            return createTipoInmueble(tipoInmueble);
        }
        TipoInmueble result = tipoInmuebleService.save(tipoInmueble);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, tipoInmueble.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tipo-inmuebles : get all the tipoInmuebles.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of tipoInmuebles in body
     */
    @GetMapping("/tipo-inmuebles")
    @Timed
    public ResponseEntity<List<TipoInmueble>> getAllTipoInmuebles(TipoInmuebleCriteria criteria) {
        log.debug("REST request to get TipoInmuebles by criteria: {}", criteria);
        List<TipoInmueble> entityList = tipoInmuebleQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /tipo-inmuebles/:id : get the "id" tipoInmueble.
     *
     * @param id the id of the tipoInmueble to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the tipoInmueble, or with status 404 (Not Found)
     */
    @GetMapping("/tipo-inmuebles/{id}")
    @Timed
    public ResponseEntity<TipoInmueble> getTipoInmueble(@PathVariable Long id) {
        log.debug("REST request to get TipoInmueble : {}", id);
        TipoInmueble tipoInmueble = tipoInmuebleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(tipoInmueble));
    }

    /**
     * DELETE  /tipo-inmuebles/:id : delete the "id" tipoInmueble.
     *
     * @param id the id of the tipoInmueble to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/tipo-inmuebles/{id}")
    @Timed
    public ResponseEntity<Void> deleteTipoInmueble(@PathVariable Long id) {
        log.debug("REST request to delete TipoInmueble : {}", id);
        tipoInmuebleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
