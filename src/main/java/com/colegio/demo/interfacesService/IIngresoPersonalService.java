package com.colegio.demo.interfacesService;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import com.colegio.demo.modelo.IngresoPersonal;
import net.sf.jasperreports.engine.JRException;

public interface IIngresoPersonalService {
	public List<IngresoPersonal> listarIngresos();
	public IngresoPersonal listarId(int id_ingreso);
	public IngresoPersonal Guardar(IngresoPersonal ip);
	public IngresoPersonal Borrar(int id_ingreso);
	public long CalcularHorasTrabajadas(String tipoPeriodo, LocalDate fechaBase, Integer idPersona);
	List<IngresoPersonal> listarIngresoPorFecha(LocalDate fecha);
	List<IngresoPersonal> BuscarPersonaId(int id_persona);
	List<IngresoPersonal> listarIngresoPorFechaTrabajador(LocalDate fecha);
	List<IngresoPersonal> BuscarPersonaIdTrabajador(int id_persona);
	List<IngresoPersonal> listarIngresoPorFechaPadre(LocalDate fecha);
	List<IngresoPersonal> BuscarPersonaIdPadre(int id_persona);
	List<IngresoPersonal> listarIngresoPorFechaExterno(LocalDate fecha);
	List<IngresoPersonal> BuscarPersonaIdExterno(int id_persona);
	byte[] exportPdf() throws JRException, FileNotFoundException;
	byte[] exportPdfID(int id_persona) throws JRException, FileNotFoundException;
	byte[] exportExcelID(int id_persona) throws JRException, FileNotFoundException;
	Map<String, Object> consultarHorasDetalle(int idPersona, LocalDate fechaInicio, LocalDate fechaFin);
}
