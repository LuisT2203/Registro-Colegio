package com.colegio.demo.interfacesService;

import java.util.List;

import com.colegio.demo.modelo.Alumna;
import com.colegio.demo.modelo.PadreApoderado;
import com.colegio.demo.modelo.Persona;
import com.colegio.demo.modelo.Trabajador;

public interface IPersonaService {
	public List<Trabajador> listarTrabajadores();
	public List<Alumna> listarAlumnas();
	public List<PadreApoderado> listarPadres();
	public List<Persona> listarExternos();
	public Persona listarId(int id_persona);
	public Persona Guardar(Persona p);
	public Persona Borrar(int id_persona);
	public Persona buscarPorDni(String dni);
}
