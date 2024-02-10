package com.colegio.demo.interfaces;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.IngresoPPFF;

@Repository
public interface IIngresoPPFF extends CrudRepository<IngresoPPFF, Integer> {
	// Query personalizado para obtener ingresos por fecha
    @Query("SELECT i FROM IngresoPPFF i WHERE i.fecha = :fecha")
    List<IngresoPPFF> listarIngresoPPFFPorFecha(@Param("fecha") Date fecha);
}
