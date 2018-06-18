package com.upcn.repository;

import com.upcn.domain.Anomalia;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Anomalia entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AnomaliaRepository extends JpaRepository<Anomalia, Long> {

}
