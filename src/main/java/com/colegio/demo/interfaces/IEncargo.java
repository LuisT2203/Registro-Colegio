package com.colegio.demo.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Encargo;
@Repository
public interface IEncargo extends CrudRepository<Encargo,Integer> {
	
	@Query("SELECT i From EncargoDTO i WHERE i.fecha_enc = :fecha_enc")
	List<Encargo> ListarEncargosPorFecha(@Param("fecha_enc") LocalDate fecha_enc);
	
	@Query("From EncargoDTO e WHERE e.encargoNom like %:encargoNom%")
	List<Encargo> ListarEncargosPorNombre(@Param("encargoNom") String encargoNom);

}
