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
@Table(name = "ingreso_ppff")
public class IngresoPPFF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingresoPPFF;
	 //llave foranea para hacer referencia al objeto ppff, (No olvidar crear el objeto manytoone para manejar los datos)
	@Column(name = "fecha")
	private LocalDate fecha;
	private LocalTime hora_ingreso;
	private LocalTime hora_salida;
	private String asunto;
	private int numeroRegistro;
	
	@ManyToOne
	@JoinColumn(name="id_ppff",referencedColumnName = "id_ppff")
	private PPFF padre;
	
	public IngresoPPFF() {
		super();
	}

	
	public IngresoPPFF(int id_ingresoPPFF, LocalDate fecha, LocalTime hora_ingreso, LocalTime hora_salida,
			String asunto, int numeroRegistro, PPFF padre) {
		super();
		this.id_ingresoPPFF = id_ingresoPPFF;
		
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.asunto = asunto;
		this.numeroRegistro = numeroRegistro;
		this.padre = padre;
	}






	public int getId_ingresoPPFF() {
		return id_ingresoPPFF;
	}

	public void setId_ingresoPPFF(int id_ingresoPPFF) {
		this.id_ingresoPPFF = id_ingresoPPFF;
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

	
	
	

	public PPFF getPadre() {
		return padre;
	}

	public void setPadre(PPFF padre) {
		this.padre = padre;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	
	
}
