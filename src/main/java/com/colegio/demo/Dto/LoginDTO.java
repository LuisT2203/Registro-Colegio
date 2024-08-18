package com.colegio.demo.Dto;

public class LoginDTO {
	
	private String correo;
	private String clave;
	
	public LoginDTO(String correo, String clave) {
		super();
		this.correo = correo;
		this.clave = clave;
	}

	public LoginDTO() {
		super();
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

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginDTO [correo=");
		builder.append(correo);
		builder.append(", clave=");
		builder.append(clave);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	

}
