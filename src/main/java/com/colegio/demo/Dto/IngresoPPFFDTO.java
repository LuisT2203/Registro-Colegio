package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class IngresoPPFFDTO {

	private int id_ingresoPPFF;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha;
	@NotNull(message = "La Hora de Ingreso no puede ser vacía")
	private LocalTime hora_ingreso;
	@NotNull(message = "La Hora de Salida no puede ser vacía")
	private LocalTime hora_salida;
	@NotBlank(message = "El Asunto no puede ser vacío")
	private String asunto;
	private int numeroRegistro;
	@NotNull(message = "El padre no puede ser vacío")
	private PPFFDTO padre;
	
	public IngresoPPFFDTO() {
		super();
	}

	
	public IngresoPPFFDTO(int id_ingresoPPFF, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida,
						  String asunto, int numeroRegistro, PPFFDTO padre) {
		super();
		this.id_ingresoPPFF = id_ingresoPPFF;
		
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
		this.numeroRegistro = numeroRegistro;
		this.padre = padre;
	}


	
}
