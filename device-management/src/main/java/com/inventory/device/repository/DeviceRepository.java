package com.inventory.device.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.device.model.Device;

public interface DeviceRepository extends JpaRepository<Device, Long> {
	Optional<Device> findByBrandAndName(String brand, String name);

	List<Device> findByBrand(String brand);
}