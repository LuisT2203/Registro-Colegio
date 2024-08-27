package com.colegio.demo.modelo;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "Encargo")
public class Encargo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_enc;
	private LocalDate fecha_enc;
	private String encargoNom;
	private int numeroRegistro;
	
	
	
	
	public Encargo() {
		super();
	}
	
	
	public Encargo(int id_enc, LocalDate fecha_enc, String encargoNom, int numeroRegistro) {
		super();
		this.id_enc = id_enc;
		this.fecha_enc = fecha_enc;
		this.encargoNom = encargoNom;
		this.numeroRegistro = numeroRegistro;
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
	public String getEncargoNom() {
		return encargoNom;
	}
	public void setEncargoNom(String encargoNom) {
		this.encargoNom = encargoNom;
	}


	public int getNumeroRegistro() {
		return numeroRegistro;
	}


	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}
	
	
	
	

}
