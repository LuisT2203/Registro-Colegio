package com.colegio.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colegio.demo.interfaces.IPadreAlumnaRepository;
import com.colegio.demo.interfacesService.IPadreAlumnaService;
import com.colegio.demo.modelo.PadreAlumna;

@Service
public class PadreAlumnaService implements IPadreAlumnaService {

	@Autowired
	private IPadreAlumnaRepository repo;

	@Override
	public List<PadreAlumna> listarPorPadre(int idPadre) {
		return repo.findByPadreId(idPadre);
	}

	@Override
	public List<PadreAlumna> listarPorAlumna(int idAlumna) {
		return repo.findByAlumnaId(idAlumna);
	}

	@Override
	public PadreAlumna Guardar(PadreAlumna pa) {
		return repo.save(pa);
	}

	@Override
	@Transactional
	public void Borrar(int idPadre, int idAlumna) {
		repo.deleteByIdPadreAndIdAlumna(idPadre, idAlumna);
	}

	@Override
	public List<PadreAlumna> listarTodas() {
		return repo.findAllWithJoins();
	}
}
