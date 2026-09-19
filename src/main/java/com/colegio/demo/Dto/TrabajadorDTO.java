package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TrabajadorDTO {

	private int id_persona;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido;
	private String dni;
	@NotBlank(message = "El cargo no puede ser vacío")
	private String cargo;

	public TrabajadorDTO() {
		super();
	}

	public TrabajadorDTO(int id_persona, String nombre, String apellido, String dni, String cargo) {
		super();
		this.id_persona = id_persona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.cargo = cargo;
	}

}
