package com.colegio.demo.interfaces;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.colegio.demo.modelo.IngresoPersonaExterna;
@Repository
public interface IIngresoPE extends CrudRepository<IngresoPersonaExterna, Integer> {
	@Query("SELECT i FROM IngresoPersonaExterna i WHERE i.fecha = :fecha")
    List<IngresoPersonaExterna> listarIngresoPEPorFecha(@Param("fecha") Date fecha);
}
