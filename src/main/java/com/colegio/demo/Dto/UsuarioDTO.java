package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UsuarioDTO {

	private Integer id;
	@NotBlank(message = "El usuario no puede ser vacío")
	private String usuario;
	@NotBlank(message = "La clave no puede ser vacía")
	private String clave;
	private boolean estado;
	@NotNull(message = "El rol no puede ser vacío")
	private Integer id_rol;

	public UsuarioDTO() {
		super();
	}

	public UsuarioDTO(Integer id, String usuario, String clave, boolean estado, Integer id_rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.clave = clave;
		this.estado = estado;
		this.id_rol = id_rol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UsuarioDTO [id=");
		builder.append(id);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", clave=");
		builder.append(clave);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", id_rol=");
		builder.append(id_rol);
		builder.append("]");
		return builder.toString();
	}

}
