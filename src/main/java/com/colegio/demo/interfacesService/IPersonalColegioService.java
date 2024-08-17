package com.colegio.demo.interfacesService;

import java.util.List;



import com.colegio.demo.modelo.PersonalColegio;

public interface IPersonalColegioService {
	public List<PersonalColegio>listarPersonal();
	public PersonalColegio listarId(int Id_personal);
	public PersonalColegio Guardar(PersonalColegio PC);
	public PersonalColegio Borrar(int Id_personal);
}
