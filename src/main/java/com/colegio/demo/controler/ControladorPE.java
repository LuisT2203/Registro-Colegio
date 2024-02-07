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
	public String listarPC(Model model) {
		List<PersonaExterna>pes = service.listarPersonaE();
		model.addAttribute("pcs",pes);
		return "PersonaExterna";
	}
	@GetMapping("/newPE")
	public String agregar(Model model) {
		model.addAttribute("pe",new PersonaExterna());
		return "NuevaPersonaE";
	}
	@PostMapping("/savePE")
	public String guardar(@Validated PersonaExterna pe, Model model) {
		service.Guardar(pe);
		return "redirect:/listarPE";
	}
	@GetMapping("/editarPE/{id_personaE}")
	public String editar(@PathVariable int id_personaE, Model model) {
		Optional<PersonaExterna>pe=service.listarId(id_personaE);
		model.addAttribute("pe", pe);
		return "NuevaPersonaE";
	}
	@GetMapping("/eliminarPE/{id_personaE}")
	public String delete(Model model, @PathVariable int id_personaE) {
		service.Borrar(id_personaE);
		return "redirect:/listarPE";
	}
	//INGRESO PPFF
	@GetMapping("/listarIngresoPE")
	public String listarI(Model model) {
		List<IngresoPersonaExterna>Ipes = serviceI.listarIngresoPE(); //IngresosPersona Externa
		model.addAttribute("Ipes",Ipes);
		return "IPE";
	}
	@GetMapping("/newIngresoPE")
	public String agregarI(Model model) {
		model.addAttribute("Ipes",new IngresoPersonaExterna());
		return "NuevoIPE";
	}
	@PostMapping("/saveIPE")
	public String guardarI(@Validated IngresoPersonaExterna ipe, Model model) {
		serviceI.Guardar(ipe);
		return "redirect:/listarIngresoPE";
	}
	@GetMapping("/editarIPE/{id_ingresoPersonaE}")
	public String editarI(@PathVariable int id_ingresoPersonaE, Model model) {
		Optional<IngresoPersonaExterna>Ipe=serviceI.listarId(id_ingresoPersonaE);
		model.addAttribute("Ipe", Ipe);
		return "NuevoIPE";
	}
	@GetMapping("/eliminarIPE/{id_ingresoPersonaE}")
	public String deleteI(Model model, @PathVariable int id_ingresoPersonaE) {
		serviceI.Borrar(id_ingresoPersonaE);
		return "redirect:/listarIngresoPE";
	}

}
