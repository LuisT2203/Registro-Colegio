package com.colegio.demo.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IPadreAlumnaRepository;
import com.colegio.demo.interfaces.ISalidasAlumnas;
import com.colegio.demo.interfacesService.ISalidaAlumnasService;
import com.colegio.demo.modelo.PadreAlumna;
import com.colegio.demo.modelo.SalidasAlumnas;

@Service
public class SalidaAlumnasService implements ISalidaAlumnasService {

	@Autowired
	private ISalidasAlumnas repo;

	@Autowired
	private IPadreAlumnaRepository padreAlumnaRepo;

	@Override
	public List<SalidasAlumnas> ListarSalidasAlumnas() {
		return (List<SalidasAlumnas>) repo.findAll();
	}

	@Override
	public SalidasAlumnas listarID(int id_salida) {
		return repo.findById(id_salida).orElse(new SalidasAlumnas());
	}

	@Override
	public SalidasAlumnas Guardar(SalidasAlumnas sa) {
		if (sa.getId_padre() != null && sa.getAlumna() != null) {
			boolean existe = padreAlumnaRepo.existsByIdPadreAndIdAlumna(
					sa.getId_padre(), sa.getAlumna().getId_persona());
			if (!existe) {
				throw new RuntimeException("El padre seleccionado no esta vinculado a esta alumna");
			}
		}
		SalidasAlumnas salida = repo.save(sa);
		return salida;
	}

	public Map<String, Object> enriquecerParentesco(SalidasAlumnas sa) {
		Map<String, Object> result = new HashMap<>();
		if (sa.getId_padre() != null && sa.getAlumna() != null) {
			List<PadreAlumna> relaciones = padreAlumnaRepo.findByAlumnaId(sa.getAlumna().getId_persona());
			for (PadreAlumna pa : relaciones) {
				if (pa.getPadre().getId_persona() == sa.getId_padre()) {
					result.put("parentesco", pa.getParentesco());
					result.put("dni", pa.getPadre().getDni());
					result.put("personaquerecoje", pa.getPadre().getNombre() + " " + pa.getPadre().getApellido());
					break;
				}
			}
		}
		return result;
	}

	@Override
	public SalidasAlumnas Borrar(int id_salida) {
		SalidasAlumnas temp = repo.findById(id_salida).orElse(null);
		if (temp == null) {
			return new SalidasAlumnas();
		} else {
			repo.deleteById(id_salida);
			return temp;
		}
	}

	@Override
	public List<SalidasAlumnas> ListarSalidaPorFecha(LocalDate fecha) {
		return repo.listarSalidasPorFecha(fecha);
	}

	@Override
	public List<SalidasAlumnas> ListarSalidaPorAlumna(int idAlumna) {
		return repo.BuscarAlumnaId(idAlumna);
	}

	public List<Map<String, Object>> listarPadresPorAlumna(int idAlumna) {
		List<PadreAlumna> relaciones = padreAlumnaRepo.findByAlumnaId(idAlumna);
		List<Map<String, Object>> resultado = new ArrayList<>();
		for (PadreAlumna pa : relaciones) {
			Map<String, Object> map = new HashMap<>();
			map.put("id_padre", pa.getPadre().getId_persona());
			map.put("nombre", pa.getPadre().getNombre());
			map.put("apellido", pa.getPadre().getApellido());
			map.put("dni", pa.getPadre().getDni());
			map.put("parentesco", pa.getParentesco());
			resultado.add(map);
		}
		return resultado;
	}
}
