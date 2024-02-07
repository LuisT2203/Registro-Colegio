package com.colegio.demo.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
@Entity
@Table(name = "PersonaExterna")
public class PersonaExterna {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_personaE;
	private String nombre_personaE;
	private String apellido_personaE;
	private int dni;
	public PersonaExterna() {
		super();
	}
	public PersonaExterna(int id_personaE, String nombre_personaE, String apellido_personaE, int dni) {
		super();
		this.id_personaE = id_personaE;
		this.nombre_personaE = nombre_personaE;
		this.apellido_personaE = apellido_personaE;
		this.dni = dni;
	}
	public int getId_personaE() {
		return id_personaE;
	}
	public void setId_personaE(int id_personaE) {
		this.id_personaE = id_personaE;
	}
	public String getNombre_personaE() {
		return nombre_personaE;
	}
	public void setNombre_personaE(String nombre_personaE) {
		this.nombre_personaE = nombre_personaE;
	}
	public String getApellido_personaE() {
		return apellido_personaE;
	}
	public void setApellido_personaE(String apellido_personaE) {
		this.apellido_personaE = apellido_personaE;
	}
	public int getDni() {
		return dni;
	}
	public void setDni(int dni) {
		this.dni = dni;
	}
	
	
}
