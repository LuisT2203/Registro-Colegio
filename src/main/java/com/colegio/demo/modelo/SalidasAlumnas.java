package com.colegio.demo.modelo;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "salidas")
public class SalidasAlumnas {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_salida;
	private String motivo;
	private String personaquerecoje;
	private String DNI;
	private String telefono;
	private LocalDate fecha;
	private LocalTime hora_salida;
	private LocalTime hora_retorno;
	private int numeroRegistro;

	private Integer id_padre;
	private Integer id_alumna;

	@ManyToOne
	@JoinColumn(name = "id_alumna", referencedColumnName = "id_persona", insertable = false, updatable = false)
	private Alumna alumna;

	@ManyToOne
	@JoinColumn(name = "id_padre", referencedColumnName = "id_persona", insertable = false, updatable = false)
	private PadreApoderado padre;

	public SalidasAlumnas() {
	}

	public SalidasAlumnas(int id_salida, String motivo, String personaquerecoje, String DNI, String telefono,
			LocalDate fecha, LocalTime hora_salida, LocalTime hora_retorno, int numeroRegistro, Alumna alumna) {
		this.id_salida = id_salida;
		this.motivo = motivo;
		this.personaquerecoje = personaquerecoje;
		this.DNI = DNI;
		this.telefono = telefono;
		this.fecha = fecha;
		this.hora_salida = hora_salida;
		this.hora_retorno = hora_retorno;
		this.numeroRegistro = numeroRegistro;
		this.alumna = alumna;
	}

	public int getId_salida() {
		return id_salida;
	}

	public void setId_salida(int id_salida) {
		this.id_salida = id_salida;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getPersonaquerecoje() {
		return personaquerecoje;
	}

	public void setPersonaquerecoje(String personaquerecoje) {
		this.personaquerecoje = personaquerecoje;
	}

	public String getDNI() {
		return DNI;
	}

	public void setDNI(String DNI) {
		this.DNI = DNI;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public LocalTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}

	public LocalTime getHora_retorno() {
		return hora_retorno;
	}

	public void setHora_retorno(LocalTime hora_retorno) {
		this.hora_retorno = hora_retorno;
	}

	public int getNumeroRegistro() {
		return numeroRegistro;
	}

	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}

	public Integer getId_padre() {
		return id_padre;
	}

	public void setId_padre(Integer id_padre) {
		this.id_padre = id_padre;
	}

	public Integer getId_alumna() {
		return id_alumna;
	}

	public void setId_alumna(Integer id_alumna) {
		this.id_alumna = id_alumna;
	}

	public PadreApoderado getPadre() {
		return padre;
	}

	public void setPadre(PadreApoderado padre) {
		this.padre = padre;
	}

	public Alumna getAlumna() {
		return alumna;
	}

	public void setAlumna(Alumna alumna) {
		this.alumna = alumna;
	}

}
