package com.colegio.demo.service;

import java.util.List;

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
	public PersonaExterna listarId(int id_personaE) {
		return data.findById(id_personaE).orElse(new PersonaExterna());
	}

	@Override
	public PersonaExterna Guardar(PersonaExterna PE) {
		
		PersonaExterna personaexterna=data.save(PE);
		return personaexterna;
		
	}

	@Override
	public PersonaExterna Borrar(int id_personaE) {
		PersonaExterna temp = data.findById(id_personaE).orElse(null);
		if(temp==null){
			return new PersonaExterna();
		}else {
			data.deleteById(id_personaE);
			return temp;
		}
		
	}

	

}
