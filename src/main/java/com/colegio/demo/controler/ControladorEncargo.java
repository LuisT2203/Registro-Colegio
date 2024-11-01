package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import com.colegio.demo.Dto.EncargoDTO;
import com.colegio.demo.Dto.IngresoPersonalColegioDTO;
import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.utils.MensajeResponse;
import com.colegio.demo.utils.ModeloNotFoundException;
import jakarta.validation.Valid;
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

import com.colegio.demo.interfacesService.IEncargoService;
import com.colegio.demo.modelo.Encargo;

@RestController
@RequestMapping(value = "ControladorEncargo", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorEncargo {
	
	@Autowired
	private IEncargoService service;

	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/listarEncargos")
	public ResponseEntity<?> listarEncargos(@RequestParam (name="fechaBusqueda",required=false)String fechaBusqueda,
									@RequestParam(name = "encargoNom", required = false) String encargoNom){
		try{
				List<Encargo> encargos;

				if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
					LocalDate fecha = LocalDate.parse(fechaBusqueda);
					encargos = listarEncargoPorFecha(fecha);
				} else if (encargoNom != null) {
					encargos = listarEncargosPorNombre(encargoNom);
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
				if (encargos.isEmpty()) {
					return new ResponseEntity<>(
							MensajeResponse.builder()
									.mensaje("No hay ingresos registrados para los parámetros proporcionados.")
									.object(null)
									.build(),
							HttpStatus.NO_CONTENT
					);
				}
				// Convertir a DTO
				List<EncargoDTO> encargosDTO = encargos.stream()
						.map(ingreso -> mapper.map(ingreso, EncargoDTO.class))
						.collect(Collectors.toList());

				return new ResponseEntity<>(
						MensajeResponse.builder()
								.mensaje("Registros encontrados")
								.object(encargosDTO)
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
	
	private List<Encargo> listarEncargosPorNombre(String Encargo) {
	    List<Encargo> encargosPorNombre = service.ListarEncargoPorNombre(Encargo);
	    asignarNumeroDeRegistro(encargosPorNombre);
	    return encargosPorNombre;
	}
	
	private List<Encargo> listarEncargoPorFecha(LocalDate fecha){
		List<Encargo> encargoPorFecha = service.ListarEncargoPorFecha(fecha);
		asignarNumeroDeRegistro(encargoPorFecha);
		return encargoPorFecha;
	}
	
	private void asignarNumeroDeRegistro(List<Encargo> encargos) {
	    for (int i = 0; i < encargos.size(); i++) {
	    	encargos.get(i).setNumeroRegistro(i + 1);
	    }
	}
	
	@PostMapping(value="/saveEncargo", consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> guardarE(@Valid @RequestBody EncargoDTO bean) {
		try {
			Encargo e = mapper.map(bean, Encargo.class);
			Encargo e1 = service.Guardar(e);
			EncargoDTO edto = mapper.map(e1, EncargoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso personal")
					.object(edto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping(value="/updateEncargo", consumes= MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarE(@Valid @RequestBody Encargo bean) {
		try {
			Encargo e = mapper.map(bean, Encargo.class);
			Encargo e1 = service.Guardar(e);
			EncargoDTO edto = mapper.map(e1, EncargoDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el ingreso personal")
					.object(edto).build(),HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/editarE/{id_enc}")
	public ResponseEntity<?> editarE(@PathVariable ("id_enc") int id_enc) {
		Encargo e = service.listarID(id_enc);
		if(e == null) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Ingreso Personal no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
		}
		EncargoDTO edto = mapper.map(e, EncargoDTO.class);
		return new ResponseEntity<>(edto, HttpStatus.OK);
	}

	@DeleteMapping("/eliminarE/{id_enc}")
	public ResponseEntity<?> eliminarE(@PathVariable ("id_enc") int id_enc) {
		try {
			Encargo e = service.listarID(id_enc);
			if (e == null) {
				return new ResponseEntity<>(MensajeResponse.builder().mensaje("ID no encontrado").object(null).build(), HttpStatus.NOT_FOUND);
			}
			service.Borrar(id_enc);
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Eliminado correctamente").object(e).build(), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
