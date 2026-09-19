package com.colegio.demo.modelo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario {
	@Id
	@Column(name = "id_usu", length = 45)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String usuario;
	private String clave;
	private boolean estado;

	@ManyToOne
	@JoinColumn(name = "id_rol", referencedColumnName = "id_rol")
	private Rol rol;

	public Usuario() {
		super();
	}

	public Usuario(Integer id, String usuario, String clave, boolean estado, Rol rol) {
		super();
		this.id = id;
		this.usuario = usuario;
		this.clave = clave;
		this.estado = estado;
		this.rol = rol;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Usuario [id=");
		builder.append(id);
		builder.append(", usuario=");
		builder.append(usuario);
		builder.append(", clave=");
		builder.append(clave);
		builder.append(", estado=");
		builder.append(estado);
		builder.append(", rol=");
		builder.append(rol != null ? rol.getNombre_rol() : "null");
		builder.append("]");
		return builder.toString();
	}
}
