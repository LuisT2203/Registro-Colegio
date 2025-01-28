package com.colegio.demo.interfacesService;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;


import com.colegio.demo.modelo.IngresoPPFF;
import net.sf.jasperreports.engine.JRException;


public interface IIngresoPPFFService {
	public List<IngresoPPFF>listarIngresoPPFF();
	public IngresoPPFF listarId(int id_ingresoPPFF);
	public IngresoPPFF Guardar(IngresoPPFF IPF); //Ingreso Padre de Familia 
	public IngresoPPFF Borrar(int id_ingresoPPFF);
	List<IngresoPPFF> listarIngresoPPFFPorFecha(LocalDate fecha);
	List<IngresoPPFF>BuscarPersonalId(int id_ppff);
	byte[] exportPdf() throws JRException, FileNotFoundException;
	byte[] exportPdfID(int id_ppff) throws JRException, FileNotFoundException;
}
