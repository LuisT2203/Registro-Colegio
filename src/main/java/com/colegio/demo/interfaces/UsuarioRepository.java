package com.colegio.demo.interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import com.colegio.demo.modelo.Usuario;
@Repository
@EnableJpaRepositories
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
	
	Optional<Usuario> findOneByCorreoAndClave(String correo,String clave);
	
	 Usuario findByCorreo (String correo); 
}
