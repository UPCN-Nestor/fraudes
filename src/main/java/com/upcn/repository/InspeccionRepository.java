package com.upcn.repository;

import com.upcn.domain.Inspeccion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Inspeccion entity.
 */
@SuppressWarnings("unused")
@Repository
public interface InspeccionRepository extends JpaRepository<Inspeccion, Long>, JpaSpecificationExecutor<Inspeccion> {
    @Query("select distinct inspeccion from Inspeccion inspeccion left join fetch inspeccion.anomaliaMedidors left join fetch inspeccion.trabajos")
    List<Inspeccion> findAllWithEagerRelationships();

    @Query("select inspeccion from Inspeccion inspeccion left join fetch inspeccion.anomaliaMedidors left join fetch inspeccion.trabajos where inspeccion.id =:id")
    Inspeccion findOneWithEagerRelationships(@Param("id") Long id);

}
