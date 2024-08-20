package com.colegio.demo.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.colegio.demo.interfacesService.IUsuarioService;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.response.LoginResponse;
import com.colegio.demo.service.JwtUtilService;
import com.colegio.demo.service.UsuarioService;
import com.colegio.demo.Dto.AuthResponseDto;
import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;
import com.colegio.demo.interfaces.UsuarioRepository;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

   
    
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private UserDetailsService userDetailsService;
    
    @Autowired
    private IUsuarioService serviceU;
    
    @Autowired 
    private UsuarioRepository usuRepo;
    
    @Autowired
    private JwtUtilService jwtUtilService;
    
    @PostMapping(path="/save")
    public String saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
    	String id = serviceU.addUsuario(usuarioDTO);
    	return id;
    	
    }

	@PostMapping(path="/login")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO){
		
		try {
		//Autenticamos el usuario con authenticationManager
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDTO.getCorreo(), loginDTO.getClave()
				));
		//validar en la bd
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDTO.getCorreo());
		Usuario usuario = usuRepo.findByCorreo(loginDTO.getCorreo());
		//generar token
		String jwt = this.jwtUtilService.generateToken(userDetails, usuario.getTipo());
		String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, usuario.getTipo());
		
		AuthResponseDto authResponseDto = new AuthResponseDto();
		authResponseDto.setToken(jwt);
		authResponseDto.setRefreshToken(refreshToken);
		
		return new ResponseEntity<AuthResponseDto>(authResponseDto, HttpStatus.OK);
		}catch (Exception e) {
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Athentication::" + e.getMessage());
		}
		
		
	}
	
	
	
	
	
	
}
