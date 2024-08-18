package com.colegio.demo.response;

public class LoginResponse {
	String message;
	Boolean status;
	
	
	public LoginResponse(String message, Boolean status) {
		super();
		this.message = message;
		this.status = status;
	}


	public LoginResponse() {
		super();
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Boolean getStatus() {
		return status;
	}


	public void setStatus(Boolean status) {
		this.status = status;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LoginResponse [message=");
		builder.append(message);
		builder.append(", status=");
		builder.append(status);
		builder.append("]");
		return builder.toString();
	}
	
	

}
