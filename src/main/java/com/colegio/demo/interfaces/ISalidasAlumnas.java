package com.colegio.demo.interfaces;

import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.modelo.SalidasAlumnas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ISalidasAlumnas extends CrudRepository<SalidasAlumnas, Integer> {

    // Query personalizado para obtener ingresos por fecha
    @Query("SELECT i FROM SalidasAlumnas i WHERE i.fecha = :fecha")
    List<SalidasAlumnas> listarSalidasPorFecha(@Param("fecha") LocalDate fecha);

    @Query("FROM SalidasAlumnas sa WHERE sa.nombre_alu like %:nombre_alu%")
    List<SalidasAlumnas> BuscarAlumnaNombre(@Param("nombre_alu") String nombre_alu);
}
