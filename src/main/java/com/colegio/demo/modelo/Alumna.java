package com.colegio.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "alumna")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Alumna extends Persona {
	private String grado;
	private String nivel;
	private String seccion;

	public Alumna() {
		super();
	}

	public Alumna(int id_persona, String nombre, String apellido, String grado, String nivel, String seccion) {
		super(id_persona, nombre, apellido, null);
		this.grado = grado;
		this.nivel = nivel;
		this.seccion = seccion;
	}

	public String getGrado() {
		return grado;
	}

	public void setGrado(String grado) {
		this.grado = grado;
	}

	public String getNivel() {
		return nivel;
	}

	public void setNivel(String nivel) {
		this.nivel = nivel;
	}

	public String getSeccion() {
		return seccion;
	}

	public void setSeccion(String seccion) {
		this.seccion = seccion;
	}

}
