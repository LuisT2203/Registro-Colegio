package com.colegio.demo.service;
import java.util.List;
import java.util.Optional;
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
	public Optional<PersonalColegio> listarId(int Id_personal) {
		return data.findById(Id_personal);
	}

	@Override
	public int Guardar(PersonalColegio PC) {
		int res=0;
		PersonalColegio personalcolegio=data.save(PC);
		if (!personalcolegio.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int Id_personal) {
		data.deleteById(Id_personal);
		
	}

}
