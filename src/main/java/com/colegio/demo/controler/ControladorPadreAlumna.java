package com.colegio.demo.controler;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.demo.Dto.PadreAlumnaDTO;
import com.colegio.demo.interfacesService.IPadreAlumnaService;
import com.colegio.demo.modelo.PadreAlumna;
import com.colegio.demo.utils.MensajeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "ControladorPadreAlumna", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPadreAlumna {
	@Autowired
	private IPadreAlumnaService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/listarPorPadre/{idPadre}")
	public ResponseEntity<?> listarPorPadre(@PathVariable("idPadre") int idPadre) {
		try {
			List<PadreAlumna> lista = service.listarPorPadre(idPadre);
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No tiene alumnas asignadas").object(null).build(),
						HttpStatus.NO_CONTENT);
			}
			List<PadreAlumnaDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, PadreAlumnaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Alumnas encontradas").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarPorAlumna/{idAlumna}")
	public ResponseEntity<?> listarPorAlumna(@PathVariable("idAlumna") int idAlumna) {
		try {
			List<PadreAlumna> lista = service.listarPorAlumna(idAlumna);
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No tiene padres asignados").object(null).build(),
						HttpStatus.NO_CONTENT);
			}
			List<PadreAlumnaDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, PadreAlumnaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Padres encontrados").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/asignarAlumna", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardar(@Valid @RequestBody PadreAlumnaDTO bean) {
		try {
			PadreAlumna pa = mapper.map(bean, PadreAlumna.class);
			PadreAlumna pa1 = service.Guardar(pa);
			PadreAlumnaDTO padto = mapper.map(pa1, PadreAlumnaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se asigno correctamente")
					.object(padto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/removerAlumna/{idPadre}/{idAlumna}")
	public ResponseEntity<?> eliminar(@PathVariable("idPadre") int idPadre,
			@PathVariable("idAlumna") int idAlumna) {
		try {
			service.Borrar(idPadre, idAlumna);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Relacion eliminada correctamente").object(null).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarTodas")
	public ResponseEntity<?> listarTodas() {
		try {
			List<PadreAlumna> lista = service.listarTodas();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay relaciones").object(null).build(),
						HttpStatus.NO_CONTENT);
			}
			List<PadreAlumnaDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, PadreAlumnaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Relaciones encontradas").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
