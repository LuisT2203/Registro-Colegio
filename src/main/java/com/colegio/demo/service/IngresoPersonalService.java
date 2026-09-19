package com.colegio.demo.service;

import java.io.FileNotFoundException;
import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.colegio.demo.Dto.DetalleDiaDTO;
import com.colegio.demo.interfaces.IIngresoPersonalRepository;
import com.colegio.demo.interfacesService.IIngresoPersonalService;
import com.colegio.demo.modelo.IngresoPersonal;
import com.colegio.demo.modelo.Persona;
import com.colegio.demo.modelo.Trabajador;
import com.colegio.demo.utils.PersonalReportGenerator;
import net.sf.jasperreports.engine.JRException;

@Service
public class IngresoPersonalService implements IIngresoPersonalService {

	@Autowired
	private IIngresoPersonalRepository data;
	@Autowired
	private PersonalReportGenerator personalReportGenerator;

	@Override
	public List<IngresoPersonal> listarIngresos() {
		return (List<IngresoPersonal>) data.findAll();
	}

	@Override
	public IngresoPersonal listarId(int id_ingreso) {
		return data.findById(id_ingreso).orElse(new IngresoPersonal());
	}

	@Override
	public IngresoPersonal Guardar(IngresoPersonal IP) {
		return data.save(IP);
	}

	@Override
	public IngresoPersonal Borrar(int id_ingreso) {
		IngresoPersonal temp = data.findById(id_ingreso).orElse(null);
		if (temp == null) {
			return new IngresoPersonal();
		} else {
			data.deleteById(id_ingreso);
			return temp;
		}
	}

	@Override
	public long CalcularHorasTrabajadas(String tipoPeriodo, LocalDate fechaBase, Integer idPersona) {
		LocalDate fechaInicio;
		LocalDate fechaFin;

		switch (tipoPeriodo.toUpperCase()) {
			case "DIA" -> {
				fechaInicio = fechaBase;
				fechaFin = fechaBase;
			}
			case "SEMANA" -> {
				fechaInicio = fechaBase.with(DayOfWeek.MONDAY);
				fechaFin = fechaBase.with(DayOfWeek.SUNDAY);
			}
			case "MES" -> {
				fechaInicio = fechaBase.withDayOfMonth(1);
				fechaFin = fechaBase.withDayOfMonth(fechaBase.lengthOfMonth());
			}
			default -> throw new IllegalArgumentException("Periodo no válido");
		}

		List<IngresoPersonal> ingresos = data.listarPorPersonaYRango(idPersona, fechaInicio, fechaFin);

		long minutosTotales = ingresos.stream()
				.mapToLong(i -> Duration.between(i.getHora_ingreso(), i.getHora_salida()).toMinutes())
				.sum();

		return minutosTotales / 60;
	}

	@Override
	public List<IngresoPersonal> listarIngresoPorFecha(LocalDate fecha) {
		return data.listarIngresoPorFecha(fecha);
	}

	@Override
	public List<IngresoPersonal> BuscarPersonaId(int id_persona) {
		return data.BuscarPersonaId(id_persona);
	}

	@Override
	public List<IngresoPersonal> listarIngresoPorFechaTrabajador(LocalDate fecha) {
		return data.listarIngresoPorFechaTrabajador(fecha);
	}

	@Override
	public List<IngresoPersonal> BuscarPersonaIdTrabajador(int id_persona) {
		return data.BuscarPersonaIdTrabajador(id_persona);
	}

	@Override
	public List<IngresoPersonal> listarIngresoPorFechaPadre(LocalDate fecha) {
		return data.listarIngresoPorFechaPadre(fecha);
	}

	@Override
	public List<IngresoPersonal> BuscarPersonaIdPadre(int id_persona) {
		return data.BuscarPersonaIdPadre(id_persona);
	}

	@Override
	public List<IngresoPersonal> listarIngresoPorFechaExterno(LocalDate fecha) {
		return data.listarIngresoPorFechaExterno(fecha);
	}

	@Override
	public List<IngresoPersonal> BuscarPersonaIdExterno(int id_persona) {
		return data.BuscarPersonaIdExterno(id_persona);
	}

	@Override
	public byte[] exportPdf() throws JRException, FileNotFoundException {
		List<IngresoPersonal> list = (List<IngresoPersonal>) data.findAll();
		return personalReportGenerator.exportToPdf(list);
	}

	@Override
	public byte[] exportPdfID(int id_persona) throws JRException, FileNotFoundException {
		List<IngresoPersonal> list = data.BuscarPersonaId(id_persona);
		return personalReportGenerator.exportToPdf(list);
	}

	@Override
	public byte[] exportExcelID(int id_persona) throws JRException, FileNotFoundException {
		List<IngresoPersonal> list = data.BuscarPersonaId(id_persona);
		String nombre = !list.isEmpty() && list.get(0).getPersona() != null
				? list.get(0).getPersona().getNombre() + " " + list.get(0).getPersona().getApellido()
				: "";
		return personalReportGenerator.exportToXls(list, nombre);
	}

	@Override
	public Map<String, Object> consultarHorasDetalle(int idPersona, LocalDate fechaInicio, LocalDate fechaFin) {
		List<IngresoPersonal> ingresos = data.listarPorPersonaYRango(idPersona, fechaInicio, fechaFin);

		Persona persona = ingresos.isEmpty() ? null : ingresos.get(0).getPersona();

		double totalMinutos = 0;
		int dias = 0;
		List<DetalleDiaDTO> detalle = new ArrayList<>();

		for (IngresoPersonal i : ingresos) {
			boolean esEstimado = false;
			LocalTime salida = i.getHora_salida();
			if (salida == null) {
				salida = LocalTime.of(18, 0);
				esEstimado = true;
			}
			long minutos = Duration.between(i.getHora_ingreso(), salida).toMinutes();
			double horas = minutos / 60.0;
			totalMinutos += minutos;
			dias++;
			detalle.add(new DetalleDiaDTO(
					i.getId_ingreso(),
					i.getFecha(),
					i.getHora_ingreso(),
					i.getHora_salida(),
					Math.round(horas * 100.0) / 100.0,
					esEstimado));
		}

		double totalHoras = Math.round((totalMinutos / 60.0) * 100.0) / 100.0;
		double promedioDiario = dias > 0 ? Math.round((totalHoras / dias) * 100.0) / 100.0 : 0;

		Map<String, Object> result = new HashMap<>();
		result.put("personal", persona);
		result.put("totalHoras", totalHoras);
		result.put("diasTrabajados", dias);
		result.put("promedioDiario", promedioDiario);
		result.put("detalle", detalle);
		return result;
	}
}
