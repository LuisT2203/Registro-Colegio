package com.colegio.demo.service;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;

import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.utils.PersonalReportGenerator;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.IIngresoPE;
import com.colegio.demo.interfacesService.IIngresoPersonaExternaService;
import com.colegio.demo.modelo.IngresoPersonaExterna;


@Service
public class IngresoPEService implements IIngresoPersonaExternaService {
	@Autowired
	private IIngresoPE data;
	@Autowired
	private PersonalReportGenerator personalReportGenerator;
	@Override
	public List<IngresoPersonaExterna> listarIngresoPE() {
		return (List<IngresoPersonaExterna>)data.findAll();
	}

	@Override
	public IngresoPersonaExterna listarId(int id_ingresoPersonaE) {
		return data.findById(id_ingresoPersonaE).orElse(new IngresoPersonaExterna());
	}

	@Override
	public IngresoPersonaExterna Guardar(IngresoPersonaExterna IPE) {
		return data.save(IPE);
	}

	@Override
	public IngresoPersonaExterna Borrar(int id_ingresoPersonaE) {
		IngresoPersonaExterna temp = data.findById(id_ingresoPersonaE).orElse(null);
		if(temp==null) {
			return new IngresoPersonaExterna();
		}else {
			data.deleteById(id_ingresoPersonaE);
			return temp;
		}
		
	}

	@Override
	public List<IngresoPersonaExterna> listarIngresoPEPorFecha(LocalDate fecha) {
		// TODO Auto-generated method stub
		return data.listarIngresoPEPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonaExterna> BuscarPersonalId(int id_personaE) {
		
		return data.BuscarPersonalId(id_personaE);
	}

	@Override
	public byte[] exportPdf() throws JRException, FileNotFoundException {
		List<IngresoPersonaExterna> personalList = (List<IngresoPersonaExterna>) data.findAll();
		return personalReportGenerator.exportToPdfPE(personalList);
	}



	@Override
	public byte[] exportPdfID(int id_personaE) throws JRException, FileNotFoundException {
		List<IngresoPersonaExterna> personalList = data.BuscarPersonalId(id_personaE);
		// Generar el Excel basado en la lista filtrada
		return personalReportGenerator.exportToPdfPE(personalList);
	}


}
