package com.colegio.demo.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.colegio.demo.modelo.SalidasAlumnas;

public interface ISalidasAlumnas extends CrudRepository<SalidasAlumnas, Integer> {

	@Query("SELECT s FROM SalidasAlumnas s JOIN FETCH s.alumna WHERE s.fecha = :fecha")
	List<SalidasAlumnas> listarSalidasPorFecha(@Param("fecha") LocalDate fecha);

	@Query("SELECT s FROM SalidasAlumnas s JOIN FETCH s.alumna WHERE s.alumna.id_persona = :idAlumna")
	List<SalidasAlumnas> BuscarAlumnaId(@Param("idAlumna") int idAlumna);
}
