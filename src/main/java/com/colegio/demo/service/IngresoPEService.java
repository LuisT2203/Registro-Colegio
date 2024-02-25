package com.colegio.demo.service;
import java.sql.Date;
import java.util.List;
import java.util.Optional;
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
	public Optional<IngresoPersonaExterna> listarId(int id_ingresoPersonaE) {
		return data.findById(id_ingresoPersonaE);
	}

	@Override
	public int Guardar(IngresoPersonaExterna IPE) {
		int res=0;
		IngresoPersonaExterna ingresoPE=data.save(IPE);
		if (!ingresoPE.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int id_ingresoPersonaE) {
		data.deleteById(id_ingresoPersonaE);
		
	}

	@Override
	public List<IngresoPersonaExterna> listarIngresoPEPorFecha(Date fecha) {
		// TODO Auto-generated method stub
		return data.listarIngresoPEPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonaExterna> BuscarPersonalId(int id_personaE) {
		
		return data.BuscarPersonalId(id_personaE);
	}

}
