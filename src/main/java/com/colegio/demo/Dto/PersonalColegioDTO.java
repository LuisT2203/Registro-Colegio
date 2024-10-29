package com.colegio.demo.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PersonalColegioDTO {

	private int id_personal;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre_personal;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido_personal;
	@NotBlank(message = "El Cargo no puede ser vacío")
	private String cargo_personal;
	
	public PersonalColegioDTO() {
		super();
	}

	public PersonalColegioDTO(int id_personal, String nombre_personal, String apellido_personal, String cargo_personal) {
		super();
		this.id_personal = id_personal;
		this.nombre_personal = nombre_personal;
		this.apellido_personal = apellido_personal;
		this.cargo_personal = cargo_personal;
	}

	

}
