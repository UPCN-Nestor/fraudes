package com.upcn.repository;

import com.upcn.domain.Precinto;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Precinto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrecintoRepository extends JpaRepository<Precinto, Long> {

}
