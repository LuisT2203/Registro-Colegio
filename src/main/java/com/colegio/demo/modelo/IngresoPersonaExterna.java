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
@Table(name = "ingreso_personaE")
public class IngresoPersonaExterna {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingresoPersonaE; //llave foranea para hacer referencia al objeto personalColegio Nos servira para listar por (id), (No olvidar crear el objeto manytoone para manejar los datos)
	@Column(name = "fecha")
	private LocalDate fecha;
	private LocalTime hora_ingreso;
	private LocalTime hora_salida;
	private String asunto;
	private int numeroRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_personaE", referencedColumnName = "id_personaE")
	private PersonaExterna personaE;
	
	public IngresoPersonaExterna() {
		super();
	}


	


	public IngresoPersonaExterna(int id_ingresoPersonaE, LocalDate fecha, LocalTime hora_ingreso,
			LocalTime hora_salida, String asunto, int numeroRegistro) {
		super();
		this.id_ingresoPersonaE = id_ingresoPersonaE;
		
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
		this.numeroRegistro = numeroRegistro;
	}





	public int getId_ingresoPersonaE() {
		return id_ingresoPersonaE;
	}


	public void setId_ingresoPersonaE(int id_ingresoPersonaE) {
		this.id_ingresoPersonaE = id_ingresoPersonaE;
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


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}
	
	

	public PersonaExterna getPersonaE() {
		return personaE;
	}


	public void setObjPersonaE(PersonaExterna personaE) {
		this.personaE = personaE;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}


	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	
}
