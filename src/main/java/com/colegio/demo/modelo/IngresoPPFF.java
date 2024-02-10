package com.colegio.demo.modelo;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "ingreso_ppff")
public class IngresoPPFF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingresoPPFF;
	private int id_ppff; //llave foranea para hacer referencia al objeto ppff, (No olvidar crear el objeto manytoone para manejar los datos)
	@Column(name = "fecha")
	private Date fecha;
	private LocalTime hora_ingreso;
	private LocalTime hora_salida;
	private String asunto;
	private int numeroRegistro;
	
	public IngresoPPFF() {
		super();
	}

	

	public IngresoPPFF(int id_ingresoPPFF, int id_ppff, Date fecha, LocalTime hora_ingreso, LocalTime hora_salida, String asunto,
			int numeroRegistro) {
		super();
		this.id_ingresoPPFF = id_ingresoPPFF;
		this.id_ppff = id_ppff;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
		this.numeroRegistro = numeroRegistro;
	}



	public int getId_ingresoPPFF() {
		return id_ingresoPPFF;
	}

	public void setId_ingresoPPFF(int id_ingresoPPFF) {
		this.id_ingresoPPFF = id_ingresoPPFF;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
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

	public int getId_ppff() {
		return id_ppff;
	}

	public void setId_ppff(int id_ppff) {
		this.id_ppff = id_ppff;
	}
	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_ppff", insertable=false,updatable=false)
	private PPFF objPPFF;

	public PPFF getObjPPFF() {
		return objPPFF;
	}

	public void setObjPPFF(PPFF objPPFF) {
		this.objPPFF = objPPFF;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	
	
}
