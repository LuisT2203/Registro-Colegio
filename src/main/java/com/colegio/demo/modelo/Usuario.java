package com.colegio.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@Column(name = "id_usu", length=45)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String correo;
	private String tipo;
	private String clave;
	
	
	

	public Usuario() {
		super();
	}

	public Usuario(Integer id, String correo,String tipo, String clave ) {
		super();
		this.id = id;
		this.correo = correo;
		this.tipo = tipo;
		this.clave = clave;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", correo=");
		builder.append(correo);
		builder.append(", clave=");
		builder.append(clave);
		builder.append(", tipo=");
		builder.append(tipo);
		builder.append("]");
		return builder.toString();
	}	
}
