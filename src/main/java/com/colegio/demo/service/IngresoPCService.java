package com.colegio.demo.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import com.colegio.demo.utils.PersonalReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IIngresoPC;

import com.colegio.demo.interfacesService.IIngresoPersonalColegioService;
import com.colegio.demo.modelo.IngresoPersonalColegio;


@Service 
public class IngresoPCService implements IIngresoPersonalColegioService {
	@Autowired
	private IIngresoPC data;
	@Autowired
	private PersonalReportGenerator personalReportGenerator;
	@Override
	public List<IngresoPersonalColegio> listarIngresoPC() {
		return (List<IngresoPersonalColegio>)data.findAll();
	}

	@Override
	public IngresoPersonalColegio listarId(int id_ingresoPersonal) {
		return data.findById(id_ingresoPersonal).orElse(new IngresoPersonalColegio());
	}

	@Override
	public IngresoPersonalColegio Guardar(IngresoPersonalColegio IPC) {
		
		 return data.save(IPC);
		
	}

	@Override
	public IngresoPersonalColegio Borrar(int id_ingresoPersonal) {
		IngresoPersonalColegio temp = data.findById(id_ingresoPersonal).orElse(null);
		if(temp == null) {
			return new IngresoPersonalColegio();
		}else {
			data.deleteById(id_ingresoPersonal);
			return temp;
		}
		
		
	}
	
	@Override
	public List<IngresoPersonalColegio> listarIngresoPCPorFecha(LocalDate fecha) {
	    return data.listarIngresoPCPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonalColegio> BuscarPersonalId(int id_personal) {
		return data.BuscarPersonalId(id_personal);
	}

	@Override
	public byte[] exportPdf() throws JRException, FileNotFoundException {
		List<IngresoPersonalColegio> personalList = (List<IngresoPersonalColegio>) data.findAll();
		return personalReportGenerator.exportToPdfPC(personalList);
	}


	@Override
	public byte[] exportPdfID(int id_personal) throws JRException, FileNotFoundException {
		// Obtener la lista filtrada según el ID proporcionado
		List<IngresoPersonalColegio> personalList = data.BuscarPersonalId(id_personal);
		// Generar el PDF basado en la lista filtrada
		return personalReportGenerator.exportToPdfPC(personalList);
	}

}
