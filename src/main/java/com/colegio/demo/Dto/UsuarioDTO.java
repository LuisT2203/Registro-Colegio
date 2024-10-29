package com.colegio.demo.Dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioDTO {
	
	
	private Integer id;
	@NotBlank(message = "El usuario no puede ser vacío")
	private String usuario;
	@NotBlank(message = "La clave no puede ser vacía")
	private String clave;
	@NotBlank(message = "El tipo no puede ser vacío")
	private String tipo;
	
	public UsuarioDTO(Integer id, String usuario, String clave, String tipo) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.clave = clave;
		this.tipo = tipo;
	}

	public UsuarioDTO() {
		super();
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
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
