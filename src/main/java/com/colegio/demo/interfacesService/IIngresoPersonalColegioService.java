package com.colegio.demo.interfacesService;


import java.time.LocalDate;
import java.util.List;




import com.colegio.demo.modelo.IngresoPersonalColegio;

public interface IIngresoPersonalColegioService {
	public List<IngresoPersonalColegio>listarIngresoPC(); //PC= Personal Colegio
	public IngresoPersonalColegio listarId(int id_ingresoPersonal);
	public IngresoPersonalColegio Guardar(IngresoPersonalColegio IPC); //Ingreso Personal Colegio
	public IngresoPersonalColegio Borrar(int id_ingresoPersonal);
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(LocalDate fecha);
    List<IngresoPersonalColegio>BuscarPersonalId(int id_personal);
}
