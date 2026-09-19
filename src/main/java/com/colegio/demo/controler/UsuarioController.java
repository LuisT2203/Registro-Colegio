package com.colegio.demo.controler;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.colegio.demo.Dto.AuthResponseDto;
import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;
import com.colegio.demo.interfaces.IRolRepository;
import com.colegio.demo.interfaces.UsuarioRepository;
import com.colegio.demo.interfacesService.IUsuarioService;
import com.colegio.demo.modelo.Rol;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.service.JwtUtilService;
import com.colegio.demo.utils.MensajeResponse;

@RestController
@RequestMapping(value = "/api/usuario", produces = MediaType.APPLICATION_JSON_VALUE)
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
	private IRolRepository rolRepo;

	@Autowired
	private JwtUtilService jwtUtilService;

	@Autowired
	private ModelMapper mapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(path = "/save", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		try {
			Usuario usu1 = serviceU.addUsuario(usuarioDTO);
			UsuarioDTO usudto = mapper.map(usu1, UsuarioDTO.class);
			usudto.setId_rol(usu1.getRol() != null ? usu1.getRol().getId_rol() : null);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se agrego correctamente el Usuario")
					.object(usudto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/listarUsuarios")
	public ResponseEntity<?> listar() {
		try {
			List<Usuario> lista = serviceU.listarUsuarios();
			if (lista.isEmpty()) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("No hay usuarios registrados").object(null).build(),
						HttpStatus.NO_CONTENT);
			}
			List<UsuarioDTO> lista2 = lista.stream().map(m -> {
				UsuarioDTO dto = mapper.map(m, UsuarioDTO.class);
				dto.setId_rol(m.getRol() != null ? m.getRol().getId_rol() : null);
				return dto;
			}).collect(Collectors.toList());
			return new ResponseEntity<>(MensajeResponse.builder().mensaje("Usuarios encontrados").object(lista2).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/editarUsuario/{id}")
	public ResponseEntity<?> editar(@PathVariable("id") int id) {
		Usuario usu = serviceU.listarId(id);
		if (usu.getId() == null) {
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Usuario no encontrado").object(null).build(),
					HttpStatus.NOT_FOUND);
		}
		UsuarioDTO usudto = mapper.map(usu, UsuarioDTO.class);
		usudto.setId_rol(usu.getRol() != null ? usu.getRol().getId_rol() : null);
		return new ResponseEntity<>(usudto, HttpStatus.OK);
	}

	@PutMapping(value = "/updateUsuario", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizar(@RequestBody UsuarioDTO bean) {
		try {
			Usuario usu = mapper.map(bean, Usuario.class);
			if (bean.getId_rol() != null) {
				Rol rol = rolRepo.findById(bean.getId_rol()).orElse(null);
				usu.setRol(rol);
			}
			if (bean.getClave() != null && !bean.getClave().isEmpty()
					&& !bean.getClave().startsWith("$2a$")) {
				usu.setClave(passwordEncoder.encode(bean.getClave()));
			}
			Usuario usu1 = serviceU.Guardar(usu);
			UsuarioDTO usudto = mapper.map(usu1, UsuarioDTO.class);
			usudto.setId_rol(usu1.getRol() != null ? usu1.getRol().getId_rol() : null);
			return new ResponseEntity<>(MensajeResponse.builder()
					.mensaje("Se actualizo correctamente el Usuario")
					.object(usudto).build(), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(MensajeResponse.builder().mensaje(e.getMessage()).object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/eliminarUsuario/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") int id) {
		try {
			Usuario usu = serviceU.listarId(id);
			if (usu.getId() == null) {
				return new ResponseEntity<>(
						MensajeResponse.builder().mensaje("Usuario no encontrado").object(null).build(),
						HttpStatus.NOT_FOUND);
			}
			serviceU.Borrar(id);
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Usuario eliminado correctamente").object(usu).build(),
					HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(
					MensajeResponse.builder().mensaje("Error al eliminar").object(null).build(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping(path = "/login")
	public ResponseEntity<?> loginUsuario(@RequestBody LoginDTO loginDTO) {
		try {
			this.authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginDTO.getUsuario(), loginDTO.getClave()));
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(loginDTO.getUsuario());
			Usuario usuario = usuRepo.findByUsuario(loginDTO.getUsuario()).orElse(null);
			String tipo = usuario != null && usuario.getRol() != null ? usuario.getRol().getNombre_rol().toLowerCase() : "user";
			String jwt = this.jwtUtilService.generateToken(userDetails, tipo);
			String refreshToken = this.jwtUtilService.generateRefreshToken(userDetails, tipo);

			AuthResponseDto authResponseDto = new AuthResponseDto();
			authResponseDto.setToken(jwt);
			authResponseDto.setRefreshToken(refreshToken);

			return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Authentication::" + e.getMessage());
		}
	}

	@PostMapping(path = "/refresh")
	public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
		String refreshToken = request.get("refreshToken");
		try {
			String username = jwtUtilService.extractUsername(refreshToken);
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
			Usuario usuario = usuRepo.findByUsuario(username).orElse(null);
			String tipo = usuario != null && usuario.getRol() != null ? usuario.getRol().getNombre_rol().toLowerCase() : "user";

			if (jwtUtilService.validateToken(refreshToken, userDetails)) {
				String newJwt = jwtUtilService.generateToken(userDetails, tipo);
				String newRefreshToken = jwtUtilService.generateRefreshToken(userDetails, tipo);

				AuthResponseDto authResponseDto = new AuthResponseDto();
				authResponseDto.setToken(newJwt);
				authResponseDto.setRefreshToken(newRefreshToken);

				return new ResponseEntity<>(authResponseDto, HttpStatus.OK);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Refresh Token");
			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Error Refresh Token::" + e.getMessage());
		}
	}
}
