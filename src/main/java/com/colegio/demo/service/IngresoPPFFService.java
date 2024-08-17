package com.colegio.demo.service;

import java.time.LocalDate;
import java.util.List;

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
	public IngresoPPFF listarId(int id_ingresoPPFF) {
		return data.findById(id_ingresoPPFF).orElse(new IngresoPPFF());
	}

	@Override
	public IngresoPPFF Guardar(IngresoPPFF IPF) {
		return data.save(IPF);
		
	}

	@Override
	public IngresoPPFF Borrar(int id_ingresoPPFF) {
		IngresoPPFF temp = data.findById(id_ingresoPPFF).orElse(null);
		if(temp==null) {
			return new IngresoPPFF();
		}else {
			data.deleteById(id_ingresoPPFF);
			return temp;
		}
		
	}
	@Override
	public List<IngresoPPFF> listarIngresoPPFFPorFecha(LocalDate fecha) {
	    return data.listarIngresoPPFFPorFecha(fecha);
	}

	@Override
	public List<IngresoPPFF> BuscarPersonalId(int id_ppff) {
		
		return data.BuscarPersonalId(id_ppff);
	}

}
