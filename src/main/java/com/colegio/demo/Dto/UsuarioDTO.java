package com.colegio.demo.Dto;



public class UsuarioDTO {
	
	
	private Integer id;
	private String correo;
	private String clave;
	private String tipo;
	
	public UsuarioDTO(Integer id, String correo, String clave, String tipo) {
		super();
		this.id = id;
		this.correo = correo;
		this.clave = clave;
		this.tipo = tipo;
	}

	public UsuarioDTO() {
		super();
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
		builder.append("UsuarioDTO [id=");
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
