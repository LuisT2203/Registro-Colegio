package com.colegio.demo.service;
import java.util.List;
import java.util.Optional;
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
	public Optional<PPFF> listarId(int Id_ppff) {
		return data.findById(Id_ppff);
	}

	@Override
	public int Guardar(PPFF P) {
		int res=0;
		PPFF ppff=data.save(P);
		if (!ppff.equals(null)) {
			res=1;
		}
		return res;
	}

	@Override
	public void Borrar(int Id_ppff) {
		data.deleteById(Id_ppff);
		
	}

}
