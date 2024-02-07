package com.colegio.demo.service;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IIngresoPPFF;
import com.colegio.demo.interfacesService.IIngresoPPFFService;
import com.colegio.demo.modelo.IngresoPPFF;


@Service 
public class IngresoPPFFService implements IIngresoPPFFService{
	@Autowired
	private IIngresoPPFF data;
	@Override
	public List<IngresoPPFF> listarIngresoPPFF() {
		return (List<IngresoPPFF>)data.findAll();
	}

	@Override
	public Optional<IngresoPPFF> listarId(int id_ingresoPPFF) {
		return data.findById(id_ingresoPPFF);
	}

	@Override
	public int Guardar(IngresoPPFF IPF) {
		int res=0;
		IngresoPPFF ingresoPPFF=data.save(IPF);
		if (!ingresoPPFF.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int id_ingresoPPFF) {
		data.deleteById(id_ingresoPPFF);
		
	}

}
