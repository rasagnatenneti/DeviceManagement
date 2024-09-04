package com.inventory.device.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.inventory.device.dto.DeviceDTO;
import com.inventory.device.model.Device;
import com.inventory.device.repository.DeviceRepository;

@SpringBootTest
public class DeviceServiceTest {
	@Mock
	private DeviceRepository deviceRepository;

	@InjectMocks
	private DeviceService deviceService;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testGetDeviceById() {
		Long id = 1L;
		Device device = new Device();
		device.setName("Device1");
		device.setBrand("Brand1");
		// creationTime is automatically set

		// Mock the device with a specific id
		when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

		DeviceDTO deviceDTO = deviceService.getDeviceById(id);
		assertNotNull(deviceDTO);
		assertEquals("Device1", deviceDTO.getName());
		assertEquals("Brand1", deviceDTO.getBrand());
	}

	@Test
	public void testGetDeviceById_NotFound() {
		Long id = 1L;

		when(deviceRepository.findById(id)).thenReturn(Optional.empty());

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			deviceService.getDeviceById(id);
		});
		assertEquals("Device not found with id: 1", thrown.getMessage());
	}

	@Test
	public void testAddDevice() {
		// Arrange
		DeviceDTO deviceDTO = new DeviceDTO(1L, "Device1", "Brand1", LocalDateTime.now());
		Device deviceToSave = new Device();
		deviceToSave.setName("Device1");
		deviceToSave.setBrand("Brand1");

		// Create a mock Device with default values
		Device savedDevice = new Device();
		savedDevice.setName("Device1");
		savedDevice.setBrand("Brand1");
		// Do not set id or creationTime here

		// Set up mock behavior
		when(deviceRepository.findByBrandAndName("Brand1", "Device1")).thenReturn(Optional.empty());
		when(deviceRepository.save(any(Device.class))).thenAnswer(invocation -> {
			Device device = invocation.getArgument(0);
			return device;
		});

		// Act
		DeviceDTO savedDeviceDTO = deviceService.addDevice(deviceDTO);

		// Assert
		assertNotNull(savedDeviceDTO);
		assertEquals("Device1", savedDeviceDTO.getName());
		assertEquals("Brand1", savedDeviceDTO.getBrand());
		assertEquals("Brand1", savedDeviceDTO.getBrand()); // Verify ID is set by mock
		// Optionally, verify creationTime if necessary
	}

	@Test
	public void testAddDevice_AlreadyExists() {
		DeviceDTO deviceDTO = new DeviceDTO(null, "Device1", "Brand1", null);

		when(deviceRepository.findByBrandAndName("Brand1", "Device1")).thenReturn(Optional.of(new Device()));

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			deviceService.addDevice(deviceDTO);
		});
		assertEquals("A device with the same name already exists for the brand: Brand1", thrown.getMessage());
	}

	@Test
	public void testUpdateDevice() {
		Long id = 1L;
		DeviceDTO deviceDTO = new DeviceDTO(null, "UpdatedDevice", "UpdatedBrand", null);
		Device existingDevice = new Device();
		existingDevice.setName("OldDevice");
		existingDevice.setBrand("OldBrand");
		// creationTime is automatically set

		when(deviceRepository.findById(id)).thenReturn(Optional.of(existingDevice));
		when(deviceRepository.save(any(Device.class))).thenAnswer(invocation -> invocation.getArgument(0));

		DeviceDTO updatedDeviceDTO = deviceService.updateDevice(id, deviceDTO);
		assertNotNull(updatedDeviceDTO);
		assertEquals("UpdatedDevice", updatedDeviceDTO.getName());
		assertEquals("UpdatedBrand", updatedDeviceDTO.getBrand());
	}

	@Test
	public void testUpdateDevice_NotFound() {
		Long id = 1L;
		DeviceDTO deviceDTO = new DeviceDTO(null, "UpdatedDevice", "UpdatedBrand", null);

		when(deviceRepository.findById(id)).thenReturn(Optional.empty());

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			deviceService.updateDevice(id, deviceDTO);
		});
		assertEquals("Device not found with id: 1", thrown.getMessage());
	}

	@Test
	public void testDeleteDevice() {
		Long id = 1L;
		Device device = new Device();
		device.setName("Device1");
		device.setBrand("Brand1");
		// creationTime is automatically set

		when(deviceRepository.findById(id)).thenReturn(Optional.of(device));

		deviceService.deleteDevice(id);

		verify(deviceRepository, times(1)).deleteById(id);
	}

	@Test
	public void testDeleteDevice_NotFound() {
		Long id = 1L;

		when(deviceRepository.findById(id)).thenReturn(Optional.empty());

		RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
			deviceService.deleteDevice(id);
		});
		assertEquals("Device not found with id: 1", thrown.getMessage());
	}

	@Test
	public void testSearchDevicesByBrand() {
		String brand = "Brand1";
		Device device = new Device();
		device.setName("Device1");
		device.setBrand(brand);
		// creationTime is automatically set

		when(deviceRepository.findByBrand(brand)).thenReturn(List.of(device));

		List<DeviceDTO> devicesDTO = deviceService.searchDevicesByBrand(brand);
		assertFalse(devicesDTO.isEmpty());
		assertEquals("Device1", devicesDTO.get(0).getName());
		assertEquals(brand, devicesDTO.get(0).getBrand());
	}

	@Test
	public void searchDevicesByBrand() {
		String brand = "abc";
		List<DeviceDTO> expected = new ArrayList<>();
		List<DeviceDTO> actual = deviceService.searchDevicesByBrand(brand);

		assertEquals(expected, actual);
	}
}