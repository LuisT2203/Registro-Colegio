package com.colegio.demo.interfacesService;

import java.util.List;


import com.colegio.demo.modelo.PPFF;



public interface IppffService {
	public List<PPFF>listarPPFF();
	public PPFF listarId(int Id_ppff);
	public PPFF Guardar(PPFF P);
	public PPFF Borrar(int Id_ppff);
}
