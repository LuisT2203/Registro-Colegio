package com.colegio.demo.controler;

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

import com.colegio.demo.Dto.AlumnaDTO;
import com.colegio.demo.Dto.PadreApoderadoDTO;
import com.colegio.demo.Dto.PersonaDTO;
import com.colegio.demo.Dto.TrabajadorDTO;
import com.colegio.demo.interfacesService.IPersonaService;
import com.colegio.demo.modelo.Alumna;
import com.colegio.demo.modelo.PadreApoderado;
import com.colegio.demo.modelo.Persona;
import com.colegio.demo.modelo.Trabajador;
import com.colegio.demo.service.PersonaService;
import com.colegio.demo.utils.MensajeResponse;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "ControladorPersona", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPersona {
	@Autowired
	private PersonaService service;

	@Autowired
	private ModelMapper mapper;

	@GetMapping("/listarTrabajadores")
	public ResponseEntity<?> listarTrabajadores(
			@RequestParam(required = false) String cargo,
			@RequestParam(required = false) String nombre,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		try {
			Map<String, Object> resultado = service.listarTrabajadoresFiltrado(cargo, nombre, page, size);
			List<?> content = (List<?>) resultado.get("content");
			if (content.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay trabajadores").object(null).build(),
						HttpStatus.OK);
			}
			List<TrabajadorDTO> listaDTO = content.stream()
					.map(m -> mapper.map(m, TrabajadorDTO.class))
					.collect(Collectors.toList());
			resultado.put("content", listaDTO);
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Trabajadores encontrados").object(resultado).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarAlumnas")
	public ResponseEntity<?> listarAlumnas(
			@RequestParam(required = false) String grado,
			@RequestParam(required = false) String nivel,
			@RequestParam(required = false) String seccion,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "30") int size) {
		try {
			boolean conFiltros = (grado != null && !grado.isEmpty())
					&& (nivel != null && !nivel.isEmpty())
					&& (seccion != null && !seccion.isEmpty());

			if (conFiltros) {
				Map<String, Object> resultado = service.listarAlumnasFiltrado(grado, nivel, seccion, page, size);
				List<?> content = (List<?>) resultado.get("content");
				List<AlumnaDTO> listaDTO = content.stream()
						.map(m -> mapper.map(m, AlumnaDTO.class))
						.collect(Collectors.toList());
				resultado.put("content", listaDTO);
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("Alumnas encontradas").object(resultado).build(),
						HttpStatus.OK);
			}

			List<Alumna> lista = service.listarAlumnas();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay alumnas").object(null).build(),
						HttpStatus.OK);
			}
			List<AlumnaDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, AlumnaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Alumnas encontradas").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarPadres")
	public ResponseEntity<?> listarPadres(
			@RequestParam(required = false) String nombre,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		try {
			Map<String, Object> resultado = service.listarPadresFiltrado(nombre, page, size);
			List<?> content = (List<?>) resultado.get("content");
			if (content.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay padres").object(null).build(),
						HttpStatus.OK);
			}
			List<PadreApoderadoDTO> listaDTO = content.stream()
					.map(m -> mapper.map(m, PadreApoderadoDTO.class))
					.collect(Collectors.toList());
			resultado.put("content", listaDTO);
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Padres encontrados").object(resultado).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarExternos")
	public ResponseEntity<?> listarExternos() {
		try {
			List<Persona> lista = service.listarExternos();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay externos").object(null).build(),
						HttpStatus.NO_CONTENT);
			}
			List<PersonaDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, PersonaDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Externos encontrados").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarTodosTrabajadores")
	public ResponseEntity<?> listarTodosTrabajadores() {
		try {
			List<Trabajador> lista = service.listarTrabajadores();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay trabajadores").object(null).build(),
						HttpStatus.OK);
			}
			List<TrabajadorDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, TrabajadorDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Trabajadores encontrados").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarTodosPadres")
	public ResponseEntity<?> listarTodosPadres() {
		try {
			List<PadreApoderado> lista = service.listarPadres();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay padres").object(null).build(),
						HttpStatus.OK);
			}
			List<PadreApoderadoDTO> listaDTO = lista.stream()
					.map(m -> mapper.map(m, PadreApoderadoDTO.class))
					.collect(Collectors.toList());
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Padres encontrados").object(listaDTO).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(value = "/savePersona", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardar(@Valid @RequestBody PersonaDTO bean) {
		try {
			Persona p = mapearPersonaPorTipo(bean);// Mapear el DTO a la clase correspondiente según el tipo y conservar ese mapeo como Persona
			Persona p1 = service.Guardar(p);// Guardar la persona en la base de datos
			PersonaDTO pdto = mapper.map(p1, PersonaDTO.class);//Convertir la persona guardada de nuevo a DTO para la respuesta
			return new ResponseEntity<>(MensajeResponse.builder() 
					.mensaje("Se agrego correctamente la persona")
					.object(pdto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/updatePersona", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizar(@Valid @RequestBody PersonaDTO bean) {
		try {
			Persona p = mapearPersonaPorTipo(bean);
			Persona p1 = service.Guardar(p);
			PersonaDTO pdto = mapper.map(p1, PersonaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizo correctamente la persona")
					.object(pdto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarPersona/{id}")
	public ResponseEntity<?> editar(@PathVariable("id") int id_persona) {
		Persona p = service.listarId(id_persona);
		if (p == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Persona no encontrada").object(null).build(), HttpStatus.NOT_FOUND);
		}
		PersonaDTO pdto = mapper.map(p, PersonaDTO.class);
		pdto.setTipo(obtenerTipoPersona(p));
		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	@DeleteMapping("/eliminarPersona/{id}")
	public ResponseEntity<?> eliminar(@PathVariable("id") int id_persona) {
		try {
			Persona p = service.listarId(id_persona);
			if (p == null) {
				return new ResponseEntity<>(MensajeResponse.builder()
						.mensaje("Id no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			service.Borrar(id_persona);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Eliminado correctamente").object(p).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/buscarPorDni/{dni}")
	public ResponseEntity<?> buscarPorDni(@PathVariable("dni") String dni) {
		Persona p = service.buscarPorDni(dni);
		if (p == null) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Persona no encontrada").object(null).build(), HttpStatus.NOT_FOUND);
		}
		PersonaDTO pdto = mapper.map(p, PersonaDTO.class);
		return new ResponseEntity<>(pdto, HttpStatus.OK);
	}

	private Persona mapearPersonaPorTipo(PersonaDTO dto) {
		if (dto.getTipo() == null) {
			return mapper.map(dto, Persona.class);
		}
		switch (dto.getTipo().toUpperCase()) {
			case "TRABAJADOR":
				return mapper.map(dto, Trabajador.class);
			case "ALUMNA":
				return mapper.map(dto, Alumna.class);
			case "PADRE":
				return mapper.map(dto, PadreApoderado.class);
			default:
				return mapper.map(dto, Persona.class);
		}
	}

	private String obtenerTipoPersona(Persona p) {
		if (p instanceof Trabajador) return "TRABAJADOR";
		if (p instanceof Alumna) return "ALUMNA";
		if (p instanceof PadreApoderado) return "PADRE";
		return "EXTERNO";
	}
}
