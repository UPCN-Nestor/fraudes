package com.upcn.repository;

import com.upcn.domain.Inspeccion;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Date;

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
    @Query(nativeQuery=true, value = "select e.descripcion_corta as descripcion, count(it.inspeccions_id) as cant from etapa e join inspeccion i on (e.id = i.etapa_id) left join inspeccion_trabajo it on (i.id = it.inspeccions_id and it.trabajos_id = :trab) group by descripcion")
    List<Object[]> findAllByTrabajo(@Param("trab") Long trab_id );

    @Query(nativeQuery=true, value = "select e.descripcion_corta as descripcion, count(ia.inspeccions_id) as cant from etapa e join inspeccion i on (e.id = i.etapa_id) left join inspeccion_anomalia_medidor ia on (i.id = ia.inspeccions_id and ia.anomalia_medidors_id = :anom) group by descripcion")
    List<Object[]> findAllByAnomalia(@Param("anom") Long anom_id );

    @Query(nativeQuery=true, value = "select f, count(*) from (select *, date(fechahora) as f from inspeccion where fechahora is not null) i where etapa_id = :etapa group by f order by f")
    List<Object[]> findAllByFecha(@Param("etapa") Long etapa_id );

    @Query(nativeQuery=true, value = "select t.descripcion, count(it.inspeccions_id) as cantidad, round(sum(case when i.mono_trif is null or i.mono_trif = 0 then t.costo else t.costo_trif end), 2) costo from inspeccion i join inspeccion_trabajo it on (i.id = it.inspeccions_id) join trabajo t on (it.trabajos_id = t.id) where (i.etapa_id = :etapa or :etapa = 9999) and (i.fechahora >= :desde) and (i.fechahora <= :hasta) group by t.descripcion")
    List<Object[]> findAllByEtapaDesdeHasta(@Param("etapa") Long etapa_id, @Param("desde") String desde, @Param("hasta") String hasta);

    @Query(nativeQuery=true, value = "select m.codigo, m.descripcion, sum(case when ins.es_editable then i.mts_cable else ins.cantidad end) cantidad from inspeccion i join inspeccion_trabajo it on (i.id = it.inspeccions_id) join insumo ins on (ins.trabajo_id = it.trabajos_id) join material m on (ins.material_id = m.id) where (i.etapa_id = :etapa or :etapa = 9999) and (i.fechahora >= :desde) and (i.fechahora <= :hasta) group by m.codigo, m.descripcion order by m.codigo ")
    List<Object[]> findMaterialesByEtapaDesdeHasta(@Param("etapa") Long etapa_id, @Param("desde") String desde, @Param("hasta") String hasta);


}

