package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PadreAlumnaDTO {

	private int id_padre;
	private int id_alumna;
	@NotBlank(message = "El parentesco no puede ser vacío")
	private String parentesco;
	private boolean es_tutor_principal;

	private PadreApoderadoDTO padre;
	private AlumnaDTO alumna;

	public PadreAlumnaDTO() {
		super();
	}

	public PadreAlumnaDTO(int id_padre, int id_alumna, String parentesco, boolean es_tutor_principal) {
		super();
		this.id_padre = id_padre;
		this.id_alumna = id_alumna;
		this.parentesco = parentesco;
		this.es_tutor_principal = es_tutor_principal;
	}

}
