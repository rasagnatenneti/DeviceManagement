package com.inventory.device.dto;

import java.time.LocalDateTime;

public class DeviceDTO {
	private Long id; // Include the device ID
	private String name;
	private String brand;
	private LocalDateTime creationTime; // Include the creation time

	// No-argument constructor
	public DeviceDTO() {
	}

	// All-argument constructor
	public DeviceDTO(Long id, String name, String brand, LocalDateTime creationTime) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.creationTime = creationTime;
	}

	// Getter and Setter for 'id'
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setCreationTime(LocalDateTime creationTime) {
		this.creationTime = creationTime;
	}
}