package com.colegio.demo.interfacesService;

import java.util.List;
import java.util.Optional;

import com.colegio.demo.modelo.PPFF;



public interface IppffService {
	public List<PPFF>listarPPFF();
	public Optional<PPFF>listarId(int Id_ppff);
	public int Guardar(PPFF P);
	public void Borrar(int Id_ppff);
}
