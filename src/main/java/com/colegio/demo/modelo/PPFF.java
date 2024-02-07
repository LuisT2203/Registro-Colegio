package com.colegio.demo.modelo;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import jakarta.persistence.Table;
@Entity
@Table(name = "ppff")
public class PPFF {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ppff;
	private String nombre_ppff;
	private String apellido_ppff;
	private String dni;
	private String nombre_alu;
	private String apellido_alu;
	private int año_alu;
	
	public PPFF() {
		super();
	}

	public PPFF(int id_ppff, String nombre_ppff, String apellido_ppff, String dni, String nombre_alu,
			String apellido_alu, int año_alu) {
		super();
		this.id_ppff = id_ppff;
		this.nombre_ppff = nombre_ppff;
		this.apellido_ppff = apellido_ppff;
		this.dni = dni;
		this.nombre_alu = nombre_alu;
		this.apellido_alu = apellido_alu;
		this.año_alu = año_alu;
	}

	public int getId_ppff() {
		return id_ppff;
	}

	public void setId_ppff(int id_ppff) {
		this.id_ppff = id_ppff;
	}

	public String getNombre_ppff() {
		return nombre_ppff;
	}

	public void setNombre_ppff(String nombre_ppff) {
		this.nombre_ppff = nombre_ppff;
	}

	public String getApellido_ppff() {
		return apellido_ppff;
	}

	public void setApellido_ppff(String apellido_ppff) {
		this.apellido_ppff = apellido_ppff;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getNombre_alu() {
		return nombre_alu;
	}

	public void setNombre_alu(String nombre_alu) {
		this.nombre_alu = nombre_alu;
	}

	public String getApellido_alu() {
		return apellido_alu;
	}

	public void setApellido_alu(String apellido_alu) {
		this.apellido_alu = apellido_alu;
	}

	public int getAño_alu() {
		return año_alu;
	}

	public void setAño_alu(int año_alu) {
		this.año_alu = año_alu;
	}
	
	
	

}
