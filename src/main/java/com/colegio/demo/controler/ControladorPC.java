package com.colegio.demo.controler;

import java.time.LocalDate;



import java.util.List;
import java.util.stream.Collectors;


import com.colegio.demo.Dto.IngresoPersonalColegioDTO;
import com.colegio.demo.Dto.PersonalColegioDTO;
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

import com.colegio.demo.interfacesService.IIngresoPersonalColegioService;
import com.colegio.demo.interfacesService.IPersonalColegioService;
import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.modelo.PersonalColegio;

@RestController
@RequestMapping(value = "ControladorPC", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPC {
	@Autowired
	private IPersonalColegioService service;
	@Autowired
	private IIngresoPersonalColegioService serviceI;

	@Autowired
	private ModelMapper mapper;

	/// Personal Colegio
	@GetMapping("/listarPC")
	public ResponseEntity<?> listarPC() {
		try {
			List<PersonalColegio> lista = service.listarPersonal();
			if(lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay personales")
								.object(null)
								.build(), HttpStatus.NO_CONTENT);
			}else {
				List<PersonalColegioDTO> lista2 = lista.stream()
						.map(m -> mapper.map(m, PersonalColegioDTO.class))
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


	@PostMapping(value="/savePC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardar(@Valid @RequestBody PersonalColegioDTO bean) {

		try {
			PersonalColegio pc = mapper.map(bean, PersonalColegio.class);
			PersonalColegio pc1 = service.Guardar(pc);
			PersonalColegioDTO pcdto = mapper.map(pc1, PersonalColegioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el personal")
					.object(pcdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	@PutMapping(value="/updatePC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizar(@Valid @RequestBody PersonalColegioDTO bean) {

		try {
			PersonalColegio pc = mapper.map(bean, PersonalColegio.class);
			PersonalColegio pc1 = service.Guardar(pc);
			PersonalColegioDTO pcdto = mapper.map(pc1, PersonalColegioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizó correctamente el personal")
					.object(pcdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/editarPC/{id_personal}")
	public ResponseEntity<?> editar(@PathVariable ("id_personal") int id_personal) {
		PersonalColegio pc = service.listarId(id_personal);
		if(pc == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Personal no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		PersonalColegioDTO pcdto = mapper.map(pc, PersonalColegioDTO.class);
		return new ResponseEntity<>(pcdto, HttpStatus.OK);
		
	}


	@DeleteMapping("/eliminarPC/{id_personal}")
	public ResponseEntity<?> eliminar(@PathVariable ("id_personal") int id_personal) {
		try {
			PersonalColegio pc = service.listarId(id_personal);
			if (pc == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("Id no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			} else {
				service.Borrar(id_personal);
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(pc).build(), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}



	}

	

	@GetMapping("/listarIngresoPC")
	public ResponseEntity<?> listarIngresoPC(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
	                                                    @RequestParam(name = "id_personal", required = false) Integer id_personal) {
		try {

			List<IngresoPersonalColegio> ingresos;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				ingresos = listarIngresoPorFecha(fecha);
			} else if (id_personal != null) {
				ingresos = listarIngresoPorID(id_personal);
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
								HttpStatus.NO_CONTENT
						);
					}
					// Convertir a DTO
					List<IngresoPersonalColegioDTO> ingresosDTO = ingresos.stream()
							.map(ingreso -> mapper.map(ingreso, IngresoPersonalColegioDTO.class))
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

	private List<IngresoPersonalColegio> listarIngresoPorFecha(LocalDate fecha) {
	    List<IngresoPersonalColegio> ingresosPorFecha = serviceI.listarIngresoPCPorFecha(fecha);
	    asignarNumeroDeRegistro(ingresosPorFecha);
	    return ingresosPorFecha;
	}

	private List<IngresoPersonalColegio> listarIngresoPorID(Integer id_personal) {
	    List<IngresoPersonalColegio> ingresosPorID = serviceI.BuscarPersonalId(id_personal);
	    asignarNumeroDeRegistro(ingresosPorID);
	    return ingresosPorID;
	}

	private void asignarNumeroDeRegistro(List<IngresoPersonalColegio> ingresos) {
	    for (int i = 0; i < ingresos.size(); i++) {
	        ingresos.get(i).setNumeroRegistro(i + 1);
	    }
	}


	
	@PostMapping(value="/saveIPC", consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarI(@Valid @RequestBody IngresoPersonalColegioDTO bean) {
		try {
			IngresoPersonalColegio ipc = mapper.map(bean, IngresoPersonalColegio.class);
			IngresoPersonalColegio ipc1 = serviceI.Guardar(ipc);
			IngresoPersonalColegioDTO ipcdto = mapper.map(ipc1, IngresoPersonalColegioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso personal")
					.object(ipcdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/updateIPC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarI(@Valid @RequestBody IngresoPersonalColegioDTO bean) {
		try {
			IngresoPersonalColegio ipc = mapper.map(bean, IngresoPersonalColegio.class);
			IngresoPersonalColegio ipc1 = serviceI.Guardar(ipc);
			IngresoPersonalColegioDTO ipcdto = mapper.map(ipc1, IngresoPersonalColegioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizó correctamente el ingreso personal")
					.object(ipcdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarIPC/{id_ingresoPersonal}")
	public ResponseEntity<?> editarI(@PathVariable ("id_ingresoPersonal") int id_ingresoPersonal) {
		IngresoPersonalColegio ipc = serviceI.listarId(id_ingresoPersonal);
		if(ipc == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Ingreso Personal no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		IngresoPersonalColegioDTO ipcdto = mapper.map(ipc, IngresoPersonalColegioDTO.class);
		return new ResponseEntity<>(ipcdto, HttpStatus.OK);
		
		
	}

	@DeleteMapping("/eliminarIPC/{id_ingresoPersonal}")
	public ResponseEntity<?> deleteI(@PathVariable ("id_ingresoPersonal") int id_ingresoPersonal) {
		try{
		IngresoPersonalColegio ipc = serviceI.listarId(id_ingresoPersonal);
		if(ipc == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		serviceI.Borrar(id_ingresoPersonal);
		return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(ipc).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
