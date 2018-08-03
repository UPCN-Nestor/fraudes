package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Inspeccion;
import com.upcn.service.InspeccionService;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import com.upcn.web.rest.util.PaginationUtil;
import com.upcn.service.dto.InspeccionCriteria;
import com.upcn.service.InspeccionQueryService;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Inspeccion.
 */
@RestController
@RequestMapping("/api")
public class InspeccionResource {

    private final Logger log = LoggerFactory.getLogger(InspeccionResource.class);

    private static final String ENTITY_NAME = "inspeccion";

    private final InspeccionService inspeccionService;

    private final InspeccionQueryService inspeccionQueryService;

    public InspeccionResource(InspeccionService inspeccionService, InspeccionQueryService inspeccionQueryService) {
        this.inspeccionService = inspeccionService;
        this.inspeccionQueryService = inspeccionQueryService;
    }

    /**
     * POST  /inspeccions : Create a new inspeccion.
     *
     * @param inspeccion the inspeccion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inspeccion, or with status 400 (Bad Request) if the inspeccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inspeccions")
    @Timed
    public ResponseEntity<Inspeccion> createInspeccion(@RequestBody Inspeccion inspeccion) throws URISyntaxException {
        log.debug("REST request to save Inspeccion : {}", inspeccion);
        if (inspeccion.getId() != null) {
            throw new BadRequestAlertException("A new inspeccion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Inspeccion result = inspeccionService.save(inspeccion);
        return ResponseEntity.created(new URI("/api/inspeccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inspeccions : Updates an existing inspeccion.
     *
     * @param inspeccion the inspeccion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inspeccion,
     * or with status 400 (Bad Request) if the inspeccion is not valid,
     * or with status 500 (Internal Server Error) if the inspeccion couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inspeccions")
    @Timed
    public ResponseEntity<Inspeccion> updateInspeccion(@RequestBody Inspeccion inspeccion) throws URISyntaxException {
        log.debug("REST request to update Inspeccion : {}", inspeccion);
        if (inspeccion.getId() == null) {
            return createInspeccion(inspeccion);
        }
        Inspeccion result = inspeccionService.save(inspeccion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inspeccion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inspeccions : get all the inspeccions.
     *
     * @param pageable the pagination information
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of inspeccions in body
     */
    @GetMapping("/inspeccions")
    @Timed
    public ResponseEntity<List<Inspeccion>> getAllInspeccions(InspeccionCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Inspeccions by criteria: {}", criteria);
        Page<Inspeccion> page = inspeccionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inspeccions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inspeccions/:id : get the "id" inspeccion.
     *
     * @param id the id of the inspeccion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inspeccion, or with status 404 (Not Found)
     */
    @GetMapping("/inspeccions/{id}")
    @Timed
    public ResponseEntity<Inspeccion> getInspeccion(@PathVariable Long id) {
        log.debug("REST request to get Inspeccion : {}", id);
        Inspeccion inspeccion = inspeccionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inspeccion));
    }

    /**
     * DELETE  /inspeccions/:id : delete the "id" inspeccion.
     *
     * @param id the id of the inspeccion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inspeccions/{id}")
    @Timed
    public ResponseEntity<Void> deleteInspeccion(@PathVariable Long id) {
        log.debug("REST request to delete Inspeccion : {}", id);
        inspeccionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
