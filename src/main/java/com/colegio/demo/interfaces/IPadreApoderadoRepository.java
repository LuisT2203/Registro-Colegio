package com.colegio.demo.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.PadreApoderado;

@Repository
public interface IPadreApoderadoRepository extends JpaRepository<PadreApoderado, Integer> {

	@Query("SELECT pa FROM PadreApoderado pa " +
		   "WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(pa.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
		   "ORDER BY pa.nombre ASC")
	Page<PadreApoderado> findFiltered(@Param("nombre") String nombre, Pageable pageable);
}
