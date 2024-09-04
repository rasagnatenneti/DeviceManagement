package com.inventory.device.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Device {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment primary key
	private Long id;

	private String name;
	private String brand;

	@Column(name = "creation_time", nullable = false, updatable = false)
	private LocalDateTime creationTime = LocalDateTime.now();

	// Getter and Setter for 'id'
	public Long getId() {
		return id;
	}

	// Getter and Setter for 'name'
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// Getter and Setter for 'brand'
	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	// Getter and Setter for 'creationTime'
	public LocalDateTime getCreationTime() {
		return creationTime;
	}
}
