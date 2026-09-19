package com.colegio.demo.interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Persona;

@Repository
public interface IPersonaRepository extends JpaRepository<Persona, Integer> {
	Optional<Persona> findByDni(String dni);

	@Query("SELECT p FROM Persona p WHERE p.id_persona NOT IN (SELECT t.id_persona FROM Trabajador t) AND p.id_persona NOT IN (SELECT a.id_persona FROM Alumna a) AND p.id_persona NOT IN (SELECT pa.id_persona FROM PadreApoderado pa)")
	List<Persona> findExternos();
}
