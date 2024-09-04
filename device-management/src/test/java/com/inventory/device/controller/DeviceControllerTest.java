package com.inventory.device.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.device.config.JwtTokenUtil;
import com.inventory.device.dto.DeviceDTO;
import com.inventory.device.service.DeviceService;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private DeviceService deviceService;

	@InjectMocks
	private DeviceController deviceController;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	private String token;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();

		// Setup user details and generate JWT token
		UserDetails userDetails = User.withUsername("user").password("password").roles("USER").build();
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn("test-token"); // Mock token generation
		token = jwtTokenUtil.generateToken(userDetails); // Generate token for test
	}

	@Test
	public void testAddDevice() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setId(1L);
		deviceDTO.setBrand("BrandX");

		when(deviceService.addDevice(any(DeviceDTO.class))).thenReturn(deviceDTO);

		mockMvc.perform(post("/devices").header("Authorization", "Bearer " + token) // Use JWT token
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(deviceDTO)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Device created successfully"))
				.andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data.brand").value("BrandX"));
	}

	@Test
	public void testGetDeviceById() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setId(1L);
		deviceDTO.setBrand("BrandX");

		when(deviceService.getDeviceById(anyLong())).thenReturn(deviceDTO);

		mockMvc.perform(get("/devices/1").header("Authorization", "Bearer " + token)) // Use JWT token
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Device fetched successfully"))
				.andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data.brand").value("BrandX"));
	}

	@Test
	public void testListAllDevices() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setId(1L);
		deviceDTO.setBrand("BrandX");

		List<DeviceDTO> devices = Collections.singletonList(deviceDTO);

		when(deviceService.getAllDevices()).thenReturn(devices);

		mockMvc.perform(get("/devices").header("Authorization", "Bearer " + token)) // Use JWT token
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Devices fetched successfully"))
				.andExpect(jsonPath("$.data[0].id").value(1)).andExpect(jsonPath("$.data[0].brand").value("BrandX"));
	}

	@Test
	public void testUpdateDevice() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setId(1L);
		deviceDTO.setBrand("BrandX");

		when(deviceService.updateDevice(anyLong(), any(DeviceDTO.class))).thenReturn(deviceDTO);

		mockMvc.perform(put("/devices/1").header("Authorization", "Bearer " + token) // Use JWT token
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(deviceDTO)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Device updated successfully"))
				.andExpect(jsonPath("$.data.id").value(1)).andExpect(jsonPath("$.data.brand").value("BrandX"));
	}

	@Test
	public void testDeleteDevice() throws Exception {
		mockMvc.perform(delete("/devices/1").header("Authorization", "Bearer " + token)) // Use JWT token
				.andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Device deleted successfully"));
	}

	@Test
	public void testSearchDevicesByBrand() throws Exception {
		DeviceDTO deviceDTO = new DeviceDTO();
		deviceDTO.setId(1L);
		deviceDTO.setBrand("BrandX");

		List<DeviceDTO> devices = Collections.singletonList(deviceDTO);

		when(deviceService.searchDevicesByBrand(anyString())).thenReturn(devices);

		mockMvc.perform(get("/devices/search").header("Authorization", "Bearer " + token) // Use JWT token
				.param("brand", "BrandX")).andExpect(status().isOk()).andExpect(jsonPath("$.success").value(true))
				.andExpect(jsonPath("$.message").value("Devices fetched successfully by brand"))
				.andExpect(jsonPath("$.data[0].id").value(1)).andExpect(jsonPath("$.data[0].brand").value("BrandX"));
	}
}