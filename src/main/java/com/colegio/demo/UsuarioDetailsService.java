/*
 * package com.colegio.demo;
 * 
 * import org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.security.core.userdetails.UserDetails; import
 * org.springframework.security.core.userdetails.UserDetailsService; import
 * org.springframework.security.core.userdetails.UsernameNotFoundException;
 * 
 * import com.colegio.demo.interfaces.UsuarioRepository; import
 * com.colegio.demo.modelo.Usuario;
 * 
 * public class UsuarioDetailsService implements UserDetailsService {
 * 
 * @Autowired private UsuarioRepository repository;
 * 
 * @Override public UserDetails loadUserByUsername(String username) throws
 * UsernameNotFoundException {
 * 
 * Usuario usuario = repository.findByCorreo(username); if(usuario == null) {
 * throw new UsernameNotFoundException("Usuario no encontrado"); } return new
 * UsuarioDetails(usuario); }
 * 
 * }
 */
