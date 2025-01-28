package com.colegio.demo.utils;
import com.colegio.demo.modelo.IngresoPPFF;
import com.colegio.demo.modelo.IngresoPersonaExterna;
import com.colegio.demo.modelo.IngresoPersonalColegio;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class PersonalReportGenerator {
    /**
     * Genera un reporte en formato PDF.
     *
     * @param list Lista de objetos de tipo Personal.
     * @return Un arreglo de bytes que representa el contenido del archivo PDF.
     * @throws JRException En caso de que ocurra un error al generar el reporte.
     * @throws FileNotFoundException En caso de que no se encuentre el archivo del diseño del reporte.
     */
    public byte[] exportToPdfPC(List<IngresoPersonalColegio> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportPC(list));
    }
    public byte[] exportToPdfPE(List<IngresoPersonaExterna> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportPE(list));
    }
    public byte[] exportToPdfPF(List<IngresoPPFF> list) throws JRException, FileNotFoundException {
        return JasperExportManager.exportReportToPdf(getReportPF(list));
    }

    /**
     * Genera un reporte en formato XLS.
     *
     * @param list Lista de objetos de tipo Personal.
     * @return Un arreglo de bytes que representa el contenido del archivo XLS.
     * @throws JRException En caso de que ocurra un error al generar el reporte.
     * @throws FileNotFoundException En caso de que no se encuentre el archivo del diseño del reporte.
     */
    public byte[] exportToXls(List<IngresoPersonalColegio> list) throws JRException, FileNotFoundException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        SimpleOutputStreamExporterOutput output = new SimpleOutputStreamExporterOutput(byteArray);
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setExporterInput(new SimpleExporterInput(getReportPC(list)));
        exporter.setExporterOutput(output);
        exporter.exportReport();
        output.close();
        return byteArray.toByteArray();
    }

    /**
     * Método auxiliar para configurar y compilar el reporte Jasper.
     *
     * @param list Lista de objetos de tipo Personal.
     * @return JasperPrint, que representa el contenido compilado del reporte.
     * @throws FileNotFoundException En caso de que no se encuentre el archivo del diseño del reporte.
     * @throws JRException En caso de que ocurra un error al compilar el reporte.
     */
    private JasperPrint getReportPC(List<IngresoPersonalColegio> list) throws FileNotFoundException, JRException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista de datos no puede ser nula o estar vacía.");
        }

        // Parámetros del reporte
        Map<String, Object> params = new HashMap<>();
        params.put("personalData", new JRBeanCollectionDataSource(list));

        // Cargar el archivo .jrxml como InputStream
        try (InputStream reportStream = getClass().getResourceAsStream("/reportPersonal.jrxml")) {
            if (reportStream == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo reportPersonal.jrxml en el classpath.");
            }

            // Compilar y llenar el reporte
            JasperReport compiledReport = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(compiledReport, params, new JREmptyDataSource());
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo del reporte: " + e.getMessage(), e);
        }
    }
    private JasperPrint getReportPE(List<IngresoPersonaExterna> list) throws FileNotFoundException, JRException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista de datos no puede ser nula o estar vacía.");
        }

        // Parámetros del reporte
        Map<String, Object> params = new HashMap<>();
        params.put("personalData", new JRBeanCollectionDataSource(list));

        // Cargar el archivo .jrxml como InputStream
        try (InputStream reportStream = getClass().getResourceAsStream("/reportPersonal.jrxml")) {
            if (reportStream == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo reportPersonal.jrxml en el classpath.");
            }

            // Compilar y llenar el reporte
            JasperReport compiledReport = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(compiledReport, params, new JREmptyDataSource());
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo del reporte: " + e.getMessage(), e);
        }
    }
    private JasperPrint getReportPF(List<IngresoPPFF> list) throws FileNotFoundException, JRException {
        if (list == null || list.isEmpty()) {
            throw new IllegalArgumentException("La lista de datos no puede ser nula o estar vacía.");
        }

        // Parámetros del reporte
        Map<String, Object> params = new HashMap<>();
        params.put("personalData", new JRBeanCollectionDataSource(list));

        // Cargar el archivo .jrxml como InputStream
        try (InputStream reportStream = getClass().getResourceAsStream("/reportPersonal.jrxml")) {
            if (reportStream == null) {
                throw new FileNotFoundException("No se pudo encontrar el archivo reportPersonal.jrxml en el classpath.");
            }

            // Compilar y llenar el reporte
            JasperReport compiledReport = JasperCompileManager.compileReport(reportStream);
            return JasperFillManager.fillReport(compiledReport, params, new JREmptyDataSource());
        } catch (IOException e) {
            throw new RuntimeException("Error al cargar el archivo del reporte: " + e.getMessage(), e);
        }
    }
}
