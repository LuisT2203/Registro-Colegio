package com.colegio.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;
import com.colegio.demo.interfaces.IRolRepository;
import com.colegio.demo.interfaces.UsuarioRepository;
import com.colegio.demo.interfacesService.IUsuarioService;
import com.colegio.demo.modelo.Rol;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.response.LoginResponse;

@Service
public class UsuarioService implements IUsuarioService {

	@Autowired
	private UsuarioRepository repository;

	@Autowired
	private IRolRepository rolRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public Usuario addUsuario(UsuarioDTO usuarioDTO) {
		Rol rol = null;
		if (usuarioDTO.getId_rol() != null) {
			rol = rolRepository.findById(usuarioDTO.getId_rol()).orElse(null);
		}
		Usuario usuario = new Usuario(
				usuarioDTO.getId(),
				usuarioDTO.getUsuario(),
				this.passwordEncoder.encode(usuarioDTO.getClave()),
				usuarioDTO.isEstado(),
				rol);
		repository.save(usuario);
		return usuario;
	}

	@Override
	public List<Usuario> listarUsuarios() {
		return (List<Usuario>) repository.findAll();
	}

	@Override
	public Usuario listarId(int id) {
		return repository.findById(id).orElse(new Usuario());
	}

	@Override
	public Usuario Guardar(Usuario usuario) {
		return repository.save(usuario);
	}

	@Override
	public Usuario Borrar(int id) {
		Usuario temp = repository.findById(id).orElse(null);
		if (temp == null) {
			return new Usuario();
		} else {
			repository.deleteById(id);
			return temp;
		}
	}

	public Usuario usuarioIns(Usuario usuario) {
		return repository.save(usuario);
	}

	@Override
	public LoginResponse loginUsuario(LoginDTO loginDTO) {
		String msg = "";
		Usuario usuario1 = repository.findByUsuario(loginDTO.getUsuario()).orElse(null);
		if (usuario1 != null) {
			String password = loginDTO.getClave();
			String encodedPassword = usuario1.getClave();
			Boolean isPwdRight = passwordEncoder.matches(password, encodedPassword);
			if (isPwdRight) {
				Optional<Usuario> usuario = repository.findByUsuario(loginDTO.getUsuario());
				if (usuario.isPresent()) {
					return new LoginResponse("Login Success", true);
				} else {
					return new LoginResponse("Login Failed", false);
				}
			} else {
				return new LoginResponse("Password Not Match", false);
			}
		} else {
			return new LoginResponse("Email not exists", false);
		}
	}

}
