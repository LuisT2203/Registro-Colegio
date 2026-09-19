package com.colegio.demo.Dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

@Data
public class SalidaAlumnasDTO {

	private int id_salida;
	private String motivo;
	private String personaquerecoje;
	private String DNI;
	private String telefono;
	private LocalDate fecha;
	private LocalTime hora_salida;
	private LocalTime hora_retorno;
	private int numeroRegistro;
	private Integer id_padre;
	private Integer id_alumna;
	private String parentesco;
	private AlumnaDTO alumna;

	public SalidaAlumnasDTO() {
		super();
	}

	public SalidaAlumnasDTO(int id_salida, String motivo, String personaquerecoje, String DNI, String telefono,
			LocalDate fecha, LocalTime hora_salida, LocalTime hora_retorno, int numeroRegistro, AlumnaDTO alumna) {
		this.id_salida = id_salida;
		this.motivo = motivo;
		this.personaquerecoje = personaquerecoje;
		this.DNI = DNI;
		this.telefono = telefono;
		this.fecha = fecha;
		this.hora_salida = hora_salida;
		this.hora_retorno = hora_retorno;
		this.numeroRegistro = numeroRegistro;
		this.alumna = alumna;
	}

}
