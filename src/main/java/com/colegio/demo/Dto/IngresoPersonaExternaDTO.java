package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class IngresoPersonaExternaDTO {

	private int id_ingresoPersonaE;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha;
	@NotNull(message = "La Hora de Ingreso no puede ser vacía")
	private LocalTime hora_ingreso;
	@NotNull(message = "La Hora de Salida no puede ser vacía")
	private LocalTime hora_salida;
	@NotBlank(message = "El Asunto no puede ser vacío")
	private String asunto;
	private int numeroRegistro;
	@NotNull(message = "La Persona Externa no puede ser vacía")
	private PersonaExternaDTO personaE;
	
	public IngresoPersonaExternaDTO() {
		super();
	}

	public IngresoPersonaExternaDTO(int id_ingresoPersonaE, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida, String asunto, int numeroRegistro, PersonaExternaDTO personaE) {
		super();
		this.id_ingresoPersonaE = id_ingresoPersonaE;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
		this.numeroRegistro = numeroRegistro;
		this.personaE = personaE;
	}
}
