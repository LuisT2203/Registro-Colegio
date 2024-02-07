package com.colegio.demo.interfacesService;

import java.util.List;
import java.util.Optional;


import com.colegio.demo.modelo.PersonalColegio;

public interface IPersonalColegioService {
	public List<PersonalColegio>listarPersonal();
	public Optional<PersonalColegio>listarId(int Id_personal);
	public int Guardar(PersonalColegio PC);
	public void Borrar(int Id_personal);
}
