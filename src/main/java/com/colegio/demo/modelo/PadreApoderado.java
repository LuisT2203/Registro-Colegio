package com.colegio.demo.modelo;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "padre_apoderado")
@PrimaryKeyJoinColumn(name = "id_persona")
public class PadreApoderado extends Persona {

	public PadreApoderado() {
		super();
	}

	public PadreApoderado(int id_persona, String nombre, String apellido, String dni) {
		super(id_persona, nombre, apellido, dni);
	}

}
