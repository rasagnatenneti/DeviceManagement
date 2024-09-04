package com.inventory.device.dto;

import java.io.Serializable;

public class JwtResponse implements Serializable {
	private static final long serialVersionUID = -281758657556062431L;
	private final String token;

	public JwtResponse(String token) {
		this.token = token;
	}

	public String getToken() {
		return this.token;
	}
}