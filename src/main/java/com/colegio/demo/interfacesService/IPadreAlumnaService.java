package com.colegio.demo.interfacesService;

import java.util.List;

import com.colegio.demo.modelo.PadreAlumna;

public interface IPadreAlumnaService {
	public List<PadreAlumna> listarPorPadre(int idPadre);
	public List<PadreAlumna> listarPorAlumna(int idAlumna);
	public PadreAlumna Guardar(PadreAlumna pa);
	public void Borrar(int idPadre, int idAlumna);
	public List<PadreAlumna> listarTodas();
}
