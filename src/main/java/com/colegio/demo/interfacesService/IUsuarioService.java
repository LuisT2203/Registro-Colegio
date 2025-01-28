package com.colegio.demo.interfacesService;

import com.colegio.demo.Dto.LoginDTO;
import com.colegio.demo.modelo.Usuario;
import com.colegio.demo.response.LoginResponse;

public interface IUsuarioService {

	Usuario addUsuario(Usuario usuarioDTO);

	LoginResponse loginUsuario(LoginDTO loginDTO);

	

}
