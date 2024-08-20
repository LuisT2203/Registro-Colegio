package com.colegio.demo.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Usuario;


public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findOneByCorreoAndClave(String correo,String clave);
	
	public Usuario findByCorreo (String correo); 
	
	
	
	
}
