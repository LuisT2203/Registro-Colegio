package com.colegio.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IPersonalColegio;
import com.colegio.demo.interfacesService.IPersonalColegioService;
import com.colegio.demo.modelo.PersonalColegio;


@Service
public class PersonalColegioService implements IPersonalColegioService {
	@Autowired
	private IPersonalColegio data;
	@Override
	public List<PersonalColegio> listarPersonal() {
		return (List<PersonalColegio>)data.findAll();
	}

	@Override
	public PersonalColegio listarId(int Id_personal) {
		return data.findById(Id_personal).orElse(new PersonalColegio());
	}

	@Override
	public PersonalColegio Guardar(PersonalColegio PC) {
		
		PersonalColegio personalcolegio=data.save(PC);
		return personalcolegio;
		
	}

	@Override
	public PersonalColegio Borrar(int Id_personal) {
		PersonalColegio temp= data.findById(Id_personal).orElse(null);
		if(temp==null) {
			return new PersonalColegio();
		}else {
			data.deleteById(Id_personal);
			return temp;
		}
		
		
	}

}
