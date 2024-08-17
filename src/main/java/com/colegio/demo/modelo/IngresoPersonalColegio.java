package com.colegio.demo.modelo;

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
public class IngresoPersonalColegio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingresoPersonal;
	@Column(name = "fecha")
	private LocalDate fecha;
	private LocalTime  hora_ingreso;
	private LocalTime  hora_salida;
	private int numeroRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_personal",  referencedColumnName = "id_personal")
	private PersonalColegio personal;

	
	public IngresoPersonalColegio() {
		super();
	}

	

	public IngresoPersonalColegio(int id_ingresoPersonal,  LocalDate fecha, LocalTime hora_ingreso,
			LocalTime hora_salida, int numeroRegistro) {
		super();
		this.id_ingresoPersonal = id_ingresoPersonal;
		
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.numeroRegistro = numeroRegistro;
	}



	public int getId_ingresoPersonal() {
		return id_ingresoPersonal;
	}

	public void setId_ingresoPersonal(int id_ingresoPersonal) {
		this.id_ingresoPersonal = id_ingresoPersonal;
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

	public void setHora_ingreso(LocalTime localTime) {
		this.hora_ingreso = localTime;
	}

	public LocalTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}
	
	
	public int getNumeroRegistro() {
		return numeroRegistro;
	}



	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	
	public PersonalColegio getPersonal() {
		return personal;
	}

	public void setObjPersonal(PersonalColegio personal) {
		this.personal = personal;
	}
	
	
	
}
