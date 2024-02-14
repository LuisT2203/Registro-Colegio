package com.colegio.demo.service;
import java.sql.Date;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IIngresoPC;

import com.colegio.demo.interfacesService.IIngresoPersonalColegioService;
import com.colegio.demo.modelo.IngresoPersonalColegio;


@Service 
public class IngresoPCService implements IIngresoPersonalColegioService {
	@Autowired
	private IIngresoPC data;
	@Override
	public List<IngresoPersonalColegio> listarIngresoPC() {
		return (List<IngresoPersonalColegio>)data.findAll();
	}

	@Override
	public Optional<IngresoPersonalColegio> listarId(int id_ingresoPersonal) {
		return data.findById(id_ingresoPersonal);
	}

	@Override
	public int Guardar(IngresoPersonalColegio IPC) {
		int res=0;
		IngresoPersonalColegio ingresoPC=data.save(IPC);
		if (!ingresoPC.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int id_ingresoPersonal) {
		data.deleteById(id_ingresoPersonal);
		
	}
	
	@Override
	public List<IngresoPersonalColegio> listarIngresoPCPorFecha(Date fecha) {
	    return data.listarIngresoPCPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonalColegio> BuscarPersonalId(int id_personal) {
		return data.BuscarPersonalId(id_personal);
	}
}
