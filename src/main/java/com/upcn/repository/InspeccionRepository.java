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

    //@Query("select count(i.id) from Inspeccion i left join i.trabajos t where t.descripcion='42-Inspección'")
    @Query(nativeQuery=true, value = "select descripcion, count(it.inspeccions_id) as cant from (select * from trabajo where id in (select distinct trabajos_id from inspeccion_trabajo)) t join inspeccion i left join inspeccion_trabajo it on (t.id = it.trabajos_id and i.id = it.inspeccions_id) where (i.etapa_id = :etapa) group by descripcion")
    List<Object[]> findAllByTrabajo(@Param("etapa") Long etapa_id );

    @Query(nativeQuery=true, value = "select descripcion, count(ia.inspeccions_id) as cant from (select * from anomalia where id in (select distinct anomalia_medidors_id from inspeccion_anomalia_medidor)) a join inspeccion i left join inspeccion_anomalia_medidor ia on (a.id = ia.anomalia_medidors_id and i.id = ia.inspeccions_id) where (i.etapa_id = :etapa) group by descripcion")
    List<Object[]> findAllByAnomalia(@Param("etapa") Long etapa_id );

    @Query(nativeQuery=true, value = "select f, count(*) from (select *, date(fechahora) as f from inspeccion where fechahora is not null) i where etapa_id = :etapa group by f order by f")
    List<Object[]> findAllByFecha(@Param("etapa") Long etapa_id );
}

