package com.colegio.demo.Dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
@Data
public class IngresoPersonalColegioDTO {

	private int id_ingresoPersonal;
	@NotNull(message = "La fecha no puede ser vacía")
	private LocalDate fecha;
	@NotNull(message = "La Hora de Ingreso no puede ser vacía")
	private LocalTime  hora_ingreso;
	@NotNull(message = "La Hora de Salida no puede ser vacía")
	private LocalTime  hora_salida;
	private int numeroRegistro;
	@NotNull(message = "El personal no puede ser vacío")
	private PersonalColegioDTO personal;

	
	public IngresoPersonalColegioDTO() {
		super();
	}

	public IngresoPersonalColegioDTO(int id_ingresoPersonal, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida, int numeroRegistro, PersonalColegioDTO personal) {
		super();
		this.id_ingresoPersonal = id_ingresoPersonal;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.numeroRegistro = numeroRegistro;
		this.personal = personal;
	}
}
