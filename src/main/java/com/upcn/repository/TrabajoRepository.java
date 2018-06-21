package com.upcn.repository;

import com.upcn.domain.Trabajo;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Trabajo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TrabajoRepository extends JpaRepository<Trabajo, Long>, JpaSpecificationExecutor<Trabajo> {
    @Query("select distinct trabajo from Trabajo trabajo left join fetch trabajo.materials")
    List<Trabajo> findAllWithEagerRelationships();

    @Query("select trabajo from Trabajo trabajo left join fetch trabajo.materials where trabajo.id =:id")
    Trabajo findOneWithEagerRelationships(@Param("id") Long id);

}
