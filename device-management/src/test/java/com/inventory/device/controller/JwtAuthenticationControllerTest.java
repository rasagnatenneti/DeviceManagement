package com.inventory.device.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inventory.device.config.JwtTokenUtil;
import com.inventory.device.dto.JwtRequest;
import com.inventory.device.service.JwtUserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtAuthenticationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Mock
	private AuthenticationManager authenticationManager;

	@Mock
	private JwtTokenUtil jwtTokenUtil;

	@Mock
	private JwtUserDetailsService userDetailsService;

	@InjectMocks
	private JwtAuthenticationController jwtAuthenticationController;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
		this.mockMvc = MockMvcBuilders.standaloneSetup(jwtAuthenticationController).build();
	}

	@Test
	public void testCreateAuthenticationToken_Success() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("password");

		UserDetails userDetails = User.withUsername("user").password("password").roles("USER").build();

		when(authenticationManager.authenticate(any())).thenReturn(null);
		when(userDetailsService.loadUserByUsername(anyString())).thenReturn(userDetails);
		when(jwtTokenUtil.generateToken(userDetails)).thenReturn("test-token");

		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jwtRequest))).andExpect(status().isOk())
				.andExpect(jsonPath("$.token").value("test-token"));
	}

	@Test
	public void testCreateAuthenticationToken_InvalidCredentials() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("user");
		jwtRequest.setPassword("wrongpassword");

		when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("INVALID_CREDENTIALS"));

		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jwtRequest))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Unauthorized: Invalid credentials"));
	}

	@Test
	public void testCreateAuthenticationToken_UserNotFound() throws Exception {
		JwtRequest jwtRequest = new JwtRequest();
		jwtRequest.setUsername("nonexistentuser");
		jwtRequest.setPassword("password");

		when(authenticationManager.authenticate(any())).thenThrow(new BadCredentialsException("USER_NOT_FOUND"));

		mockMvc.perform(post("/authenticate").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(jwtRequest))).andExpect(status().isUnauthorized())
				.andExpect(jsonPath("$.error").value("Unauthorized: Invalid credentials")); // Expect JSON with error
																							// field

	}

}