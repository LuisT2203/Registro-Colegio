package com.colegio.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.colegio.demo.interfacesService.IUsuarioService;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.response.LoginResponse;
import com.colegio.demo.service.UsuarioService;
import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "https://registro-colegio-angular.vercel.app")
public class UsuarioController {

    @Autowired
    private UsuarioService service;
    
    
    
    @Autowired
    private IUsuarioService serviceU;
    
    @PostMapping(path="/save")
    public String saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    	String id = serviceU.addUsuario(usuarioDTO);
    	return id;
    	
    }

	@PostMapping(path="/login")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO){
		LoginResponse loginResponse = serviceU.loginUsuario(loginDTO);
		return ResponseEntity.ok(loginResponse);
	}
	
	
	
	
	
	
}
