package com.colegio.demo.controler;


import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.colegio.demo.Dto.IngresoPPFFDTO;
import com.colegio.demo.Dto.IngresoPersonalColegioDTO;
import com.colegio.demo.Dto.PPFFDTO;
import com.colegio.demo.Dto.PersonalColegioDTO;
import com.colegio.demo.modelo.PersonalColegio;
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

import com.colegio.demo.interfacesService.IIngresoPPFFService;
import com.colegio.demo.interfacesService.IppffService;
import com.colegio.demo.modelo.PPFF;
import com.colegio.demo.modelo.IngresoPPFF;
import com.colegio.demo.modelo.IngresoPersonalColegio;



@RestController
@RequestMapping(value = "ControladorPPFF", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPPFF {
	@Autowired
	private IppffService service;
	@Autowired
	private IIngresoPPFFService serviceI;

	@Autowired
	private ModelMapper mapper;
	/// PPFF
	@GetMapping("/listarPPFF")
	public ResponseEntity<?> listar() {
		try {
			List<PPFF> lista = service.listarPPFF();
			if(lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("No hay personales")
								.object(null)
								.build(), HttpStatus.NO_CONTENT);
			}else {
				List<PPFFDTO> lista2 = lista.stream()
						.map(m -> mapper.map(m, PPFFDTO.class))
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


	@PostMapping(value="/savePPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardar(@Valid @RequestBody PPFFDTO bean) {
		try {
			PPFF pf = mapper.map(bean, PPFF.class);
			PPFF pf1 = service.Guardar(pf);
			PPFFDTO pfdto = mapper.map(pf1, PPFFDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el padre")
					.object(pfdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping(value="/updatePPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizar(@Valid @RequestBody PPFFDTO bean) {
		try {
			PPFF pf = mapper.map(bean, PPFF.class);
			PPFF pf1 = service.Guardar(pf);
			PPFFDTO pfdto = mapper.map(pf1, PPFFDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizó correctamente el padre")
					.object(pfdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarPPFF/{Id_ppff}")
	public ResponseEntity<?> editar(@PathVariable ("Id_ppff") int Id_ppff) {
		PPFF pf = service.listarId(Id_ppff);
		if(pf == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Personal no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		PPFFDTO pfdto = mapper.map(pf, PPFFDTO.class);
		return new ResponseEntity<>(pfdto, HttpStatus.OK);
		
	}


	@DeleteMapping("/eliminarPPFF/{Id_ppff}")
	public ResponseEntity<?> delete(@PathVariable ("Id_ppff") int Id_ppff) {
		try {
			PPFF pf = service.listarId(Id_ppff);
			if (pf == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("Id no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			service.Borrar(Id_ppff);
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(pf).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	 //INGRESO PPFF
	

	@GetMapping("/listarIPPFF")
	public ResponseEntity<?> listarIngresoPPFF(
			@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "id_ppff", required = false) Integer id_ppff) {
		try {

			List<IngresoPPFF> ingresos;

			if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
				LocalDate fecha = LocalDate.parse(fechaBusqueda);
				ingresos = listarIngresoPorFecha(fecha);
			} else if (id_ppff != null) {
				ingresos = listarIngresoPorID(id_ppff);
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
			List<IngresoPPFFDTO> ingresosDTO = ingresos.stream()
					.map(ingreso -> mapper.map(ingreso, IngresoPPFFDTO.class))
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

	private List<IngresoPPFF> listarIngresoPorFecha(LocalDate fecha) {
		List<IngresoPPFF> ingresosPorFecha = serviceI.listarIngresoPPFFPorFecha(fecha);
		asignarNumeroDeRegistro(ingresosPorFecha);
		return ingresosPorFecha;
	}

	private List<IngresoPPFF> listarIngresoPorID(Integer id_ppff) {
		List<IngresoPPFF> ingresosPorID = serviceI.BuscarPersonalId(id_ppff);
		asignarNumeroDeRegistro(ingresosPorID);
		return ingresosPorID;
	}

	private void asignarNumeroDeRegistro(List<IngresoPPFF> ingresos) {
		for (int i = 0; i < ingresos.size(); i++) {
			ingresos.get(i).setNumeroRegistro(i + 1);
		}
	}


	@PostMapping(value="/saveIPPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarI(@Valid @RequestBody IngresoPPFFDTO bean) {
		try {
			IngresoPPFF ipf = mapper.map(bean, IngresoPPFF.class);
			IngresoPPFF ipf1 = serviceI.Guardar(ipf);
			IngresoPPFFDTO ipfdto = mapper.map(ipf1, IngresoPPFFDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso padre")
					.object(ipfdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	@PutMapping(value="/updateIPPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarI(@Valid @RequestBody IngresoPPFFDTO bean) {
		try {
			IngresoPPFF ipf = mapper.map(bean, IngresoPPFF.class);
			IngresoPPFF ipf1 = serviceI.Guardar(ipf);
			IngresoPPFFDTO ipfdto = mapper.map(ipf1, IngresoPPFFDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizó correctamente el ingreso padre")
					.object(ipfdto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarIPPFF/{id_ingresoPPFF}")
	public ResponseEntity<?> editarI(@PathVariable ("id_ingresoPPFF") int id_ingresoPPFF) {
		IngresoPPFF ipf = serviceI.listarId(id_ingresoPPFF);
		if(ipf == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Ingreso Padre no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		IngresoPPFF ipfdto = mapper.map(ipf, IngresoPPFF.class);
		return new ResponseEntity<>(ipfdto, HttpStatus.OK);
		
	}


	@DeleteMapping("/eliminarIPPFF/{id_ingresoPPFF}")
	public ResponseEntity<?> deleteI( @PathVariable ("id_ingresoPPFF") int id_ingresoPPFF) {
		try{
			IngresoPPFF ipf = serviceI.listarId(id_ingresoPPFF);
			if(ipf == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			serviceI.Borrar(id_ingresoPPFF);
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(ipf).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
