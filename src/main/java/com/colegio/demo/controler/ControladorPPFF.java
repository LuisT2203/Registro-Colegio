package com.colegio.demo.controler;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
		return "PPFF";
	}
	@GetMapping("/newPPFF")
	public String agregar(Model model) {
		model.addAttribute("ppff",new PPFF());
		return "NuevoPPFF";
	}
	@PostMapping("/savePPFF")
	public String guardar(@Validated PPFF p, Model model) {
		service.Guardar(p);
		return "redirect:/listarPPFF";
	}
	@GetMapping("/editarPPFF/{Id_ppff}")
	public String editar(@PathVariable int Id_ppff, Model model) {
		Optional<PPFF>ppff=service.listarId(Id_ppff);
		model.addAttribute("ppff", ppff);
		return "NuevoPPFF";
	}
	@GetMapping("/eliminarPPFF/{Id_ppff}")
	public String delete(Model model, @PathVariable int Id_ppff) {
		service.Borrar(Id_ppff);
		return "redirect:/listarPPFF";
	}
	//INGRESO PPFF
	@GetMapping("/listarIngresoPPFF")
	public String listarI(Model model) {
		List<IngresoPPFF>Ippffs = serviceI.listarIngresoPPFF(); //IngresosPPFFS
		model.addAttribute("Ippffs",Ippffs);
		return "IPPFF";
	}
	@GetMapping("/newIngresoPPFF")
	public String agregarI(Model model) {
		model.addAttribute("Ippff",new IngresoPPFF());
		return "NuevoIPPFF";
	}
	@PostMapping("/saveIPPFF")
	public String guardarI(@Validated IngresoPPFF ip, Model model) {
		serviceI.Guardar(ip);
		return "redirect:/listarIngresoPPFF";
	}
	@GetMapping("/editarIPPFF/{id_ingresoPPFF}")
	public String editarI(@PathVariable int id_ingresoPPFF, Model model) {
		Optional<IngresoPPFF>Ippff=serviceI.listarId(id_ingresoPPFF);
		model.addAttribute("Ippff", Ippff);
		return "NuevoIPPFF";
	}
	@GetMapping("/eliminarIPPFF/{id_ingresoPPFF}")
	public String deleteI(Model model, @PathVariable int id_ingresoPPFF) {
		serviceI.Borrar(id_ingresoPPFF);
		return "redirect:/listarIngresoPPFF";
	}
}
