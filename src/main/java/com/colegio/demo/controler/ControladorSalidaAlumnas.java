package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
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

import com.colegio.demo.Dto.SalidaAlumnasDTO;
import com.colegio.demo.interfacesService.ISalidaAlumnasService;
import com.colegio.demo.modelo.SalidasAlumnas;
import com.colegio.demo.utils.MensajeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "ControladorSA", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorSalidaAlumnas {
	@Autowired
	private ISalidaAlumnasService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/listarSalidas")
	public ResponseEntity<?> listarSalidas(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "idAlumna", required = false) Integer idAlumna) {
		try {
			List<SalidasAlumnas> salidas;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				salidas = listarSalidasPorFecha(fecha);
			} else if (idAlumna != null) {
				salidas = listarSalidasPorAlumna(idAlumna);
			} else {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("Debe proporcionarse una fecha o un ID de alumna.")
								.object(null)
								.build(),
						HttpStatus.BAD_REQUEST);
			}

			if (salidas.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay salidas registradas para los parametros proporcionados.")
								.object(null)
								.build(),
						HttpStatus.NO_CONTENT);
			}

			List<SalidaAlumnasDTO> salidasDTO = salidas.stream()
					.map(salida -> {
						SalidaAlumnasDTO dto = mapper.map(salida, SalidaAlumnasDTO.class);
						Map<String, Object> extra = service.enriquecerParentesco(salida);
						if (extra.containsKey("parentesco")) {
							dto.setParentesco((String) extra.get("parentesco"));
						}
						if (extra.containsKey("dni")) {
							dto.setDNI((String) extra.get("dni"));
						}
						if (extra.containsKey("personaquerecoje")) {
							dto.setPersonaquerecoje((String) extra.get("personaquerecoje"));
						}
						return dto;
					})
					.collect(Collectors.toList());

			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Registros encontrados")
							.object(salidasDTO)
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

	private List<SalidasAlumnas> listarSalidasPorAlumna(int idAlumna) {
		List<SalidasAlumnas> salidaPorAlumna = service.ListarSalidaPorAlumna(idAlumna);
		asignarNumeroDeRegistro(salidaPorAlumna);
		return salidaPorAlumna;
	}

	private List<SalidasAlumnas> listarSalidasPorFecha(LocalDate fecha) {
		List<SalidasAlumnas> salidasPorFecha = service.ListarSalidaPorFecha(fecha);
		asignarNumeroDeRegistro(salidasPorFecha);
		return salidasPorFecha;
	}

	private void asignarNumeroDeRegistro(List<SalidasAlumnas> salidas) {
		for (int i = 0; i < salidas.size(); i++) {
			salidas.get(i).setNumeroRegistro(i + 1);
		}
	}

	@PostMapping(value = "/saveSalida", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarS(@Valid @RequestBody SalidaAlumnasDTO bean) {
		try {
			SalidasAlumnas s = mapper.map(bean, SalidasAlumnas.class);
			SalidasAlumnas s1 = service.Guardar(s);
			SalidaAlumnasDTO sadto = mapper.map(s1, SalidaAlumnasDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente la salida")
					.object(sadto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updateSalida", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarS(@Valid @RequestBody SalidaAlumnasDTO bean) {
		try {
			SalidasAlumnas s = mapper.map(bean, SalidasAlumnas.class);
			SalidasAlumnas s1 = service.Guardar(s);
			SalidaAlumnasDTO sadto = mapper.map(s1, SalidaAlumnasDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizo correctamente la salida")
					.object(sadto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarSA/{id}")
	public ResponseEntity<?> editarS(@PathVariable("id") int id_salida) {
		SalidasAlumnas s = service.listarID(id_salida);
		if (s == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Salida no encontrada").object(null).build(), HttpStatus.NOT_FOUND);
		}
		SalidaAlumnasDTO sadto = mapper.map(s, SalidaAlumnasDTO.class);
		return new ResponseEntity<>(sadto, HttpStatus.OK);
	}

	@DeleteMapping("/eliminarSA/{id}")
	public ResponseEntity<?> eliminarS(@PathVariable("id") int id_salida) {
		try {
			SalidasAlumnas s = service.listarID(id_salida);
			if (s == null) {
				return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			service.Borrar(id_salida);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Eliminado correctamente").object(s).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/padresPorAlumna/{idAlumna}")
	public ResponseEntity<?> padresPorAlumna(@PathVariable("idAlumna") int idAlumna) {
		try {
			List<Map<String, Object>> padres = service.listarPadresPorAlumna(idAlumna);
			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Padres encontrados")
							.object(padres)
							.build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Error al obtener padres: " + e.getMessage())
							.object(null)
							.build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
