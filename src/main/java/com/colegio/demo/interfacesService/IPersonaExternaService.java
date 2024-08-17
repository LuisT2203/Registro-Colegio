package com.colegio.demo.interfacesService;


import java.util.List;



import com.colegio.demo.modelo.PersonaExterna;


public interface IPersonaExternaService {
	public List<PersonaExterna>listarPersonaE();
	public PersonaExterna listarId(int id_personaE);
	public PersonaExterna Guardar(PersonaExterna PE);
	public PersonaExterna Borrar(int id_personaE);
	
}
