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

	/// PPFF
	@GetMapping("/listarPPFF")
	public List<PPFF> listar(Model model) {
		return service.listarPPFF();
		
	}

	@GetMapping("/newPPFF")
	public String agregar(Model model) {
		model.addAttribute("ppff", new PPFF());
		return "PPFF";
	}

	@PostMapping(value="/savePPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PPFF guardar(@RequestBody PPFF p) {
		return service.Guardar(p);
		
	}
	@PutMapping(value="/updatePPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PPFF Actualizar(@RequestBody PPFF p) {
		return service.Guardar(p);
		
	}

	@GetMapping("/editarPPFF/{Id_ppff}")
	public PPFF editar(@PathVariable ("Id_ppff") int Id_ppff) {
		return service.listarId(Id_ppff);
		
	}


	@DeleteMapping("/eliminarPPFF/{Id_ppff}")
	public PPFF delete(@PathVariable ("Id_ppff") int Id_ppff) {
		return service.Borrar(Id_ppff);
		
	}
	
	 //INGRESO PPFF
	

	@GetMapping("/listarIPPFF")
	public List<IngresoPPFF> listarIngresoPPFF(
			@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
			@RequestParam(name = "id_ppff", required = false) Integer id_ppff) {
		if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
			LocalDate fecha = LocalDate.parse(fechaBusqueda);
			return listarIngresoPorFecha(fecha);
		} else if (id_ppff != null) {
			return listarIngresoPorID(id_ppff);
		} else {
			LocalDate fechaActual = LocalDate.now();
			return listarIngresoPorFecha(fechaActual);
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

	@GetMapping("/newIngresoPPFF")
	public String agregarI(Model model) {
		model.addAttribute("Ippff", new IngresoPPFF());
		return "IPPFF";
	}

	@PostMapping(value="/saveIPPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPPFF guardarI(@RequestBody IngresoPPFF ip) {
		
		/* ip.setFecha(LocalDate.now()); */
		IngresoPPFF guardado = serviceI.Guardar(ip);
		
		
		return guardado;
	}
	@PutMapping(value="/updateIPPFF",consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPPFF actualizarI(@RequestBody IngresoPPFF ip) {
		
		ip.setFecha(LocalDate.now());
		IngresoPPFF guardado = serviceI.Guardar(ip);
		
		if(guardado != null) {
			// LocalDate fechaActual = LocalDate.now();
			// List<IngresoPPFF> ingresosPorFecha = serviceI.listarIngresoPPFFPorFecha(fechaActual); (retorna la lista con la fecha actual una vez guardado)
		}
		return guardado;
	}

	@GetMapping("/editarIPPFF/{id_ingresoPPFF}")
	public IngresoPPFF editarI(@PathVariable ("id_ingresoPPFF") int id_ingresoPPFF) {
		return serviceI.listarId(id_ingresoPPFF);
		
	}

	/*
	 * @GetMapping("/editarIPPFF/{id_ingresoPPFF}/json")
	 * 
	 * @ResponseBody public ResponseEntity<IngresoPPFF> editarJsonI(@PathVariable
	 * int id_ingresoPPFF) { Optional<IngresoPPFF> Ippff =
	 * serviceI.listarId(id_ingresoPPFF); return ResponseEntity.ok(Ippff.orElse(new
	 * IngresoPPFF())); }
	 */

	@DeleteMapping("/eliminarIPPFF/{id_ingresoPPFF}")
	public IngresoPPFF deleteI( @PathVariable ("Id_ppff") int id_ingresoPPFF) {
		return serviceI.Borrar(id_ingresoPPFF);
		
	}
}
