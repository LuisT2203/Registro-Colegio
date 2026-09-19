package com.colegio.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Trabajador;

@Repository
public interface ITrabajadorRepository extends JpaRepository<Trabajador, Integer> {

	@Query("SELECT t FROM Trabajador t " +
		   "WHERE (:cargo IS NULL OR :cargo = '' OR LOWER(t.cargo) LIKE LOWER(CONCAT('%', :cargo, '%'))) " +
		   "AND (:nombre IS NULL OR :nombre = '' OR LOWER(t.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
		   "ORDER BY t.nombre ASC")
	Page<Trabajador> findFiltered(@Param("cargo") String cargo, @Param("nombre") String nombre, Pageable pageable);
}
