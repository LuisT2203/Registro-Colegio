package com.colegio.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "trabajador")
@PrimaryKeyJoinColumn(name = "id_persona")
public class Trabajador extends Persona {
	private String cargo;

	public Trabajador() {
		super();
	}

	public Trabajador(int id_persona, String nombre, String apellido, String dni, String cargo) {
		super(id_persona, nombre, apellido, dni);
		this.cargo = cargo;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

}
