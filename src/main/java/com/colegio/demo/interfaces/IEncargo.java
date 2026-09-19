package com.colegio.demo.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Encargo;

@Repository
public interface IEncargo extends CrudRepository<Encargo, Integer> {

	@Query("SELECT e FROM Encargo e WHERE e.fecha_enc = :fecha_enc")
	List<Encargo> ListarEncargosPorFecha(@Param("fecha_enc") LocalDate fecha_enc);

	@Query("FROM Encargo e WHERE e.encargo LIKE %:encargo%")
	List<Encargo> ListarEncargosPorNombre(@Param("encargo") String encargo);
}
