package com.colegio.demo.utils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.colegio.demo.modelo.IngresoPersonal;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;

@Service
public class PersonalReportGenerator {

	public byte[] exportToPdf(List<IngresoPersonal> list) throws JRException, FileNotFoundException {
		return JasperExportManager.exportReportToPdf(getReport(list, ""));
	}

	public byte[] exportToXls(List<IngresoPersonal> list, String nombrePersona) throws JRException, FileNotFoundException {
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
		JRXlsExporter exporter = new JRXlsExporter();
		exporter.setExporterInput(new SimpleExporterInput(getReport(list, nombrePersona)));
		exporter.setExporterOutput(output);
		exporter.exportReport();
		output.close();
		return byteArray.toByteArray();
	}

	private JasperPrint getReport(List<IngresoPersonal> list, String nombrePersona) throws FileNotFoundException, JRException {
		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("La lista de datos no puede ser nula o estar vacía.");
		}

		Map<String, Object> params = new HashMap<>();
		params.put("personalData", new JRBeanCollectionDataSource(list));
		params.put("nombrePersona", nombrePersona != null ? nombrePersona : "");

		try (InputStream reportStream = getClass().getResourceAsStream("/reportPersonal.jrxml")) {
			if (reportStream == null) {
				throw new FileNotFoundException("No se pudo encontrar el archivo reportPersonal.jrxml en el classpath.");
			}

			JasperReport compiledReport = JasperCompileManager.compileReport(reportStream);
			return JasperFillManager.fillReport(compiledReport, params, new JRBeanCollectionDataSource(list));
		} catch (IOException e) {
			throw new RuntimeException("Error al cargar el archivo del reporte: " + e.getMessage(), e);
		}
	}
}
