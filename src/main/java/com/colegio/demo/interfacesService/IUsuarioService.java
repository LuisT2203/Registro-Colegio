package com.colegio.demo.interfacesService;

import java.util.List;

import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.response.LoginResponse;

public interface IUsuarioService {

	Usuario addUsuario(UsuarioDTO usuarioDTO);

	LoginResponse loginUsuario(LoginDTO loginDTO);

	List<Usuario> listarUsuarios();

	Usuario listarId(int id);

	Usuario Guardar(Usuario usuario);

	Usuario Borrar(int id);

}
