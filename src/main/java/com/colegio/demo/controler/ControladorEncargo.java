package com.colegio.demo.controler;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
	
	@GetMapping("/listarEncargos")
	public List<Encargo> listarEncargos(@RequestParam (name="fechaBusqueda",required=false)String fechaBusqueda,
										@RequestParam(name = "encargoNom", required = false) String encargoNom){
		if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
	        LocalDate fecha = LocalDate.parse(fechaBusqueda);
	        return listarEncargoPorFecha(fecha);
		}else if(encargoNom != null && !encargoNom.isEmpty()){
			return listarEncargosPorNombre(encargoNom);
		}else {
			throw new IllegalArgumentException("Debe proporcionarse una fecha o un Nombre.");
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
	public Encargo guardarE(@RequestBody Encargo enc) {
		Encargo guardado = service.Guardar(enc);
		return guardado;
	}
	
	@PutMapping(value="/updateEncargo", consumes= MediaType.APPLICATION_JSON_VALUE)
	public Encargo actualizarE(@RequestBody Encargo enc) {
		Encargo guardado = service.Guardar(enc);
		return guardado;
	}
	
	@GetMapping("/editarE/{id_enc}")
	public Encargo editarE(@PathVariable ("id_enc") int id_enc) {
		return service.listarID(id_enc);
	}
	@DeleteMapping("/eliminarE/{id_enc}")
	public Encargo eliminarE(@PathVariable ("id_enc") int id_enc) {
		return service.Borrar(id_enc);
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
