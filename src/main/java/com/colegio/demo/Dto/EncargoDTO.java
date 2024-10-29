package com.colegio.demo.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EncargoDTO {

	private int id_enc;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha_enc;
	@NotBlank(message = "El Encargo no puede ser vacío")
	private String encargoNom;
	private int numeroRegistro;
	
	
	
	
	public EncargoDTO() {
		super();
	}
	
	
	public EncargoDTO(int id_enc, LocalDate fecha_enc, String encargoNom, int numeroRegistro) {
		super();
		this.id_enc = id_enc;
		this.fecha_enc = fecha_enc;
		this.encargoNom = encargoNom;
		this.numeroRegistro = numeroRegistro;
	}


	


}
