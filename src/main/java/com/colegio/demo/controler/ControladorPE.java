package com.colegio.demo.controler;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.colegio.demo.interfacesService.IIngresoPersonaExternaService;
import com.colegio.demo.interfacesService.IPersonaExternaService;
import com.colegio.demo.modelo.IngresoPersonaExterna;
import com.colegio.demo.modelo.PersonaExterna;




@Controller
@RequestMapping
public class ControladorPE {
	@Autowired
	private IPersonaExternaService service;
	@Autowired
	private IIngresoPersonaExternaService serviceI;
	///Persona Externa
	@GetMapping("/listarPE")
	public String listarPE(Model model) {
		List<PersonaExterna>pes = service.listarPersonaE();
		model.addAttribute("pes",pes);
		model.addAttribute("pe",new PersonaExterna());
		return "PersonaExterna";
	}
	@GetMapping("/newPE")
	public String agregar(Model model) {
		model.addAttribute("pe",new PersonaExterna());
		return "PersonaExterna";
	}
	@PostMapping("/savePE")
	public String guardar(@Validated PersonaExterna pe, Model model) {
		model.addAttribute("pe",new PersonaExterna());
		service.Guardar(pe);
		return "redirect:/listarIngresoPE";
	}
	@GetMapping("/editarPE/{id_personaE}")
	public String editar(@PathVariable int id_personaE, Model model) {
		Optional<PersonaExterna>pe=service.listarId(id_personaE);
		model.addAttribute("pe", pe);
		return "PersonaExterna";
	}
	@GetMapping("/editarPE/{id_personaE}/json")
	@ResponseBody
	public ResponseEntity<PersonaExterna> editarJson(@PathVariable int id_personaE) {
		Optional<PersonaExterna> pe = service.listarId(id_personaE);
		return ResponseEntity.ok(pe.orElse(new PersonaExterna()));
	}
	@GetMapping("/eliminarPE/{id_personaE}")
	public String delete(Model model, @PathVariable int id_personaE) {
		service.Borrar(id_personaE);
		return "redirect:/listarPE";
	}
	//INGRESO PPFF
	/*
	 * @GetMapping("/listarIngresoPE") public String listarI(@RequestParam(name =
	 * "fechaBusqueda", required = false) String fechaBusqueda, Model model) {
	 * 
	 * List<PersonaExterna>pes = service.listarPersonaE(); // lista de personal de
	 * personas externas model.addAttribute("pe",new PersonaExterna()); //nueva
	 * persona para agregar desde la misma vista model.addAttribute("Ipe",new
	 * IngresoPersonaExterna());//Objeto vacio para crear nuevos registros
	 * model.addAttribute("pes", pes); //agregando al modelo la lista de personal
	 * colegio para manipular los nombres
	 * 
	 * //metodo para obtener listado segun la fecha Date fecha;
	 * 
	 * if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) { // Si se ingresó una
	 * fecha, conviértela a java.sql.Date fecha =
	 * Date.valueOf(LocalDate.parse(fechaBusqueda)); } else { // Si no se ingresó
	 * una fecha, usa la fecha del sistema fecha = Date.valueOf(LocalDate.now()); }
	 * 
	 * List<IngresoPersonaExterna> IpesPorFecha =
	 * serviceI.listarIngresoPEPorFecha(fecha);//lista segun la fecha ingresada for
	 * (int i = 0; i < IpesPorFecha.size(); i++) {
	 * IpesPorFecha.get(i).setNumeroRegistro(i + 1); } int contadorRegistros =
	 * IpesPorFecha.size(); // declarando contador usamos size para sacar la
	 * cantidad total de registros por fecha model.addAttribute("contadorRegistros",
	 * contadorRegistros); model.addAttribute("IpesPorFecha", IpesPorFecha);
	 * //agregando la lista al modelo
	 * 
	 * 
	 * return "IPE"; }
	 */
	@GetMapping("/listarIngresoPE")
	public String listarI(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
	                       @RequestParam(name = "personaE", required = false) Integer personaE,
	                       Model model) {
		


		List<PersonaExterna>pes = service.listarPersonaE(); // lista de personal de personas externas
		model.addAttribute("pe",new PersonaExterna()); //nueva persona para agregar desde la misma vista
		model.addAttribute("Ipe",new IngresoPersonaExterna());//Objeto vacio para crear nuevos registros		
		model.addAttribute("pes", pes); //agregando al modelo la lista de personal colegio para manipular los nombres

	    // Método para obtener listado según la fecha
	    Date fecha;

	    if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
	        // Si se ingresó una fecha, conviértela a java.sql.Date
	        fecha = Date.valueOf(LocalDate.parse(fechaBusqueda));
	        List<IngresoPersonaExterna> IpePorFecha = serviceI.listarIngresoPEPorFecha(fecha);//lista segun la fecha ingresada
		    for (int i = 0; i < IpePorFecha.size(); i++) {
		    	IpePorFecha.get(i).setNumeroRegistro(i + 1);
		    }
		    int contadorRegistros = IpePorFecha.size();   // declarando contador usamos size para sacar la cantidad total de registros por fecha
		    model.addAttribute("contadorRegistros", contadorRegistros);
		    model.addAttribute("IpePorFecha", IpePorFecha); //agregando la lista al modelo
		    return "IPE";
	    } else if (personaE != null){
	    	List<IngresoPersonaExterna> IpesporID = serviceI.BuscarPersonalId(personaE); //En el html se hace referencia cuando lleve el atributo "name"
	        for (int i = 0; i < IpesporID.size(); i++) {
	        	IpesporID.get(i).setNumeroRegistro(i + 1);
	        }
	        int contadorRegistrosID = IpesporID.size();   // declarando contador usamos size para sacar la cantidad total de registros por ID
	        model.addAttribute("contadorRegistrosID", contadorRegistrosID);
	        model.addAttribute("IpesporID", IpesporID); // Agrega el ingreso de personal al modelo
	        return "IPE";
	    }else{
	    	 // Si no se proporcionó ningún parámetro válido, mostrar un mensaje de error o redirigir a otra página
	        return "IPE"; // Otra vista que muestre un mensaje de erro
	        
	    }
	}
	
	
	@GetMapping("/newIngresoPE")
	public String agregarI(Model model) {
		model.addAttribute("Ipe",new IngresoPersonaExterna());
		return "IPE";
	}
	@PostMapping("/saveIPE")
	public String guardarI(@Validated IngresoPersonaExterna ipe, Model model) {
		serviceI.Guardar(ipe);
		model.addAttribute("pe",new PersonaExterna()); 
		model.addAttribute("Ipe",new IngresoPersonaExterna());
		
		LocalDate fechaActual = LocalDate.now();
	    String fechaActualStr = fechaActual.toString();
		return "redirect:/listarIngresoPE?fechaBusqueda=" + fechaActualStr;
	}
	@GetMapping("/editarIPE/{id_ingresoPersonaE}")
	public String editarI(@PathVariable int id_ingresoPersonaE, Model model) {
		Optional<IngresoPersonaExterna>Ipe=serviceI.listarId(id_ingresoPersonaE);
		model.addAttribute("Ipe", Ipe);
		return "NuevoIPE";
	}
	@GetMapping("/editarIPE/{id_ingresoPersonaE}/json")
	@ResponseBody
	public ResponseEntity<IngresoPersonaExterna> editarJsonI(@PathVariable int id_ingresoPersonaE) {
		Optional<IngresoPersonaExterna> Ipe = serviceI.listarId(id_ingresoPersonaE);
		return ResponseEntity.ok(Ipe.orElse(new IngresoPersonaExterna()));
	}
	@GetMapping("/eliminarIPE/{id_ingresoPersonaE}")
	public String deleteI(Model model, @PathVariable int id_ingresoPersonaE) {
		serviceI.Borrar(id_ingresoPersonaE);
		return "redirect:/listarIngresoPE";
	}

}
