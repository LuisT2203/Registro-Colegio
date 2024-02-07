package com.colegio.demo.interfacesService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.colegio.demo.modelo.IngresoPersonalColegio;

public interface IIngresoPersonalColegioService {
	public List<IngresoPersonalColegio>listarIngresoPC(); //PC= Personal Colegio
	public Optional<IngresoPersonalColegio>listarId(int id_ingresoPersonal);
	public int Guardar(IngresoPersonalColegio IPC); //Ingreso Personal Colegio
	public void Borrar(int id_ingresoPersonal);
	// Query personalizado para obtener ingresos por fecha
    @Query("SELECT i FROM IngresoPersonalColegio i WHERE i.fecha = :fecha")
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(@Param("fecha") Date fecha);
}
