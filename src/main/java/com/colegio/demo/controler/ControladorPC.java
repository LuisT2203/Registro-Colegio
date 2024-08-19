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

	/// Personal Colegio
	@GetMapping("/listarPC")
	public List<PersonalColegio> listarPC() {
		return  service.listarPersonal();
		
	}

	@GetMapping("/newPC")
	public String agregar(Model model) {
		model.addAttribute("pc", new PersonalColegio());
		return "PersonalColegio";
	}

	@PostMapping(value="/savePC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PersonalColegio guardar(@RequestBody PersonalColegio pc) {
		
		return service.Guardar(pc);
		
	}
	@PutMapping(value="/updatePC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public PersonalColegio Actualizar(@RequestBody PersonalColegio pc) {
		
		return service.Guardar(pc);
		
	}

	@GetMapping("/editarPC/{id_personal}")
	public PersonalColegio editar(@PathVariable ("id_personal") int id_personal) {
		return service.listarId(id_personal);
		
	}


	@DeleteMapping("/eliminarPC/{id_personal}")
	public PersonalColegio delete(@PathVariable ("id_personal") int id_personal) {
		return service.Borrar(id_personal);
		
	}

	/*
	 * // INGRESO PPFF
	 * 
	 * @GetMapping("/listarIngresoPC") public List<IngresoPersonalColegio>
	 * listarI(@RequestParam(name = "fechaBusqueda", required = false) String
	 * fechaBusqueda,
	 * 
	 * @RequestParam(name = "id_personal", required = false) Integer id_personal,
	 * Model model) {
	 * 
	 * 
	 * 
	 * List<PersonalColegio> pcs = service.listarPersonal(); // lista de personal de
	 * Personal Colegio model.addAttribute("pc", new PersonalColegio());
	 * model.addAttribute("Ipc", new IngresoPersonalColegio());//Objeto vacio para
	 * crear nuevos registros model.addAttribute("pcs", pcs); //agregando al modelo
	 * la lista de personal colegio para manipular los nombres
	 * 
	 * // Método para obtener listado según la fecha LocalDate fecha;
	 * 
	 * if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) { // Si se ingresó una
	 * fecha, conviértela a java.sql.Date fecha = LocalDate.parse(fechaBusqueda);
	 * List<IngresoPersonalColegio> IpcsPorFecha =
	 * serviceI.listarIngresoPCPorFecha(fecha);//lista segun la fecha ingresada for
	 * (int i = 0; i < IpcsPorFecha.size(); i++) {
	 * IpcsPorFecha.get(i).setNumeroRegistro(i + 1); } int contadorRegistros =
	 * IpcsPorFecha.size(); // declarando contador usamos size para sacar la
	 * cantidad total de registros por fecha model.addAttribute("contadorRegistros",
	 * contadorRegistros); model.addAttribute("IpcsPorFecha", IpcsPorFecha);
	 * //agregando la lista al modelo return
	 * serviceI.listarIngresoPCPorFecha(fecha); } else if (id_personal != null){
	 * List<IngresoPersonalColegio> IpcsporID =
	 * serviceI.BuscarPersonalId(id_personal); for (int i = 0; i < IpcsporID.size();
	 * i++) { IpcsporID.get(i).setNumeroRegistro(i + 1); } int contadorRegistrosID =
	 * IpcsporID.size(); // declarando contador usamos size para sacar la cantidad
	 * total de registros por ID model.addAttribute("contadorRegistrosID",
	 * contadorRegistrosID); model.addAttribute("IpcsporID", IpcsporID); // Agrega
	 * el ingreso de personal al modelo return
	 * serviceI.BuscarPersonalId(id_personal); }else{ // Si no se proporcionó ningún
	 * parámetro válido, mostrar un mensaje de error o redirigir a otra página
	 * LocalDate fechaActual = LocalDate.now(); return
	 * serviceI.listarIngresoPCPorFecha(fechaActual); // Otra vista que muestre un
	 * mensaje de erro
	 * 
	 * } }
	 */

	@GetMapping("/listarIngresoPC")
	public List<IngresoPersonalColegio> listarIngresoPC(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda,
	                                                    @RequestParam(name = "id_personal", required = false) Integer id_personal) {
	    if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
	        LocalDate fecha = LocalDate.parse(fechaBusqueda);
	        return listarIngresoPorFecha(fecha);
	    } else if (id_personal != null) {
	        return listarIngresoPorID(id_personal);
	    } else {
	        throw new IllegalArgumentException("Debe proporcionarse una fecha o un ID de personal.");
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

	 
	
	@GetMapping("/newIPC")
	public String agregarI(Model model) {
		model.addAttribute("Ipc", new IngresoPersonalColegio());
		return "NuevoPersonal";
	}

	
	@PostMapping(value="/saveIPC", consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPersonalColegio guardarI(@RequestBody IngresoPersonalColegio ipc) {
	    // Establecer la fecha actual en el registro
		/* ipc.setFecha(LocalDate.now()); */

	    // Guardar el registro
	    IngresoPersonalColegio guardado = serviceI.Guardar(ipc);

	    return guardado;
	}
	
	@PutMapping(value="/updateIPC",consumes= MediaType.APPLICATION_JSON_VALUE)
	public IngresoPersonalColegio actualizarI(@RequestBody IngresoPersonalColegio ipc) {
	    // Establecer la fecha actual en el registro
	    ipc.setFecha(LocalDate.now());

	    // Guardar el registro
	    IngresoPersonalColegio guardado = serviceI.Guardar(ipc);

	    // Verificar si el guardado fue exitoso
	    if (guardado != null) {
	        // Redirigir al método listarIngresoPCPorFecha con la fecha actual
	       // LocalDate fechaActual = LocalDate.now();
	       // List<IngresoPersonalColegio> ingresosPorFecha = serviceI.listarIngresoPCPorFecha(fechaActual);(retorna la lista con la fecha actual una vez guardado)
	        // Aquí puedes hacer algo con la lista de ingresos por fecha
	        // ...
	    }

	    // Devolver el registro guardado
	    return guardado;
	}

	@GetMapping("/editarIPC/{id_ingresoPersonal}")
	public IngresoPersonalColegio editarI(@PathVariable ("id_ingresoPersonal") int id_ingresoPersonal) {
		return serviceI.listarId(id_ingresoPersonal);
		
		
	}

	

	@DeleteMapping("/eliminarIPC/{id_ingresoPersonal}")
	public IngresoPersonalColegio deleteI(@PathVariable ("id_ingresoPersonal") int id_ingresoPersonal) {
		return	serviceI.Borrar(id_ingresoPersonal);
		
	}
}
