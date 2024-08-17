package com.colegio.demo.interfacesService;


import java.time.LocalDate;
import java.util.List;



import com.colegio.demo.modelo.IngresoPersonaExterna;


public interface IIngresoPersonaExternaService {
	public List<IngresoPersonaExterna>listarIngresoPE();  //PE= Persona Externa
	public IngresoPersonaExterna listarId(int id_ingresoPersonaE);
	public IngresoPersonaExterna Guardar(IngresoPersonaExterna IPE); //Ingreso Padre de Familia 
	public IngresoPersonaExterna Borrar(int id_ingresoPersonaE);
	List<IngresoPersonaExterna> listarIngresoPEPorFecha(LocalDate fecha);
	 List<IngresoPersonaExterna>BuscarPersonalId(int id_personaE);
}
