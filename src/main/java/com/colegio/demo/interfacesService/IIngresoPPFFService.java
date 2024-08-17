package com.colegio.demo.interfacesService;


import java.time.LocalDate;
import java.util.List;


import com.colegio.demo.modelo.IngresoPPFF;



public interface IIngresoPPFFService {
	public List<IngresoPPFF>listarIngresoPPFF();
	public IngresoPPFF listarId(int id_ingresoPPFF);
	public IngresoPPFF Guardar(IngresoPPFF IPF); //Ingreso Padre de Familia 
	public IngresoPPFF Borrar(int id_ingresoPPFF);
	List<IngresoPPFF> listarIngresoPPFFPorFecha(LocalDate fecha);
	List<IngresoPPFF>BuscarPersonalId(int id_ppff);
}
