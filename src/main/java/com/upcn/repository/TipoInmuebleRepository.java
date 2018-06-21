package com.upcn.repository;

import com.upcn.domain.TipoInmueble;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the TipoInmueble entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TipoInmuebleRepository extends JpaRepository<TipoInmueble, Long>, JpaSpecificationExecutor<TipoInmueble> {

}
