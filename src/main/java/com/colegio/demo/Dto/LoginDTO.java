package com.colegio.demo.Dto;

public class LoginDTO {
	
	private String usuario;
	private String clave;
	
	public LoginDTO(String usuario, String clave) {
		super();
		this.usuario = usuario;
		this.clave = clave;
	}

	public LoginDTO() {
		super();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginDTO [usuario=");
		builder.append(usuario);
		builder.append(", clave=");
		builder.append(clave);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
