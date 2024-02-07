package com.colegio.demo.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IPersonaExterna;
import com.colegio.demo.interfacesService.IPersonaExternaService;
import com.colegio.demo.modelo.PersonaExterna;


@Service
public class PersonaExternaService implements IPersonaExternaService {
	@Autowired
	private IPersonaExterna data;
	@Override
	public List<PersonaExterna> listarPersonaE() {
		return (List<PersonaExterna>)data.findAll();
	}

	@Override
	public Optional<PersonaExterna> listarId(int id_personaE) {
		return data.findById(id_personaE);
	}

	@Override
	public int Guardar(PersonaExterna PE) {
		int res=0;
		PersonaExterna personaexterna=data.save(PE);
		if (!personaexterna.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int id_personaE) {
		data.deleteById(id_personaE);
		
	}

}
