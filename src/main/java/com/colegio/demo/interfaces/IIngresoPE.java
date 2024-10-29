package com.colegio.demo.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.colegio.demo.modelo.IngresoPersonaExterna;

@Repository
public interface IIngresoPE extends CrudRepository<IngresoPersonaExterna, Integer> {
	@Query("SELECT i FROM IngresoPersonaExternaDTO i WHERE i.fecha = :fecha")
    List<IngresoPersonaExterna> listarIngresoPEPorFecha(@Param("fecha") LocalDate fecha);
	
	@Query("SELECT i FROM IngresoPersonaExternaDTO i WHERE i.personaE.id_personaE = :id_personaE")
    List<IngresoPersonaExterna> BuscarPersonalId(@Param("id_personaE") int id_personaE);
}
