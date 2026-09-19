package com.colegio.demo.modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingreso_personal")
public class IngresoPersonal {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingreso;
	@Column(name = "fecha")
	private LocalDate fecha;
	private LocalTime hora_ingreso;
	private LocalTime hora_salida;
	private String motivo;
	private int numero_registro;

	@ManyToOne
	@JoinColumn(name = "id_persona", referencedColumnName = "id_persona")
	private Persona persona;

	@ManyToOne
	@JoinColumn(name = "id_alumna", referencedColumnName = "id_persona")
	private Alumna alumna;

	public IngresoPersonal() {
		super();
	}

	public IngresoPersonal(int id_ingreso, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida,
			String motivo, int numero_registro, Persona persona, Alumna alumna) {
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

	public String getNombre() {
		return this.persona != null ? this.persona.getNombre() : "-----";
	}

	public String getApellido() {
		return this.persona != null ? this.persona.getApellido() : "-----";
	}

	public int getId_ingreso() {
		return id_ingreso;
	}

	public void setId_ingreso(int id_ingreso) {
		this.id_ingreso = id_ingreso;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora_ingreso() {
		return hora_ingreso;
	}

	public void setHora_ingreso(LocalTime hora_ingreso) {
		this.hora_ingreso = hora_ingreso;
	}

	public LocalTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public int getNumero_registro() {
		return numero_registro;
	}

	public void setNumero_registro(int numero_registro) {
		this.numero_registro = numero_registro;
	}

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public Alumna getAlumna() {
		return alumna;
	}

	public void setAlumna(Alumna alumna) {
		this.alumna = alumna;
	}

	public double getHorasTrabajadas() {
		if (hora_ingreso == null || hora_salida == null)
			return 0;
		long minutos = Duration.between(hora_ingreso, hora_salida).toMinutes();
		return Math.round((minutos / 60.0) * 100.0) / 100.0;
	}

	public String getHoraIngresoStr() {
		return hora_ingreso != null ? hora_ingreso.toString() : "--:--";
	}

	public String getHoraSalidaStr() {
		return hora_salida != null ? hora_salida.toString() : "--:--";
	}

	public String getFechaStr() {
		return fecha != null ? fecha.toString() : "----";
	}

}
