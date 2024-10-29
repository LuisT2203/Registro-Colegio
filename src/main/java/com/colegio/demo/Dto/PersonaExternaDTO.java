package com.colegio.demo.Dto;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PersonaExternaDTO {

	private int id_personaE;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre_personaE;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido_personaE;
	@NotNull(message = "El dni  no puede ser vacío")
	@Size(max = 8,min = 8)
	private int dni;
	
	public PersonaExternaDTO() {
		super();
	}
	public PersonaExternaDTO(int id_personaE, String nombre_personaE, String apellido_personaE, int dni) {
		super();
		this.id_personaE = id_personaE;
		this.nombre_personaE = nombre_personaE;
		this.apellido_personaE = apellido_personaE;
		this.dni = dni;
	}
	

	
}
