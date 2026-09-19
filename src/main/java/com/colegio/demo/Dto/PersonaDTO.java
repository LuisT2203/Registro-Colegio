package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonaDTO {

	private int id_persona;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido;
	private String dni;
	private String tipo;
	private String grado;
	private String nivel;
	private String seccion;
	private String cargo;

	public PersonaDTO() {
		super();
	}

	public PersonaDTO(int id_persona, String nombre, String apellido, String dni) {
		super();
		this.id_persona = id_persona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
	}

}
