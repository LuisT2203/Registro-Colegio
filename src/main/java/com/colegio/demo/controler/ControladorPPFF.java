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

import com.colegio.demo.interfacesService.IIngresoPPFFService;
import com.colegio.demo.interfacesService.IppffService;
import com.colegio.demo.modelo.PPFF;
import com.colegio.demo.modelo.IngresoPPFF;





@Controller
@RequestMapping
public class ControladorPPFF {
	@Autowired
	private IppffService service;
	@Autowired
	private IIngresoPPFFService serviceI;
	///PPFF
	@GetMapping("/listarPPFF")
	public String listar(Model model) {
		List<PPFF>ppffs = service.listarPPFF();
		model.addAttribute("ppffs",ppffs);
		model.addAttribute("ppff",new PPFF());
		return "PPFF";
	}
	@GetMapping("/newPPFF")
	public String agregar(Model model) {
		model.addAttribute("ppff",new PPFF());
		return "PPFF";
	}
	@PostMapping("/savePPFF")
	public String guardar(@Validated PPFF p, Model model) {
		model.addAttribute("ppff",new PPFF());
		service.Guardar(p);
		return "redirect:/listarPPFF";
	}
	@GetMapping("/editarPPFF/{Id_ppff}")
	public String editar(@PathVariable int Id_ppff, Model model) {
		Optional<PPFF>ppff=service.listarId(Id_ppff);
		model.addAttribute("ppff", ppff);
		return "PPFF";
	}
	@GetMapping("/editarPPFF/{id_ppff}/json")
	@ResponseBody
	public ResponseEntity<PPFF> editarJson(@PathVariable int id_ppff) {
		Optional<PPFF> ppff = service.listarId(id_ppff);
		return ResponseEntity.ok(ppff.orElse(new PPFF()));
	}
	@GetMapping("/eliminarPPFF/{Id_ppff}")
	public String delete(Model model, @PathVariable int Id_ppff) {
		service.Borrar(Id_ppff);
		return "redirect:/listarPPFF";
	}
	//INGRESO PPFF
	@GetMapping("/listarIPPFF")
	public String listarI(@RequestParam(name = "fechaBusqueda", required = false) String fechaBusqueda, Model model) {

		List<PPFF>ppffs = service.listarPPFF(); // lista de personal de Personal Colegio
		model.addAttribute("Ippff",new IngresoPPFF());//Objeto vacio para crear nuevos registros		
		model.addAttribute("ppffs", ppffs); //agregando al modelo la lista de personal colegio para manipular los nombres

		//metodo para obtener listado segun la fecha 
		 Date fecha;

		    if (fechaBusqueda != null && !fechaBusqueda.isEmpty()) {
		        // Si se ingresó una fecha, conviértela a java.sql.Date
		        fecha = Date.valueOf(LocalDate.parse(fechaBusqueda));
		    } else {
		        // Si no se ingresó una fecha, usa la fecha del sistema
		        fecha = Date.valueOf(LocalDate.now());
		    }

		    List<IngresoPPFF> IppffsPorFecha = serviceI.listarIngresoPPFFPorFecha(fecha);//lista segun la fecha ingresada
		    for (int i = 0; i < IppffsPorFecha.size(); i++) {
		    	IppffsPorFecha.get(i).setNumeroRegistro(i + 1);
		    }
		    int contadorRegistros = IppffsPorFecha.size();		   // declarando contador usamos size para sacar la cantidad total de registros por fecha
		    model.addAttribute("contadorRegistros", contadorRegistros); 
		    model.addAttribute("IppffsPorFecha", IppffsPorFecha); //agregando la lista al modelo

		    
		return "IPPFF";
	}
	@GetMapping("/newIngresoPPFF")
	public String agregarI(Model model) {
		model.addAttribute("Ippff",new IngresoPPFF());
		return "IPPFF";
	}
	@PostMapping("/saveIPPFF")
	public String guardarI(@Validated IngresoPPFF ip, Model model) {
		model.addAttribute("Ippff",new IngresoPPFF());
		serviceI.Guardar(ip);
		return "redirect:/listarIPPFF";
	}
	@GetMapping("/editarIPPFF/{id_ingresoPPFF}")
	public String editarI(@PathVariable int id_ingresoPPFF, Model model) {
		Optional<IngresoPPFF>Ippff=serviceI.listarId(id_ingresoPPFF);
		model.addAttribute("Ippff", Ippff);
		return "IPPFF";
	}
	@GetMapping("/editarIPPFF/{id_ingresoPPFF}/json")
	@ResponseBody
	public ResponseEntity<IngresoPPFF> editarJsonI(@PathVariable int id_ingresoPPFF) {
		Optional<IngresoPPFF> Ippff = serviceI.listarId(id_ingresoPPFF);
		return ResponseEntity.ok(Ippff.orElse(new IngresoPPFF()));
	}
	@GetMapping("/eliminarIPPFF/{id_ingresoPPFF}")
	public String deleteI(Model model, @PathVariable int id_ingresoPPFF) {
		serviceI.Borrar(id_ingresoPPFF);
		return "redirect:/listarIPPFF";
	}
}
