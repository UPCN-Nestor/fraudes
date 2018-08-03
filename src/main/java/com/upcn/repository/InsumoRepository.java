package com.upcn.repository;

import com.upcn.domain.Insumo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Insumo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InsumoRepository extends JpaRepository<Insumo, Long> {

}
