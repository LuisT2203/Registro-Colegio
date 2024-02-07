package com.colegio.demo.interfaces;
import java.sql.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.IngresoPersonalColegio;
@Repository
public interface IIngresoPC extends CrudRepository<IngresoPersonalColegio, Integer> {
	// Query personalizado para obtener ingresos por fecha
    @Query("SELECT i FROM IngresoPersonalColegio i WHERE i.fecha = :fecha")
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(@Param("fecha") Date fecha);
}
