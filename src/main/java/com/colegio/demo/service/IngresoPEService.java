package com.colegio.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IIngresoPE;
import com.colegio.demo.interfacesService.IIngresoPersonaExternaService;
import com.colegio.demo.modelo.IngresoPersonaExterna;


@Service
public class IngresoPEService implements IIngresoPersonaExternaService {
	@Autowired
	private IIngresoPE data;
	@Override
	public List<IngresoPersonaExterna> listarIngresoPE() {
		return (List<IngresoPersonaExterna>)data.findAll();
	}

	@Override
	public IngresoPersonaExterna listarId(int id_ingresoPersonaE) {
		return data.findById(id_ingresoPersonaE).orElse(new IngresoPersonaExterna());
	}

	@Override
	public IngresoPersonaExterna Guardar(IngresoPersonaExterna IPE) {
		return data.save(IPE);
	}

	@Override
	public IngresoPersonaExterna Borrar(int id_ingresoPersonaE) {
		IngresoPersonaExterna temp = data.findById(id_ingresoPersonaE).orElse(null);
		if(temp==null) {
			return new IngresoPersonaExterna();
		}else {
			data.deleteById(id_ingresoPersonaE);
			return temp;
		}
		
	}

	@Override
	public List<IngresoPersonaExterna> listarIngresoPEPorFecha(LocalDate fecha) {
		// TODO Auto-generated method stub
		return data.listarIngresoPEPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonaExterna> BuscarPersonalId(int id_personaE) {
		
		return data.BuscarPersonalId(id_personaE);
	}

}
