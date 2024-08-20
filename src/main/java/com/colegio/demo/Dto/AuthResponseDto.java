package com.colegio.demo.Dto;

public class AuthResponseDto {
	String token;
    String refreshToken;
    
    
	public AuthResponseDto() {
		super();
	}

	public AuthResponseDto(String token, String refreshToken) {
		super();
		this.token = token;
		this.refreshToken = refreshToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
    
    
}
