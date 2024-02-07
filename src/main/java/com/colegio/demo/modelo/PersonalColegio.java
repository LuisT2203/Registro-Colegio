package com.colegio.demo.modelo;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
@Entity
@Table(name = "PersonalColegio")
public class PersonalColegio {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	 @JsonProperty("id_personal")
	private int id_personal;
	private String nombre_personal;
	private String apellido_personal;
	private String cargo_personal;
	
	public PersonalColegio() {
		super();
	}

	public PersonalColegio(int id_personal, String nombre_personal, String apellido_personal, String cargo_personal) {
		super();
		this.id_personal = id_personal;
		this.nombre_personal = nombre_personal;
		this.apellido_personal = apellido_personal;
		this.cargo_personal = cargo_personal;
	}

	public int getId_personal() {
		return id_personal;
	}

	public void setId_personal(int id_personal) {
		this.id_personal = id_personal;
	}

	public String getNombre_personal() {
		return nombre_personal;
	}

	public void setNombre_personal(String nombre_personal) {
		this.nombre_personal = nombre_personal;
	}

	public String getApellido_personal() {
		return apellido_personal;
	}

	public void setApellido_personal(String apellido_personal) {
		this.apellido_personal = apellido_personal;
	}

	public String getCargo_personal() {
		return cargo_personal;
	}

	public void setCargo_personal(String cargo_personal) {
		this.cargo_personal = cargo_personal;
	}
	@JsonManagedReference
	@OneToMany(mappedBy = "objPersonal")
    private List<IngresoPersonalColegio> ingresosPersonales;

	public List<IngresoPersonalColegio> getIngresosPersonales() {
		return ingresosPersonales;
	}

	public void setIngresosPersonales(List<IngresoPersonalColegio> ingresosPersonales) {
		this.ingresosPersonales = ingresosPersonales;
	}
	

}
