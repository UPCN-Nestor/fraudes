package com.upcn.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.upcn.domain.Insumo;

import com.upcn.repository.InsumoRepository;
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
 * REST controller for managing Insumo.
 */
@RestController
@RequestMapping("/api")
public class InsumoResource {

    private final Logger log = LoggerFactory.getLogger(InsumoResource.class);

    private static final String ENTITY_NAME = "insumo";

    private final InsumoRepository insumoRepository;

    public InsumoResource(InsumoRepository insumoRepository) {
        this.insumoRepository = insumoRepository;
    }

    /**
     * POST  /insumos : Create a new insumo.
     *
     * @param insumo the insumo to create
     * @return the ResponseEntity with status 201 (Created) and with body the new insumo, or with status 400 (Bad Request) if the insumo has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/insumos")
    @Timed
    public ResponseEntity<Insumo> createInsumo(@RequestBody Insumo insumo) throws URISyntaxException {
        log.debug("REST request to save Insumo : {}", insumo);
        if (insumo.getId() != null) {
            throw new BadRequestAlertException("A new insumo cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Insumo result = insumoRepository.save(insumo);
        return ResponseEntity.created(new URI("/api/insumos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /insumos : Updates an existing insumo.
     *
     * @param insumo the insumo to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated insumo,
     * or with status 400 (Bad Request) if the insumo is not valid,
     * or with status 500 (Internal Server Error) if the insumo couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/insumos")
    @Timed
    public ResponseEntity<Insumo> updateInsumo(@RequestBody Insumo insumo) throws URISyntaxException {
        log.debug("REST request to update Insumo : {}", insumo);
        if (insumo.getId() == null) {
            return createInsumo(insumo);
        }
        Insumo result = insumoRepository.save(insumo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, insumo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insumos : get all the insumos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of insumos in body
     */
    @GetMapping("/insumos")
    @Timed
    public List<Insumo> getAllInsumos() {
        log.debug("REST request to get all Insumos");
        return insumoRepository.findAll();
        }

    /**
     * GET  /insumos/:id : get the "id" insumo.
     *
     * @param id the id of the insumo to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the insumo, or with status 404 (Not Found)
     */
    @GetMapping("/insumos/{id}")
    @Timed
    public ResponseEntity<Insumo> getInsumo(@PathVariable Long id) {
        log.debug("REST request to get Insumo : {}", id);
        Insumo insumo = insumoRepository.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(insumo));
    }

    /**
     * DELETE  /insumos/:id : delete the "id" insumo.
     *
     * @param id the id of the insumo to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/insumos/{id}")
    @Timed
    public ResponseEntity<Void> deleteInsumo(@PathVariable Long id) {
        log.debug("REST request to delete Insumo : {}", id);
        insumoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
