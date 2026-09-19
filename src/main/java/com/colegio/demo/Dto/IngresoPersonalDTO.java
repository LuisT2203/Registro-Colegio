package com.colegio.demo.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class IngresoPersonalDTO {

	private int id_ingreso;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha;
	@NotNull(message = "La Hora de Ingreso no puede ser vacía")
	private LocalTime hora_ingreso;
	@NotNull(message = "La Hora de Salida no puede ser vacía")
	private LocalTime hora_salida;
	private String motivo;
	private int numero_registro;
	@NotNull(message = "La persona no puede ser vacía")
	private PersonaDTO persona;
	private AlumnaDTO alumna;

	public IngresoPersonalDTO() {
		super();
	}

	public IngresoPersonalDTO(int id_ingreso, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida,
			String motivo, int numero_registro, PersonaDTO persona, AlumnaDTO alumna) {
		super();
		this.id_ingreso = id_ingreso;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.motivo = motivo;
		this.numero_registro = numero_registro;
		this.persona = persona;
		this.alumna = alumna;
	}

}
