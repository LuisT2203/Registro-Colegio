package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RolDTO {

	private int id_rol;
	@NotBlank(message = "El nombre del rol no puede ser vacío")
	private String nombre_rol;

	public RolDTO() {
		super();
	}

	public RolDTO(int id_rol, String nombre_rol) {
		super();
		this.id_rol = id_rol;
		this.nombre_rol = nombre_rol;
	}

}
