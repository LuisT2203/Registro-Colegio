package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.colegio.demo.Dto.IngresoPersonaExternaDTO;
import com.colegio.demo.Dto.IngresoPersonalColegioDTO;
import com.colegio.demo.Dto.PersonaExternaDTO;
import com.colegio.demo.Dto.PersonalColegioDTO;
import com.colegio.demo.modelo.*;
import com.colegio.demo.utils.MensajeResponse;
import com.colegio.demo.utils.ModeloNotFoundException;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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
import com.colegio.demo.interfacesService.IIngresoPersonaExternaService;
import com.colegio.demo.interfacesService.IPersonaExternaService;


@RestController
@RequestMapping(value = "ControladorPE", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPE {
	@Autowired
	private IPersonaExternaService service;
	@Autowired
	private IIngresoPersonaExternaService serviceI;

	@Autowired
	private ModelMapper mapper;
	
	///Persona Externa
	@GetMapping("/listarPE")
	public ResponseEntity<?> listarPE() {
		try {
			List<PersonaExterna> lista = service.listarPersonaE();
			if(lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay personas")
								.object(null)
								.build(), HttpStatus.NO_CONTENT);
			}else {
				List<PersonaExternaDTO> lista2 = lista.stream()
						.map(m -> mapper.map(m, PersonaExternaDTO.class))
						.collect(Collectors.toList());
				return new ResponseEntity<> (
						MensajeResponse.builder()
								.mensaje("Si hay registro de personales")
								.object(lista2)
								.build(), HttpStatus.OK);

			}
		}catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}

	
	@PostMapping(value="/savePE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardar(@Valid @RequestBody PersonaExternaDTO bean) {
		try {
			PersonaExterna pe = mapper.map(bean, PersonaExterna.class);
			PersonaExterna pe1 = service.Guardar(pe);
			PersonaExternaDTO pedto = mapper.map(pe1, PersonaExternaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el personal")
					.object(pedto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value="/updatePE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> Actualizar(@Valid @RequestBody PersonaExternaDTO bean) {
		try {
			PersonaExterna pe = mapper.map(bean, PersonaExterna.class);
			PersonaExterna pe1 = service.Guardar(pe);
			PersonaExternaDTO pedto = mapper.map(pe1, PersonaExternaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el personal")
					.object(pedto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	
	@GetMapping("/editarPE/{id_personaE}")
	public ResponseEntity<?> editar(@PathVariable ("id_personaE") int id_personaE) {
		PersonaExterna pe = service.listarId(id_personaE);
		if(pe == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Persona no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		PersonaExternaDTO pedto = mapper.map(pe, PersonaExternaDTO.class);
		return new ResponseEntity<>(pedto, HttpStatus.OK);
		
	}


	
	@DeleteMapping("/eliminarPE/{id_personaE}")
	public ResponseEntity<?> delete( @PathVariable ("id_personaE") int id_personaE) {
		try {
			PersonaExterna pe = service.listarId(id_personaE);
			if (pe == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			} else {
				service.Borrar(id_personaE);
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(pe).build(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	//INGRESO PERSONA EXTERNA//
	@GetMapping("/listarIngresoPE")
	public ResponseEntity<?> listarIngresoPPFF(
			@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "id_personaE", required = false) Integer id_personaE) {
		try {

			List<IngresoPersonaExterna> ingresos;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				ingresos = listarIngresoPorFecha(fecha);
			} else if (id_personaE != null) {
				ingresos = listarIngresoPorID(id_personaE);
			} else {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("Debe proporcionarse una fecha o un ID de personal.")
								.object(null)
								.build(),
						HttpStatus.BAD_REQUEST
				);
			}
			// Si no hay resultados
			if (ingresos.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay ingresos registrados para los parámetros proporcionados.")
								.object(null)
								.build(),
						HttpStatus.OK
				);
			}
			// Convertir a DTO
			List<IngresoPersonaExternaDTO> ingresosDTO = ingresos.stream()
					.map(ingreso -> mapper.map(ingreso, IngresoPersonaExternaDTO.class))
					.collect(Collectors.toList());

			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Registros encontrados")
							.object(ingresosDTO)
							.build(),
					HttpStatus.OK
			);

		} catch (Exception e) {
			return new ResponseEntity<>(
					MensajeResponse.builder()
							.mensaje("Error interno del servidor")
							.object(e.getMessage())
							.build(),
					HttpStatus.INTERNAL_SERVER_ERROR
			);
		}
	}

	private List<IngresoPersonaExterna> listarIngresoPorFecha(LocalDate fecha) {
		List<IngresoPersonaExterna> ingresosPorFecha = serviceI.listarIngresoPEPorFecha(fecha);
		asignarNumeroDeRegistro(ingresosPorFecha);
		return ingresosPorFecha;
	}

	private List<IngresoPersonaExterna> listarIngresoPorID(Integer id_personaE) {
		List<IngresoPersonaExterna> ingresosPorID = serviceI.BuscarPersonalId(id_personaE);
		asignarNumeroDeRegistro(ingresosPorID);
		return ingresosPorID;
	}
	private void asignarNumeroDeRegistro(List<IngresoPersonaExterna> ingresos) {
	    for (int i = 0; i < ingresos.size(); i++) {
	        ingresos.get(i).setNumeroRegistro(i + 1);
	    }
	}
	
	

	@PostMapping(value="/saveIPE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarI(@Valid @RequestBody IngresoPersonaExternaDTO bean) {
		try {
			IngresoPersonaExterna ipe = mapper.map(bean, IngresoPersonaExterna.class);
			IngresoPersonaExterna ipe1 = serviceI.Guardar(ipe);
			IngresoPersonaExternaDTO ipedto = mapper.map(ipe1, IngresoPersonaExternaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso personal")
					.object(ipedto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/updateIPE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarI(@Valid @RequestBody IngresoPersonaExternaDTO bean) {
		try {
			IngresoPersonaExterna ipe = mapper.map(bean, IngresoPersonaExterna.class);
			IngresoPersonaExterna ipe1 = serviceI.Guardar(ipe);
			IngresoPersonaExternaDTO ipedto = mapper.map(ipe1, IngresoPersonaExternaDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso personal")
					.object(ipedto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/editarIPE/{id_ingresoPersonaE}")
	public ResponseEntity<?> editarI(@PathVariable ("id_ingresoPersonaE") int id_ingresoPersonaE) {
		IngresoPersonaExterna ipe = serviceI.listarId(id_ingresoPersonaE);
		if(ipe == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Ingreso Persona Externa no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		IngresoPersonaExternaDTO ipedto = mapper.map(ipe, IngresoPersonaExternaDTO.class);
		return new ResponseEntity<>(ipedto, HttpStatus.OK);
		
	
	}

	@DeleteMapping("/eliminarIPE/{id_ingresoPersonaE}")
	public ResponseEntity<?>  deleteI(@PathVariable ("id_ingresoPersonaE") int id_ingresoPersonaE) {
		try {
			IngresoPersonaExterna ipe = serviceI.listarId(id_ingresoPersonaE);
			if (ipe == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			serviceI.Borrar(id_ingresoPersonaE);
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(ipe).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
