package com.colegio.demo.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IPPFF;
import com.colegio.demo.interfacesService.IppffService;
import com.colegio.demo.modelo.PPFF;


@Service
public class PPFFService implements IppffService {
	@Autowired
	private IPPFF data;

	@Override
	public List<PPFF> listarPPFF() {
		return (List<PPFF>)data.findAll();
	}

	@Override
	public PPFF listarId(int Id_ppff) {
		return data.findById(Id_ppff).orElse(new PPFF());
	}

	@Override
	public PPFF Guardar(PPFF P) {
		
		PPFF ppff=data.save(P);
		return ppff;
		
	}

	@Override
	public PPFF Borrar(int Id_ppff) {

		PPFF temp = data.findById(Id_ppff).orElse(null);
		if(temp==null) {
			return new PPFF();
		}else {
			data.deleteById(Id_ppff);
			return temp;
		}
		
	}

}
