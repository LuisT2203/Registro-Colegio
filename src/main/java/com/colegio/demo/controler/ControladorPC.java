package com.colegio.demo.controler;

import java.time.LocalDate;

import java.sql.Date;

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

import com.colegio.demo.interfacesService.IIngresoPersonalColegioService;
import com.colegio.demo.interfacesService.IPersonalColegioService;
import com.colegio.demo.modelo.IngresoPersonalColegio;
import com.colegio.demo.modelo.PersonalColegio;

@Controller
@RequestMapping
public class ControladorPC {
	@Autowired
	private IPersonalColegioService service;
	@Autowired
	private IIngresoPersonalColegioService serviceI;

	/// Personal Colegio
	@GetMapping("/listarPC")
	public String listarPC(Model model) {
		List<PersonalColegio> pcs = service.listarPersonal();
		model.addAttribute("pcs", pcs);
		model.addAttribute("pc", new PersonalColegio()); // Añade un objeto pc vacío al modelo
		return "PersonalColegio";
	}

	@GetMapping("/newPC")
	public String agregar(Model model) {
		model.addAttribute("pc", new PersonalColegio());
		return "PersonalColegio";
	}

	@PostMapping("/savePC")
	public String guardar(@Validated PersonalColegio pc, Model model) {
		model.addAttribute("pc", new PersonalColegio());
		service.Guardar(pc);
		return "redirect:/listarIngresoPC";
	}

	@GetMapping("/editarPC/{id_personal}")
	public String editar(@PathVariable int id_personal, Model model) {
		Optional<PersonalColegio> pc = service.listarId(id_personal);
		model.addAttribute("pc", pc);
		return "PersonalColegio";
	}

	@GetMapping("/editarPC/{id_personal}/json")
	@ResponseBody
	public ResponseEntity<PersonalColegio> editarJson(@PathVariable int id_personal) {
		Optional<PersonalColegio> pc = service.listarId(id_personal);
		return ResponseEntity.ok(pc.orElse(new PersonalColegio()));
	}

	@GetMapping("/eliminarPC/{id_personal}")
	public String delete(Model model, @PathVariable int id_personal) {
		service.Borrar(id_personal);
		return "redirect:/listarPC";
	}

