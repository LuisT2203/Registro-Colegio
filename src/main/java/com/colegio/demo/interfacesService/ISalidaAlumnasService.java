package com.colegio.demo.interfacesService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.colegio.demo.modelo.SalidasAlumnas;

public interface ISalidaAlumnasService {
	public List<SalidasAlumnas> ListarSalidasAlumnas();
	public SalidasAlumnas listarID(int id_salida);
	public SalidasAlumnas Guardar(SalidasAlumnas sa);
	public SalidasAlumnas Borrar(int id_salida);
	public List<SalidasAlumnas> ListarSalidaPorFecha(LocalDate fecha);
	public List<SalidasAlumnas> ListarSalidaPorAlumna(int idAlumna);
	public List<Map<String, Object>> listarPadresPorAlumna(int idAlumna);
	public Map<String, Object> enriquecerParentesco(SalidasAlumnas sa);
}
