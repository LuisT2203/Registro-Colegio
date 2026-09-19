package com.colegio.demo.interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.PadreAlumna;

import jakarta.transaction.Transactional;

@Repository
public interface IPadreAlumnaRepository extends CrudRepository<PadreAlumna, PadreAlumna.PadreAlumnaId> {

	@Query("SELECT pa FROM PadreAlumna pa JOIN FETCH pa.padre p JOIN FETCH pa.alumna a WHERE pa.id_padre = :idPadre")
	List<PadreAlumna> findByPadreId(@Param("idPadre") int idPadre);

	@Query("SELECT pa FROM PadreAlumna pa JOIN FETCH pa.padre p JOIN FETCH pa.alumna a WHERE pa.id_alumna = :idAlumna")
	List<PadreAlumna> findByAlumnaId(@Param("idAlumna") int idAlumna);

	@Query("SELECT COUNT(pa) > 0 FROM PadreAlumna pa WHERE pa.id_padre = :idPadre AND pa.id_alumna = :idAlumna")
	boolean existsByIdPadreAndIdAlumna(@Param("idPadre") int idPadre, @Param("idAlumna") int idAlumna);

	@Modifying
	@Transactional
	@Query("DELETE FROM PadreAlumna pa WHERE pa.id_padre = :idPadre AND pa.id_alumna = :idAlumna")
	void deleteByIdPadreAndIdAlumna(@Param("idPadre") int idPadre, @Param("idAlumna") int idAlumna);

	@Query("SELECT pa FROM PadreAlumna pa JOIN FETCH pa.padre p JOIN FETCH pa.alumna a")
	List<PadreAlumna> findAllWithJoins();
}
