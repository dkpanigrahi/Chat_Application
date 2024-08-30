package com.chat.entity;

public class JwtResponse {
    
	private String jwtToken;

	public JwtResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}
	
	
	public JwtResponse() {
		super();
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
   
	
   
}