	// INGRESO PPFF
	@GetMapping("/listarIngresoPC")
	public String listarI(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
	                       @RequestParam(name = "id_personal", required = false) Integer id_personal,
	                       Model model) {
		


	    List<PersonalColegio> pcs = service.listarPersonal(); // lista de personal de Personal Colegio
	    model.addAttribute("pc", new PersonalColegio());
	    model.addAttribute("Ipc", new IngresoPersonalColegio());//Objeto vacio para crear nuevos registros
	    model.addAttribute("pcs", pcs); //agregando al modelo la lista de personal colegio para manipular los nombres

	    // Método para obtener listado según la fecha
	    Date fecha;

	    if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
	        // Si se ingresó una fecha, conviértela a java.sql.Date
	        fecha = Date.valueOf(LocalDate.parse(fechaBusqueda));
	        List<IngresoPersonalColegio> IpcsPorFecha = serviceI.listarIngresoPCPorFecha(fecha);//lista segun la fecha ingresada
		    for (int i = 0; i < IpcsPorFecha.size(); i++) {
		        IpcsPorFecha.get(i).setNumeroRegistro(i + 1);
		    }
		    int contadorRegistros = IpcsPorFecha.size();   // declarando contador usamos size para sacar la cantidad total de registros por fecha
		    model.addAttribute("contadorRegistros", contadorRegistros);
		    model.addAttribute("IpcsPorFecha", IpcsPorFecha); //agregando la lista al modelo
		    return "IPC";
	    } else if (id_personal != null){
	    	List<IngresoPersonalColegio> IpcsporID = serviceI.BuscarPersonalId(id_personal);
	        for (int i = 0; i < IpcsporID.size(); i++) {
	            IpcsporID.get(i).setNumeroRegistro(i + 1);
	        }
	        int contadorRegistrosID = IpcsporID.size();   // declarando contador usamos size para sacar la cantidad total de registros por ID
	        model.addAttribute("contadorRegistrosID", contadorRegistrosID);
	        model.addAttribute("IpcsporID", IpcsporID); // Agrega el ingreso de personal al modelo
	        return "IPC";
	    }else{
	    	 // Si no se proporcionó ningún parámetro válido, mostrar un mensaje de error o redirigir a otra página
	    	LocalDate fechaActual = LocalDate.now();
		    String fechaActualStr = fechaActual.toString();
		    return "redirect:/listarIngresoPC?fechaBusqueda=" + fechaActualStr; // Otra vista que muestre un mensaje de erro
	        
	    }
	}

	/*
	 * @GetMapping("/listarIngresoPC") public String listarI(@RequestParam(name =
	 * "fechaBusqueda", required = false) String fechaBusqueda, Model model) {
	 * 
	 * List<PersonalColegio> pcs = service.listarPersonal(); // lista de personal de
	 * Personal Colegio model.addAttribute("Ipc", new
	 * IngresoPersonalColegio());//Objeto vacio para crear nuevos registros
	 * model.addAttribute("pcs", pcs); //agregando al modelo la lista de personal
	 * colegio para manipular los nombres
	 * 
	 * //metodo para obtener listado segun la fecha Date fecha;
	 * 
	 * if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) { // Si se ingresó una
	 * fecha, conviértela a java.sql.Date fecha =
	 * Date.valueOf(LocalDate.parse(fechaBusqueda)); } else { // Si no se ingresó
	 * una fecha, usa la fecha del sistema fecha = Date.valueOf(LocalDate.now()); }
	 * 
	 * List<IngresoPersonalColegio> IpcsPorFecha =
	 * serviceI.listarIngresoPCPorFecha(fecha);//lista segun la fecha ingresada for
	 * (int i = 0; i < IpcsPorFecha.size(); i++) {
	 * IpcsPorFecha.get(i).setNumeroRegistro(i + 1); } int contadorRegistros =
	 * IpcsPorFecha.size(); // declarando contador usamos size para sacar la
	 * cantidad total de registros por fecha model.addAttribute("contadorRegistros",
	 * contadorRegistros); model.addAttribute("IpcsPorFecha", IpcsPorFecha);
	 * //agregando la lista al modelo
	 * 
	 * 
	 * return "IPC"; }
	 */
	

	 
	@GetMapping("/NuevoPersonal")
	public String mostrarMiPagina() {
	    return "NuevoPersonal"; // Nombre de la plantilla Thymeleaf (sin la extensión .html)
	}
	@GetMapping("/newIPC")
	public String agregarI(Model model) {
		model.addAttribute("Ipc", new IngresoPersonalColegio());
		return "NuevoPersonal";
	}

	
	@PostMapping("/saveIPC")
	public String guardarI(@Validated IngresoPersonalColegio ipc, Model model) {
	    // Establecer la fecha actual en el registro
	    // Suponiendo que la fecha se almacena en un campo 'fecha' en tu entidad IngresoPersonalColegio

	    model.addAttribute("Ipc", new IngresoPersonalColegio());
	    serviceI.Guardar(ipc);

	    // Redirigir al usuario a la página de listarIngresoPC con la fecha actual como parámetro de búsqueda
	    LocalDate fechaActual = LocalDate.now();
	    String fechaActualStr = fechaActual.toString();
	    return "redirect:/listarIngresoPC?fechaBusqueda=" + fechaActualStr;
	}

	@GetMapping("/editarIPC/{id_ingresoPersonal}")
	public String editarI(@PathVariable int id_ingresoPersonal, Model model) {
		Optional<IngresoPersonalColegio> Ipc = serviceI.listarId(id_ingresoPersonal);
		model.addAttribute("Ipc", Ipc);
		return "IPC";
	}

	@GetMapping("/editarIPC/{id_ingresoPersonal}/json")
	@ResponseBody
	public ResponseEntity<IngresoPersonalColegio> editarJsonI(@PathVariable int id_ingresoPersonal) {
		Optional<IngresoPersonalColegio> Ipc = serviceI.listarId(id_ingresoPersonal);
		return ResponseEntity.ok(Ipc.orElse(new IngresoPersonalColegio()));
	}

	@GetMapping("/eliminarIPC/{id_ingresoPersonal}")
	public String deleteI(Model model, @PathVariable int id_ingresoPersonal) {
		serviceI.Borrar(id_ingresoPersonal);
		return "redirect:/listarIngresoPC";
	}
}
