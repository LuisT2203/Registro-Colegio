package com.colegio.demo.interfaces;
import java.time.LocalDate;
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
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(@Param("fecha") LocalDate fecha);
    
    @Query("SELECT ipc FROM IngresoPersonalColegio ipc WHERE ipc.personal.id_personal = :id_personal")
    List<IngresoPersonalColegio> BuscarPersonalId(@Param("id_personal") int id_personal);
}
