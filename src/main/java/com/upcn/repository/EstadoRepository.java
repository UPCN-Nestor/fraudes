package com.upcn.repository;

import com.upcn.domain.Estado;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Estado entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EstadoRepository extends JpaRepository<Estado, Long> {

}
