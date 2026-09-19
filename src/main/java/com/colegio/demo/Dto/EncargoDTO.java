package com.colegio.demo.Dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class EncargoDTO {

	private int id_enc;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha_enc;
	@NotBlank(message = "El Encargo no puede ser vacío")
	private String encargo;
	private int numero_registro;

	public EncargoDTO() {
		super();
	}

	public EncargoDTO(int id_enc, LocalDate fecha_enc, String encargo, int numero_registro) {
		super();
		this.id_enc = id_enc;
		this.fecha_enc = fecha_enc;
		this.encargo = encargo;
		this.numero_registro = numero_registro;
	}

}
