package com.upcn.repository;

import com.upcn.domain.Inmueble;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Inmueble entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InmuebleRepository extends JpaRepository<Inmueble, Long> {

}
