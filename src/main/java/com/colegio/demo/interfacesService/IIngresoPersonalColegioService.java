package com.colegio.demo.interfacesService;


import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;




import com.colegio.demo.modelo.IngresoPersonalColegio;
import net.sf.jasperreports.engine.JRException;

public interface IIngresoPersonalColegioService {
	public List<IngresoPersonalColegio>listarIngresoPC(); //PC= Personal Colegio
	public IngresoPersonalColegio listarId(int id_ingresoPersonal);
	public IngresoPersonalColegio Guardar(IngresoPersonalColegio IPC); //Ingreso Personal Colegio
	public IngresoPersonalColegio Borrar(int id_ingresoPersonal);
    List<IngresoPersonalColegio> listarIngresoPCPorFecha(LocalDate fecha);
    List<IngresoPersonalColegio>BuscarPersonalId(int id_personal);
	byte[] exportPdf() throws JRException, FileNotFoundException;
	byte[] exportPdfID(int id_personal) throws JRException, FileNotFoundException;
}
