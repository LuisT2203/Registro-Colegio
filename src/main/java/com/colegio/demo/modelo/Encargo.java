package com.colegio.demo.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "encargo")
public class Encargo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_enc;
	private LocalDate fecha_enc;
	private String encargo;
	private int numero_registro;

	public Encargo() {
		super();
	}

	public Encargo(int id_enc, LocalDate fecha_enc, String encargo, int numero_registro) {
		super();
		this.id_enc = id_enc;
		this.fecha_enc = fecha_enc;
		this.encargo = encargo;
		this.numero_registro = numero_registro;
	}

	public int getId_enc() {
		return id_enc;
	}

	public void setId_enc(int id_enc) {
		this.id_enc = id_enc;
	}

	public LocalDate getFecha_enc() {
		return fecha_enc;
	}

	public void setFecha_enc(LocalDate fecha_enc) {
		this.fecha_enc = fecha_enc;
	}

	public String getEncargo() {
		return encargo;
	}

	public void setEncargo(String encargo) {
		this.encargo = encargo;
	}

	public int getNumero_registro() {
		return numero_registro;
	}

	public void setNumero_registro(int numero_registro) {
		this.numero_registro = numero_registro;
	}

}
