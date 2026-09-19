package com.colegio.demo.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Rol;

@Repository
public interface IRolRepository extends CrudRepository<Rol, Integer> {
	@Query("SELECT r FROM Rol r WHERE r.nombre_rol = :nombreRol")
	Optional<Rol> findByNombre_rol(@Param("nombreRol") String nombreRol);
}
