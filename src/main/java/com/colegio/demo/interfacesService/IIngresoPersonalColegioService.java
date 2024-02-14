package com.colegio.demo.interfacesService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;



import com.colegio.demo.modelo.IngresoPersonalColegio;

public interface IIngresoPersonalColegioService {
	public List<IngresoPersonalColegio>listarIngresoPC(); //PC= Personal Colegio
	public Optional<IngresoPersonalColegio>listarId(int id_ingresoPersonal);
	public int Guardar(IngresoPersonalColegio IPC); //Ingreso Personal Colegio
	public void Borrar(int id_ingresoPersonal);
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(Date fecha);
    List<IngresoPersonalColegio>BuscarPersonalId(int id_personal);
}
