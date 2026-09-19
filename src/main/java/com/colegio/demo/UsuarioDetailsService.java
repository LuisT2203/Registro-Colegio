package com.colegio.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.colegio.demo.interfaces.UsuarioRepository;
import com.colegio.demo.modelo.Usuario;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioRepository Usuariorepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = Usuariorepository.findByUsuario(username).orElse(null);
		if (usuario == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UsuarioDetails(usuario);
	}

}
