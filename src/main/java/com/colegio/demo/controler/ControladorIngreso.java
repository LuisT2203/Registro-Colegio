package com.colegio.demo.controler;

import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.demo.Dto.IngresoPersonalDTO;
import com.colegio.demo.interfacesService.IIngresoPersonalService;
import com.colegio.demo.modelo.IngresoPersonal;
import com.colegio.demo.utils.MensajeResponse;

import jakarta.validation.Valid;
import net.sf.jasperreports.engine.JRException;

@RestController
@RequestMapping(value = "ControladorIngreso", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorIngreso {
	@Autowired
	private IIngresoPersonalService serviceI;

	@Autowired
	private ModelMapper mapper;

	private List<IngresoPersonal> listarIngresoPorFecha(LocalDate fecha) {
		List<IngresoPersonal> ingresosPorFecha = serviceI.listarIngresoPorFecha(fecha);
		asignarNumeroDeRegistro(ingresosPorFecha);
		return ingresosPorFecha;
	}

	private List<IngresoPersonal> listarIngresoPorID(Integer id_persona) {
		List<IngresoPersonal> ingresosPorID = serviceI.BuscarPersonaId(id_persona);
		asignarNumeroDeRegistro(ingresosPorID);
		return ingresosPorID;
	}

	private void asignarNumeroDeRegistro(List<IngresoPersonal> ingresos) {
		for (int i = 0; i < ingresos.size(); i++) {
			ingresos.get(i).setNumero_registro(i + 1);
		}
	}

	@GetMapping("/listarIngreso")
	public ResponseEntity<?> listarIngreso(@RequestParam(name = "fecha", required = false) String fechaBusqueda,
			@RequestParam(name = "idPersona", required = false) Integer id_persona,
			@RequestParam(name = "tipoPersona", required = false) String tipoPersona) {
		try {
			List<IngresoPersonal> ingresos;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				ingresos = listarIngresoPorFechaConTipo(fecha, tipoPersona);
			} else if (id_persona != null) {
				ingresos = listarIngresoPorIDConTipo(id_persona, tipoPersona);
			} else {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("Debe proporcionarse una fecha o un ID de persona.")
								.object(null)
								.build(),
						HttpStatus.BAD_REQUEST);
			}

			if (ingresos.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay ingresos registrados para los parametros proporcionados.")
								.object(null)
								.build(),
						HttpStatus.OK);
			}

			List<IngresoPersonalDTO> ingresosDTO = ingresos.stream()
					.map(ingreso -> mapper.map(ingreso, IngresoPersonalDTO.class))
					.collect(Collectors.toList());

			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Registros encontrados")
							.object(ingresosDTO)
							.build(),
					HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Error interno del servidor")
							.object(e.getMessage())
							.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private List<IngresoPersonal> listarIngresoPorFechaConTipo(LocalDate fecha, String tipoPersona) {
		List<IngresoPersonal> ingresos;
		if (tipoPersona != null) {
			switch (tipoPersona.toUpperCase()) {
				case "TRABAJADOR":
					ingresos = serviceI.listarIngresoPorFechaTrabajador(fecha);
					break;
				case "PADRE":
					ingresos = serviceI.listarIngresoPorFechaPadre(fecha);
					break;
				case "EXTERNO":
					ingresos = serviceI.listarIngresoPorFechaExterno(fecha);
					break;
				default:
					ingresos = serviceI.listarIngresoPorFecha(fecha);
			}
		} else {
			ingresos = serviceI.listarIngresoPorFecha(fecha);
		}
		asignarNumeroDeRegistro(ingresos);
		return ingresos;
	}

	private List<IngresoPersonal> listarIngresoPorIDConTipo(Integer idPersona, String tipoPersona) {
		List<IngresoPersonal> ingresos;
		if (tipoPersona != null) {
			switch (tipoPersona.toUpperCase()) {
				case "TRABAJADOR":
					ingresos = serviceI.BuscarPersonaIdTrabajador(idPersona);
					break;
				case "PADRE":
					ingresos = serviceI.BuscarPersonaIdPadre(idPersona);
					break;
				case "EXTERNO":
					ingresos = serviceI.BuscarPersonaIdExterno(idPersona);
					break;
				default:
					ingresos = serviceI.BuscarPersonaId(idPersona);
			}
		} else {
			ingresos = serviceI.BuscarPersonaId(idPersona);
		}
		asignarNumeroDeRegistro(ingresos);
		return ingresos;
	}

	@PostMapping(value = "/saveIngreso", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarI(@Valid @RequestBody IngresoPersonalDTO bean) {
		try {
			IngresoPersonal ip = mapper.map(bean, IngresoPersonal.class);
			IngresoPersonal ip1 = serviceI.Guardar(ip);
			IngresoPersonalDTO ipdto = mapper.map(ip1, IngresoPersonalDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso")
					.object(ipdto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateIngreso", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarI(@Valid @RequestBody IngresoPersonalDTO bean) {
		try {
			IngresoPersonal ip = mapper.map(bean, IngresoPersonal.class);
			IngresoPersonal ip1 = serviceI.Guardar(ip);
			IngresoPersonalDTO ipdto = mapper.map(ip1, IngresoPersonalDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizo correctamente el ingreso")
					.object(ipdto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarIngreso/{id}")
	public ResponseEntity<?> editarI(@PathVariable("id") int id_ingreso) {
		IngresoPersonal ip = serviceI.listarId(id_ingreso);
		if (ip == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Ingreso no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		IngresoPersonalDTO ipdto = mapper.map(ip, IngresoPersonalDTO.class);
		return new ResponseEntity<>(ipdto, HttpStatus.OK);
	}

	@DeleteMapping("/eliminarIngreso/{id}")
	public ResponseEntity<?> deleteI(@PathVariable("id") int id_ingreso) {
		try {
			IngresoPersonal ip = serviceI.listarId(id_ingreso);
			if (ip == null) {
				return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			serviceI.Borrar(id_ingreso);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Eliminado correctamente").object(ip).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/export-pdf/{id}", produces = "application/pdf")
	public ResponseEntity<byte[]> exportPdfID(@PathVariable("id") int id_persona)
			throws JRException, FileNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.setContentDispositionFormData("ingresoReport", "ingresoReport_" + id_persona + ".pdf");
		byte[] pdfReport = serviceI.exportPdfID(id_persona);
		return ResponseEntity.ok().headers(headers).body(pdfReport);
	}

	@GetMapping(value = "/export-excel/{id}", produces = "application/vnd.ms-excel")
	public ResponseEntity<byte[]> exportExcelID(@PathVariable("id") int id_persona)
			throws JRException, FileNotFoundException {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
		headers.setContentDispositionFormData("ingresoReport", "ingresoReport_" + id_persona + ".xls");
		byte[] excelReport = serviceI.exportExcelID(id_persona);
		return ResponseEntity.ok().headers(headers).body(excelReport);
	}

	@GetMapping("/consultarHoras")
	public ResponseEntity<?> consultarHoras(
			@RequestParam String tipoPeriodo,
			@RequestParam String fecha,
			@RequestParam Integer idPersona) {
		try {
			LocalDate fechaBase = LocalDate.parse(fecha);
			long horas = serviceI.CalcularHorasTrabajadas(tipoPeriodo, fechaBase, idPersona);
			return ResponseEntity.ok(
					MensajeResponse.builder()
							.mensaje("Horas trabajadas calculadas correctamente")
							.object(horas)
							.build());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					MensajeResponse.builder()
							.mensaje("Error al calcular horas")
							.object(e.getMessage())
							.build());
		}
	}

	@GetMapping("/consultarHorasDetalle")
	public ResponseEntity<?> consultarHorasDetalle(
			@RequestParam Integer idPersona,
			@RequestParam String fechaInicio,
			@RequestParam String fechaFin) {
		try {
			LocalDate inicio = LocalDate.parse(fechaInicio);
			LocalDate fin = LocalDate.parse(fechaFin);
			Map<String, Object> resultado = serviceI.consultarHorasDetalle(idPersona, inicio, fin);
			return ResponseEntity.ok(
					MensajeResponse.builder()
							.mensaje("Consulta de horas detallada exitosa")
							.object(resultado)
							.build());
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
					MensajeResponse.builder()
							.mensaje("Error al consultar horas detalladas: " + e.getClass().getSimpleName())
							.object(e.getMessage() != null ? e.getMessage() : "Sin detalle")
							.build());
		}
	}
}
