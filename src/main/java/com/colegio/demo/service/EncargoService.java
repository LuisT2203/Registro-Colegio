package com.colegio.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IEncargo;
import com.colegio.demo.interfacesService.IEncargoService;
import com.colegio.demo.modelo.Encargo;
@Service
public class EncargoService implements IEncargoService {
	
	@Autowired
	private IEncargo repo;
	
	@Override
	public List<Encargo> ListarEncargos() {
		return (List<Encargo>)repo.findAll();
	}

	@Override
	public Encargo listarID(int id_enc) {
		return repo.findById(id_enc).orElse(new Encargo());
	}

	@Override
	public Encargo Guardar(Encargo enc) {
		Encargo encargo = repo.save(enc);
		return encargo;
	}

	@Override
	public Encargo Borrar(int id_enc) {
		Encargo temp = repo.findById(id_enc).orElse(null);
		if(temp==null) {
			return new Encargo();
		}else {
			repo.deleteById(id_enc);
			return temp;
		}
	}

	@Override
	public List<Encargo> ListarEncargoPorFecha(LocalDate fecha_enc) {
		return repo.ListarEncargosPorFecha(fecha_enc);
	}

	@Override
	public List<Encargo> ListarEncargoPorNombre(String Encargo) {
		return repo.ListarEncargosPorNombre(Encargo);
	}

}
