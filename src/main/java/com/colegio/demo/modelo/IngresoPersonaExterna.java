package com.colegio.demo.modelo;
import java.sql.Date;
import java.sql.Time;

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
	private int id_ingresoPersonaE;
	private int id_personaE; //llave foranea para hacer referencia al objeto personalColegio, (No olvidar crear el objeto manytoone para manejar los datos)
	private Date fecha;
	private Time hora_ingreso;
	private Time hora_salida;
	private String asunto;
	
	public IngresoPersonaExterna() {
		super();
	}


	public IngresoPersonaExterna(int id_ingresoPersonaE, int id_personaE, Date fecha, Time hora_ingreso,
			Time hora_salida, String asunto) {
		super();
		this.id_ingresoPersonaE = id_ingresoPersonaE;
		this.id_personaE = id_personaE;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
	}


	public int getId_ingresoPersonaE() {
		return id_ingresoPersonaE;
	}


	public void setId_ingresoPersonaE(int id_ingresoPersonaE) {
		this.id_ingresoPersonaE = id_ingresoPersonaE;
	}


	public int getId_personaE() {
		return id_personaE;
	}


	public void setId_personaE(int id_personaE) {
		this.id_personaE = id_personaE;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public Time getHora_ingreso() {
		return hora_ingreso;
	}


	public void setHora_ingreso(Time hora_ingreso) {
		this.hora_ingreso = hora_ingreso;
	}


	public Time getHora_salida() {
		return hora_salida;
	}


	public void setHora_salida(Time hora_salida) {
		this.hora_salida = hora_salida;
	}


	public String getAsunto() {
		return asunto;
	}


	public void setAsunto(String asunto) {
		this.asunto = asunto;
	}


	public PersonaExterna getObjPersonaE() {
		return objPersonaE;
	}


	public void setObjPersonaE(PersonaExterna objPersonaE) {
		this.objPersonaE = objPersonaE;
	}


	@ManyToOne
	@JoinColumn(name="id_personaE", insertable=false,updatable=false)
	private PersonaExterna objPersonaE;
}
