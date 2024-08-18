package com.colegio.demo.interfacesService;

import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.Dto.UsuarioDTO;
import com.colegio.demo.response.LoginResponse;

public interface IUsuarioService {

	String addUsuario(UsuarioDTO usuarioDTO);

	LoginResponse loginUsuario(LoginDTO loginDTO);

	

}
