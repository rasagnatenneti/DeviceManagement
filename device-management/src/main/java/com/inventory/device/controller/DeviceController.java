package com.inventory.device.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.device.dto.ApiResponse;
import com.inventory.device.dto.DeviceDTO;
import com.inventory.device.service.DeviceService;

@RestController
@RequestMapping("/devices")
public class DeviceController {

	@Autowired
	private DeviceService deviceService;

	@PostMapping
	public ResponseEntity<ApiResponse<DeviceDTO>> addDevice(@RequestBody DeviceDTO deviceDTO) {
		DeviceDTO createdDevice = deviceService.addDevice(deviceDTO);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new ApiResponse<>(true, "Device created successfully", createdDevice));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<DeviceDTO>> getDeviceById(@PathVariable Long id) {
		DeviceDTO device = deviceService.getDeviceById(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Device fetched successfully", device));
	}

	@GetMapping
	public ResponseEntity<ApiResponse<List<DeviceDTO>>> listAllDevices() {
		List<DeviceDTO> devices = deviceService.getAllDevices();
		return ResponseEntity.ok(new ApiResponse<>(true, "Devices fetched successfully", devices));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<DeviceDTO>> updateDevice(@PathVariable Long id,
			@RequestBody DeviceDTO deviceDTO) {
		DeviceDTO updatedDevice = deviceService.updateDevice(id, deviceDTO);
		return ResponseEntity.ok(new ApiResponse<>(true, "Device updated successfully", updatedDevice));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<Void>> deleteDevice(@PathVariable Long id) {
		deviceService.deleteDevice(id);
		return ResponseEntity.ok(new ApiResponse<>(true, "Device deleted successfully", null));
	}

	@GetMapping("/search")
	public ResponseEntity<ApiResponse<List<DeviceDTO>>> searchDevicesByBrand(@RequestParam String brand) {
		List<DeviceDTO> devices = deviceService.searchDevicesByBrand(brand);
		return ResponseEntity.ok(new ApiResponse<>(true, "Devices fetched successfully by brand", devices));
	}
}