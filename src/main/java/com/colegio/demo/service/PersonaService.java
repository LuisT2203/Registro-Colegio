package com.colegio.demo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.colegio.demo.interfaces.IAlumnaRepository;
import com.colegio.demo.interfaces.IPadreApoderadoRepository;
import com.colegio.demo.interfaces.IPersonaRepository;
import com.colegio.demo.interfaces.ITrabajadorRepository;
import com.colegio.demo.interfacesService.IPersonaService;
import com.colegio.demo.modelo.Alumna;
import com.colegio.demo.modelo.PadreApoderado;
import com.colegio.demo.modelo.Persona;
import com.colegio.demo.modelo.Trabajador;

@Service
public class PersonaService implements IPersonaService {

	@Autowired
	private IPersonaRepository personaRepo;
	@Autowired
	private ITrabajadorRepository trabajadorRepo;
	@Autowired
	private IAlumnaRepository alumnaRepo;
	@Autowired
	private IPadreApoderadoRepository padreRepo;

	@Override
	public List<Trabajador> listarTrabajadores() {
		return (List<Trabajador>) trabajadorRepo.findAll();
	}

	public Map<String, Object> listarTrabajadoresFiltrado(String cargo, String nombre, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Trabajador> pageResult;

		boolean conFiltros = (cargo != null && !cargo.isEmpty()) || (nombre != null && !nombre.isEmpty());
		if (conFiltros) {
			pageResult = trabajadorRepo.findFiltered(cargo, nombre, pageable);
		} else {
			pageResult = trabajadorRepo.findAll(pageable);
		}

		Map<String, Object> result = new HashMap<>();
		result.put("content", pageResult.getContent());
		result.put("totalPages", pageResult.getTotalPages());
		result.put("totalElements", pageResult.getTotalElements());
		result.put("currentPage", pageResult.getNumber());
		return result;
	}

	@Override
	public List<Alumna> listarAlumnas() {
		return (List<Alumna>) alumnaRepo.findAll();
	}

	public Map<String, Object> listarAlumnasFiltrado(String grado, String nivel, String seccion, int page, int size) {
		if (grado != null && !grado.isEmpty() && nivel != null && !nivel.isEmpty() && seccion != null && !seccion.isEmpty()) {
			Pageable pageable = PageRequest.of(page, size);
			Page<Alumna> pageResult = alumnaRepo.findByGradoAndNivelAndSeccion(grado, nivel, seccion, pageable);
			Map<String, Object> result = new HashMap<>();
			result.put("content", pageResult.getContent());
			result.put("totalPages", pageResult.getTotalPages());
			result.put("totalElements", pageResult.getTotalElements());
			result.put("currentPage", pageResult.getNumber());
			return result;
		}
		List<Alumna> all = (List<Alumna>) alumnaRepo.findAll();
		Map<String, Object> result = new HashMap<>();
		result.put("content", all);
		result.put("totalPages", 1);
		result.put("totalElements", all.size());
		result.put("currentPage", 0);
		return result;
	}

	@Override
	public List<PadreApoderado> listarPadres() {
		return (List<PadreApoderado>) padreRepo.findAll();
	}

	public Map<String, Object> listarPadresFiltrado(String nombre, int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<PadreApoderado> pageResult = padreRepo.findFiltered(
				(nombre != null && !nombre.isEmpty()) ? nombre : "",
				pageable);
		Map<String, Object> result = new HashMap<>();
		result.put("content", pageResult.getContent());
		result.put("totalPages", pageResult.getTotalPages());
		result.put("totalElements", pageResult.getTotalElements());
		result.put("currentPage", pageResult.getNumber());
		return result;
	}

	@Override
	public List<Persona> listarExternos() {
		return personaRepo.findExternos();
	}

	@Override
	public Persona listarId(int id_persona) {
		return personaRepo.findById(id_persona).orElse(null);
	}

	@Override
	@Transactional
	public Persona Guardar(Persona p) {
		if (p instanceof Trabajador) {
			return trabajadorRepo.save((Trabajador) p);
		} else if (p instanceof Alumna) {
			return alumnaRepo.save((Alumna) p);
		} else if (p instanceof PadreApoderado) {
			return padreRepo.save((PadreApoderado) p);
		} else {
			return personaRepo.save(p);
		}
	}

	@Override
	public Persona Borrar(int id_persona) {
		Persona temp = personaRepo.findById(id_persona).orElse(null);
		if (temp == null) {
			return null;
		} else {
			personaRepo.deleteById(id_persona);
			return temp;
		}
	}

	@Override
	public Persona buscarPorDni(String dni) {
		Optional<Persona> p = personaRepo.findByDni(dni);
		return p.orElse(null);
	}

}
