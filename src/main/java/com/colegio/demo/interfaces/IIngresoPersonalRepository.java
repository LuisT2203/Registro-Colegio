package com.colegio.demo.interfaces;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.IngresoPersonal;

@Repository
public interface IIngresoPersonalRepository extends CrudRepository<IngresoPersonal, Integer> {

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.fecha = :fecha")
	List<IngresoPersonal> listarIngresoPorFecha(@Param("fecha") LocalDate fecha);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.persona.id_persona = :idPersona")
	List<IngresoPersonal> BuscarPersonaId(@Param("idPersona") int idPersona);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.persona.id_persona = :idPersona AND i.fecha BETWEEN :fechaInicio AND :fechaFin")
	List<IngresoPersonal> listarPorPersonaYRango(@Param("idPersona") int idPersona, @Param("fechaInicio") LocalDate fechaInicio, @Param("fechaFin") LocalDate fechaFin);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.fecha = :fecha AND EXISTS (SELECT 1 FROM Trabajador t WHERE t.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> listarIngresoPorFechaTrabajador(@Param("fecha") LocalDate fecha);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.persona.id_persona = :idPersona AND EXISTS (SELECT 1 FROM Trabajador t WHERE t.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> BuscarPersonaIdTrabajador(@Param("idPersona") int idPersona);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.fecha = :fecha AND EXISTS (SELECT 1 FROM PadreApoderado pa WHERE pa.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> listarIngresoPorFechaPadre(@Param("fecha") LocalDate fecha);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.persona.id_persona = :idPersona AND EXISTS (SELECT 1 FROM PadreApoderado pa WHERE pa.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> BuscarPersonaIdPadre(@Param("idPersona") int idPersona);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.fecha = :fecha AND NOT EXISTS (SELECT 1 FROM Trabajador t WHERE t.id_persona = i.persona.id_persona) AND NOT EXISTS (SELECT 1 FROM PadreApoderado pa WHERE pa.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> listarIngresoPorFechaExterno(@Param("fecha") LocalDate fecha);

	@Query("SELECT i FROM IngresoPersonal i JOIN FETCH i.persona LEFT JOIN FETCH i.alumna WHERE i.persona.id_persona = :idPersona AND NOT EXISTS (SELECT 1 FROM Trabajador t WHERE t.id_persona = i.persona.id_persona) AND NOT EXISTS (SELECT 1 FROM PadreApoderado pa WHERE pa.id_persona = i.persona.id_persona)")
	List<IngresoPersonal> BuscarPersonaIdExterno(@Param("idPersona") int idPersona);
}
