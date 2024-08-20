package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import com.colegio.demo.modelo.IngresoPPFF;
import com.colegio.demo.modelo.IngresoPersonaExterna;
import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.modelo.PersonaExterna;




@RestController
@RequestMapping(value = "ControladorPE", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*")
public class ControladorPE {
	@Autowired
	private IPersonaExternaService service;
	@Autowired
	private IIngresoPersonaExternaService serviceI;
	
	///Persona Externa
	@GetMapping("/listarPE")
	public List<PersonaExterna> listarPE(Model model) {
		return service.listarPersonaE();
		
	}
	
	@GetMapping("/newPE")
	public String agregar(Model model) {
		model.addAttribute("pe",new PersonaExterna());
		return "PersonaExterna";
	}
	
	@PostMapping(value="/savePE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PersonaExterna guardar(@RequestBody PersonaExterna pe) {
		return service.Guardar(pe);
		
	}
	
	@PutMapping(value="/updatePE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PersonaExterna Actualizar(@RequestBody PersonaExterna pe) {
		return service.Guardar(pe);
		
	}
	
	
	@GetMapping("/editarPE/{id_personaE}")
	public PersonaExterna editar(@PathVariable ("id_personaE") int id_personaE) {
		return service.listarId(id_personaE);
		
	}

	/*
	 * @GetMapping("/editarPE/{id_personaE}/json")
	 * 
	 * @ResponseBody public ResponseEntity<PersonaExterna> editarJson(@PathVariable
	 * int id_personaE) { Optional<PersonaExterna> pe =
	 * service.listarId(id_personaE); return ResponseEntity.ok(pe.orElse(new
	 * PersonaExterna())); }
	 */
	
	@DeleteMapping("/eliminarPE/{id_personaE}")
	public PersonaExterna delete( @PathVariable ("id_personaE") int id_personaE) {
		return service.Borrar(id_personaE);
		
	}
	
	//INGRESO PERSONA EXTERNA//
	@GetMapping("/listarIngresoPE")
	public List<IngresoPersonaExterna> listarIngresoPPFF(
			@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "id_personaE", required = false) Integer id_personaE) {
		if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
			LocalDate fecha = LocalDate.parse(fechaBusqueda);
			return listarIngresoPorFecha(fecha);
		} else if (id_personaE != null) {
			return listarIngresoPorID(id_personaE);
		} else {
			throw new IllegalArgumentException("Debe proporcionarse una fecha o un ID de personal.");
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
	
	
	@GetMapping("/newIngresoPE")
	public String agregarI(Model model) {
		model.addAttribute("Ipe",new IngresoPersonaExterna());
		return "IPE";
	}
	@PostMapping(value="/saveIPE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPersonaExterna guardarI(@RequestBody IngresoPersonaExterna ipe) {
		
		/* ipe.setFecha(LocalDate.now()); */
		 IngresoPersonaExterna guardado = serviceI.Guardar(ipe);
		 
		 
		 return guardado;
		
			
	}
	
	@PutMapping(value="/updateIPE",consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPersonaExterna actualizarI(@RequestBody IngresoPersonaExterna ipe) {
		
		 ipe.setFecha(LocalDate.now());
		 IngresoPersonaExterna guardado = serviceI.Guardar(ipe);
		 
		 if(guardado != null) {
			// LocalDate fechaActual = LocalDate.now();
			// List<IngresoPersonaExterna> ingresosPorFecha = serviceI.listarIngresoPEPorFecha(fechaActual);(retorna la lista con la fecha actual una vez guardado)
			 //.. falta implementar redirecciones 
		 }
		 return guardado;
		
			/*
			 * LocalDate fechaActual = LocalDate.now(); String fechaActualStr =
			 * fechaActual.toString(); return "redirect:/listarIngresoPE?fechaBusqueda=" +
			 * fechaActualStr;
			 */
	}
	
	@GetMapping("/editarIPE/{id_ingresoPersonaE}")
	public IngresoPersonaExterna editarI(@PathVariable ("id_ingresoPersonaE") int id_ingresoPersonaE) {
		 return serviceI.listarId(id_ingresoPersonaE);
		
	
	}

	/*
	 * @GetMapping("/editarIPE/{id_ingresoPersonaE}/json")
	 * 
	 * @ResponseBody public ResponseEntity<IngresoPersonaExterna>
	 * editarJsonI(@PathVariable int id_ingresoPersonaE) {
	 * Optional<IngresoPersonaExterna> Ipe = serviceI.listarId(id_ingresoPersonaE);
	 * return ResponseEntity.ok(Ipe.orElse(new IngresoPersonaExterna())); }
	 */
	@DeleteMapping("/eliminarIPE/{id_ingresoPersonaE}")
	public IngresoPersonaExterna deleteI(@PathVariable ("id_ingresoPersonaE") int id_ingresoPersonaE) {
		return serviceI.Borrar(id_ingresoPersonaE);
		
	}

}
