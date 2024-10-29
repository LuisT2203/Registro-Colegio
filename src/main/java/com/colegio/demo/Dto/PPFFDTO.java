package com.colegio.demo.Dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PPFFDTO {

	private int id_ppff;
	@NotBlank(message = "El nombre no puede ser vacío")
	private String nombre_ppff;
	@NotBlank(message = "El apellido no puede ser vacío")
	private String apellido_ppff;
	@NotNull(message = "El dni  no puede ser vacío")
	@Size(max = 8,min = 8)
	private String dni;
	@NotBlank(message = "El nombre alumna no puede ser vacío")
	private String nombre_alu;
	@NotBlank(message = "El apellido alumna no puede ser vacío")
	private String apellido_alu;
	@NotBlank(message = "El año alumna no puede ser vacío")
	private String anio_alu;
	
	public PPFFDTO() {
		super();
	}

	public PPFFDTO(int id_ppff, String nombre_ppff, String apellido_ppff, String dni, String nombre_alu,
				   String apellido_alu, String anio_alu) {
		super();
		this.id_ppff = id_ppff;
		this.nombre_ppff = nombre_ppff;
		this.apellido_ppff = apellido_ppff;
		this.dni = dni;
		this.nombre_alu = nombre_alu;
		this.apellido_alu = apellido_alu;
		this.anio_alu = anio_alu;
	}


}
