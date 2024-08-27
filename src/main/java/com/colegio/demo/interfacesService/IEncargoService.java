package com.colegio.demo.interfacesService;

import java.time.LocalDate;
import java.util.List;

import com.colegio.demo.modelo.Encargo;

public interface IEncargoService {
	public List<Encargo> ListarEncargos();
	public Encargo listarID(int id_enc);
	public Encargo Guardar (Encargo enc);
	public Encargo Borrar(int id_enc);
	List<Encargo> ListarEncargoPorFecha (LocalDate fecha_enc);
	List<Encargo> ListarEncargoPorNombre(String Encargo);

}
