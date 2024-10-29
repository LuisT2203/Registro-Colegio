package com.colegio.demo.interfaces;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.IngresoPPFF;


@Repository
public interface IIngresoPPFF extends CrudRepository<IngresoPPFF, Integer> {
	// Query personalizado para obtener ingresos por fecha
    @Query("SELECT i FROM IngresoPPFFDTO i WHERE i.fecha = :fecha")
    List<IngresoPPFF> listarIngresoPPFFPorFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT i FROM IngresoPPFFDTO i WHERE i.padre.id_ppff = :id_ppff")
    List<IngresoPPFF> BuscarPersonalId(@Param("id_ppff") int id_ppff);
}
