package com.colegio.demo.interfacesService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;


import com.colegio.demo.modelo.IngresoPersonaExterna;


public interface IIngresoPersonaExternaService {
	public List<IngresoPersonaExterna>listarIngresoPE();  //PE= Persona Externa
	public Optional<IngresoPersonaExterna>listarId(int id_ingresoPersonaE);
	public int Guardar(IngresoPersonaExterna IPE); //Ingreso Padre de Familia 
	public void Borrar(int id_ingresoPersonaE);
	List<IngresoPersonaExterna> listarIngresoPEPorFecha(Date fecha);
	 List<IngresoPersonaExterna>BuscarPersonalId(int id_personaE);
}
