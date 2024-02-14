package com.colegio.demo.modelo;
import java.sql.Date;

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
@Table(name = "ingreso_personal")
public class IngresoPersonalColegio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_ingresoPersonal;
	@Column(name = "id_personal")
	private int id_personal; //llave foranea para hacer referencia al objeto personalColegio, (No olvidar crear el objeto manytoone para manejar los datos)
	@Column(name = "fecha")
	private Date fecha;
	private LocalTime  hora_ingreso;
	private LocalTime  hora_salida;
	private int numeroRegistro;
	public IngresoPersonalColegio() {
		super();
	}

	

	public IngresoPersonalColegio(int id_ingresoPersonal, int id_personal, Date fecha, LocalTime hora_ingreso,
			LocalTime hora_salida, int numeroRegistro) {
		super();
		this.id_ingresoPersonal = id_ingresoPersonal;
		this.id_personal = id_personal;
		this.fecha = fecha;
		this.hora_ingreso = hora_ingreso;
		this.hora_salida = hora_salida;
		this.numeroRegistro = numeroRegistro;
	}



	public int getId_ingresoPersonal() {
		return id_ingresoPersonal;
	}

	public void setId_ingresoPersonal(int id_ingresoPersonal) {
		this.id_ingresoPersonal = id_ingresoPersonal;
	}

	

	

	public int getId_personal() {
		return id_personal;
	}

	public void setId_personal(int id_personal) {
		this.id_personal = id_personal;
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

	public void setHora_ingreso(LocalTime localTime) {
		this.hora_ingreso = localTime;
	}

	public LocalTime getHora_salida() {
		return hora_salida;
	}

	public void setHora_salida(LocalTime hora_salida) {
		this.hora_salida = hora_salida;
	}
	
	
	public int getNumeroRegistro() {
		return numeroRegistro;
	}



	public void setNumeroRegistro(int numeroRegistro) {
		this.numeroRegistro = numeroRegistro;
	}


	@JsonBackReference
	@ManyToOne
	@JoinColumn(name="id_personal", insertable=false,updatable=false)
	private PersonalColegio objPersonal;

	public PersonalColegio getObjPersonal() {
		return objPersonal;
	}

	public void setObjPersonal(PersonalColegio objPersonal) {
		this.objPersonal = objPersonal;
	}
	
	
	
}
