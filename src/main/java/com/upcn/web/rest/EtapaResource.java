package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Etapa;
import com.upcn.service.EtapaService;
import com.upcn.web.rest.errors.BadRequestAlertException;
import com.upcn.web.rest.util.HeaderUtil;
import com.upcn.service.dto.EtapaCriteria;
import com.upcn.service.EtapaQueryService;
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
 * REST controller for managing Etapa.
 */
@RestController
@RequestMapping("/api")
public class EtapaResource {

    private final Logger log = LoggerFactory.getLogger(EtapaResource.class);

    private static final String ENTITY_NAME = "etapa";

    private final EtapaService etapaService;

    private final EtapaQueryService etapaQueryService;

    public EtapaResource(EtapaService etapaService, EtapaQueryService etapaQueryService) {
        this.etapaService = etapaService;
        this.etapaQueryService = etapaQueryService;
    }

    /**
     * POST  /etapas : Create a new etapa.
     *
     * @param etapa the etapa to create
     * @return the ResponseEntity with status 201 (Created) and with body the new etapa, or with status 400 (Bad Request) if the etapa has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/etapas")
    @Timed
    public ResponseEntity<Etapa> createEtapa(@RequestBody Etapa etapa) throws URISyntaxException {
        log.debug("REST request to save Etapa : {}", etapa);
        if (etapa.getId() != null) {
            throw new BadRequestAlertException("A new etapa cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Etapa result = etapaService.save(etapa);
        return ResponseEntity.created(new URI("/api/etapas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /etapas : Updates an existing etapa.
     *
     * @param etapa the etapa to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated etapa,
     * or with status 400 (Bad Request) if the etapa is not valid,
     * or with status 500 (Internal Server Error) if the etapa couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/etapas")
    @Timed
    public ResponseEntity<Etapa> updateEtapa(@RequestBody Etapa etapa) throws URISyntaxException {
        log.debug("REST request to update Etapa : {}", etapa);
        if (etapa.getId() == null) {
            return createEtapa(etapa);
        }
        Etapa result = etapaService.save(etapa);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, etapa.getId().toString()))
            .body(result);
    }

    /**
     * GET  /etapas : get all the etapas.
     *
     * @param criteria the criterias which the requested entities should match
     * @return the ResponseEntity with status 200 (OK) and the list of etapas in body
     */
    @GetMapping("/etapas")
    @Timed
    public ResponseEntity<List<Etapa>> getAllEtapas(EtapaCriteria criteria) {
        log.debug("REST request to get Etapas by criteria: {}", criteria);
        List<Etapa> entityList = etapaQueryService.findByCriteria(criteria);
        return ResponseEntity.ok().body(entityList);
    }

    /**
     * GET  /etapas/:id : get the "id" etapa.
     *
     * @param id the id of the etapa to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the etapa, or with status 404 (Not Found)
     */
    @GetMapping("/etapas/{id}")
    @Timed
    public ResponseEntity<Etapa> getEtapa(@PathVariable Long id) {
        log.debug("REST request to get Etapa : {}", id);
        Etapa etapa = etapaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(etapa));
    }

    /**
     * DELETE  /etapas/:id : delete the "id" etapa.
     *
     * @param id the id of the etapa to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/etapas/{id}")
    @Timed
    public ResponseEntity<Void> deleteEtapa(@PathVariable Long id) {
        log.debug("REST request to delete Etapa : {}", id);
        etapaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
