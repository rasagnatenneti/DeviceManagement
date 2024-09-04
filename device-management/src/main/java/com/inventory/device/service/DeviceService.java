package com.inventory.device.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.device.dto.DeviceDTO;
import com.inventory.device.model.Device;
import com.inventory.device.repository.DeviceRepository;

@Service
public class DeviceService {

	@Autowired
	private DeviceRepository deviceRepository;

	public DeviceDTO getDeviceById(Long id) {
		// Get the device by ID and throw an exception if not found
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Device not found with id: " + id));

		// Convert Device entity to DeviceDTO and return
		return convertToDeviceDTO(device);
	}

	public List<DeviceDTO> getAllDevices() {
		// Get all devices
		List<Device> devices = deviceRepository.findAll();

		// Convert list of Device entities to list of DeviceDTOs
		return devices.stream().map(this::convertToDeviceDTO).collect(Collectors.toList());
	}

	public DeviceDTO addDevice(DeviceDTO deviceDTO) {
		// Check if a device with the same name and brand already exists
		Optional<Device> existingDevice = deviceRepository.findByBrandAndName(deviceDTO.getBrand(),
				deviceDTO.getName());
		if (existingDevice.isPresent()) {
			throw new RuntimeException(
					"A device with the same name already exists for the brand: " + deviceDTO.getBrand());
		}

		// Add a new device with the provided details
		Device device = new Device();
		device.setName(deviceDTO.getName());
		device.setBrand(deviceDTO.getBrand());
		Device savedDevice = deviceRepository.save(device);

		// Convert the saved Device entity to DeviceDTO and return
		return convertToDeviceDTO(savedDevice);
	}

	public DeviceDTO updateDevice(Long id, DeviceDTO deviceDTO) {
		// Get the existing device from the repository
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Device not found with id: " + id));

		// Update only the fields that are provided in the request
		if (deviceDTO.getName() != null && !deviceDTO.getName().isEmpty()) {
			device.setName(deviceDTO.getName());
		}
		if (deviceDTO.getBrand() != null && !deviceDTO.getBrand().isEmpty()) {
			device.setBrand(deviceDTO.getBrand());
		}

		// Save and return the updated device as a DeviceDTO
		Device updatedDevice = deviceRepository.save(device);
		return convertToDeviceDTO(updatedDevice);
	}

	public void deleteDevice(Long id) {
		// Check if the device exists
		Device device = deviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Device not found with id: " + id));

		// Delete the device if it exists
		deviceRepository.deleteById(id);
	}

	public List<DeviceDTO> searchDevicesByBrand(String brand) {
		// Search for devices by brand
		List<Device> devices = deviceRepository.findByBrand(brand);

		// Convert list of Device entities to list of DeviceDTOs
		return devices.stream().map(this::convertToDeviceDTO).collect(Collectors.toList());
	}

	// Utility method to convert a Device entity to a DeviceDTO
	private DeviceDTO convertToDeviceDTO(Device device) {
		return new DeviceDTO(device.getId(), device.getName(), device.getBrand(), device.getCreationTime());
	}
}