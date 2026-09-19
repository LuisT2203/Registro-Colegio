package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlumnaDTO {

	private int id_persona;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido;
	@NotBlank(message = "El grado no puede ser vacío")
	private String grado;
	@NotBlank(message = "El nivel no puede ser vacío")
	private String nivel;
	@NotBlank(message = "La sección no puede ser vacía")
	private String seccion;

	public AlumnaDTO() {
		super();
	}

	public AlumnaDTO(int id_persona, String nombre, String apellido, String grado, String nivel, String seccion) {
		super();
		this.id_persona = id_persona;
		this.nombre = nombre;
		this.apellido = apellido;
		this.grado = grado;
		this.nivel = nivel;
		this.seccion = seccion;
	}

}
