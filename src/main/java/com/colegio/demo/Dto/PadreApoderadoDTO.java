package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PadreApoderadoDTO {

	private int id_persona;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido;
	private String dni;

	public PadreApoderadoDTO() {
		super();
	}

	public PadreApoderadoDTO(int id_persona, String nombre, String apellido, String dni) {
		super();
		this.id_persona = id_persona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

}
