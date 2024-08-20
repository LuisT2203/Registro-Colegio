
  package com.colegio.demo;
  
  import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import
  org.springframework.security.core.userdetails.UserDetails; import
  org.springframework.security.core.userdetails.UserDetailsService; import
  org.springframework.security.core.userdetails.UsernameNotFoundException;
  
  import com.colegio.demo.interfaces.UsuarioRepository; import
  com.colegio.demo.modelo.Usuario;
  
  public class UsuarioDetailsService implements UserDetailsService {
  
  @Autowired 
  private UsuarioRepository Usuariorepository;
  
  
  
  @Override public UserDetails loadUserByUsername(String username) throws
  UsernameNotFoundException {
  
  Usuario usuario = Usuariorepository.findByCorreo(username); 
  if(usuario == null) {
  throw new UsernameNotFoundException(username); 
  } 
  return new User(usuario.getCorreo(),usuario.getClave(), new ArrayList<>()); 
  }
  
  }
 
