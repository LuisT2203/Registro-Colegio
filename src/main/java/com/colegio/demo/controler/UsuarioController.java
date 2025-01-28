package com.colegio.demo.controler;

import java.util.Map;

import com.colegio.demo.utils.MensajeResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
	@Autowired
	private ModelMapper mapper;
    
    @PostMapping(path="/save",consumes= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			Usuario usu = mapper.map(usuarioDTO, Usuario.class);
			Usuario usu1 = serviceU.addUsuario(usu);
			UsuarioDTO usudto = mapper.map(usu1, UsuarioDTO.class);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el Usuario")
					.object(usudto).build(),HttpStatus.CREATED);

		}catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().
					mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);

		}

    	
    }

	@PostMapping(path="/login")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO){
		
		try {
		//Autenticamos el usuario con authenticationManager
		this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				loginDTO.getUsuario(), loginDTO.getClave()
				));
		//validar en la bd
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDTO.getUsuario());
		Usuario usuario = usuRepo.findByUsuario(loginDTO.getUsuario());
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
		
		@PostMapping(path="/refresh")
		public ResponseEntity<?> loginUsuario(@RequestBody Map<String, String> request){
			String refreshToken = request.get("refreshToken");
			
			try {
			
					String username = jwtUtilService.extractUsername(refreshToken);
					UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
					Usuario usuario = usuRepo.findByUsuario(username);
					
					if(jwtUtilService.validateToken(refreshToken, userDetails)) {
						String newJwt = jwtUtilService.generateToken(userDetails, usuario.getTipo());
						String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, usuario.getTipo());
						
						
						AuthResponseDto authResponseDto = new AuthResponseDto();
						authResponseDto.setToken(newJwt);
						authResponseDto.setRefreshToken(newRefreshToken);
						
						return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
					}else {
						return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
					}
				
			}catch (Exception e) {
				return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Refresh Token::" + e.getMessage());
			}
		
		
	}
	
	
	
	
	
	
}
