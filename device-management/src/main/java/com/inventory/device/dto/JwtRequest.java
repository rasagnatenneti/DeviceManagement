package com.inventory.device.dto;

import java.io.Serializable;

public class JwtRequest implements Serializable {
	private static final long serialVersionUID = 7713686885975212906L;
	private String username;
	private String password;

	// Getters and Setters
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}