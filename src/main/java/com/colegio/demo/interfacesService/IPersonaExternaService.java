package com.colegio.demo.interfacesService;


import java.util.List;
import java.util.Optional;


import com.colegio.demo.modelo.PersonaExterna;


public interface IPersonaExternaService {
	public List<PersonaExterna>listarPersonaE();
	public Optional<PersonaExterna>listarId(int id_personaE);
	public int Guardar(PersonaExterna PE);
	public void Borrar(int id_personaE);
	
}
