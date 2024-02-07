package com.colegio.demo.interfacesService;

import java.util.List;
import java.util.Optional;

import com.colegio.demo.modelo.IngresoPPFF;


public interface IIngresoPPFFService {
	public List<IngresoPPFF>listarIngresoPPFF();
	public Optional<IngresoPPFF>listarId(int id_ingresoPPFF);
	public int Guardar(IngresoPPFF IPF); //Ingreso Padre de Familia 
	public void Borrar(int id_ingresoPPFF);
}
