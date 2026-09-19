package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.colegio.demo.Dto.EncargoDTO;
import com.colegio.demo.interfacesService.IEncargoService;
import com.colegio.demo.modelo.Encargo;
import com.colegio.demo.utils.MensajeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "ControladorEncargo", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorEncargo {

	@Autowired
	private IEncargoService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/listarEncargos")
	public ResponseEntity<?> listarEncargos(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "encargo", required = false) String encargo) {
		try {
			List<Encargo> encargos;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				encargos = listarEncargoPorFecha(fecha);
			} else if (encargo != null) {
				encargos = listarEncargosPorNombre(encargo);
			} else {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("Debe proporcionarse una fecha o un nombre de encargo.")
								.object(null)
								.build(),
						HttpStatus.BAD_REQUEST);
			}

			if (encargos.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay encargos registrados para los parametros proporcionados.")
								.object(null)
								.build(),
						HttpStatus.NO_CONTENT);
			}

			List<EncargoDTO> encargosDTO = encargos.stream()
					.map(e -> mapper.map(e, EncargoDTO.class))
					.collect(Collectors.toList());

			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Registros encontrados")
							.object(encargosDTO)
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

	private List<Encargo> listarEncargosPorNombre(String encargo) {
		List<Encargo> encargosPorNombre = service.ListarEncargoPorNombre(encargo);
		asignarNumeroDeRegistro(encargosPorNombre);
		return encargosPorNombre;
	}

	private List<Encargo> listarEncargoPorFecha(LocalDate fecha) {
		List<Encargo> encargoPorFecha = service.ListarEncargoPorFecha(fecha);
		asignarNumeroDeRegistro(encargoPorFecha);
		return encargoPorFecha;
	}

	private void asignarNumeroDeRegistro(List<Encargo> encargos) {
		for (int i = 0; i < encargos.size(); i++) {
			encargos.get(i).setNumero_registro(i + 1);
		}
	}

	@PostMapping(value = "/saveEncargo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarE(@Valid @RequestBody EncargoDTO bean) {
		try {
			Encargo e = mapper.map(bean, Encargo.class);
			Encargo e1 = service.Guardar(e);
			EncargoDTO edto = mapper.map(e1, EncargoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el encargo")
					.object(edto).build(), HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(ex.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateEncargo", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarE(@Valid @RequestBody EncargoDTO bean) {
		try {
			Encargo e = mapper.map(bean, Encargo.class);
			Encargo e1 = service.Guardar(e);
			EncargoDTO edto = mapper.map(e1, EncargoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizo correctamente el encargo")
					.object(edto).build(), HttpStatus.CREATED);
		} catch (Exception ex) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(ex.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarE/{id}")
	public ResponseEntity<?> editarE(@PathVariable("id") int id_enc) {
		Encargo e = service.listarID(id_enc);
		if (e == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Encargo no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		EncargoDTO edto = mapper.map(e, EncargoDTO.class);
		return new ResponseEntity<>(edto, HttpStatus.OK);
	}

	@DeleteMapping("/eliminarE/{id}")
	public ResponseEntity<?> eliminarE(@PathVariable("id") int id_enc) {
		try {
			Encargo e = service.listarID(id_enc);
			if (e == null) {
				return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			service.Borrar(id_enc);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Eliminado correctamente").object(e).build(), HttpStatus.OK);
		} catch (Exception ex) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
