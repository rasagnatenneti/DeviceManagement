package com.inventory.device.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.inventory.device.dto.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

	// Handle resource not found exceptions
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ApiResponse<Object>> handleNotFoundException(RuntimeException ex, WebRequest request) {
		ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// Handle JWT authentication exceptions
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ApiResponse<Object>> handleBadCredentialsException(BadCredentialsException ex,
			WebRequest request) {
		ApiResponse<Object> response = new ApiResponse<>(false, "Invalid credentials", null);
		return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
	}

	// Handle username not found exceptions
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ApiResponse<Object>> handleUsernameNotFoundException(UsernameNotFoundException ex,
			WebRequest request) {
		ApiResponse<Object> response = new ApiResponse<>(false, ex.getMessage(), null);
		return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	}

	// Handle all other exceptions
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ApiResponse<Object>> handleGlobalException(Exception ex, WebRequest request) {
		ApiResponse<Object> response = new ApiResponse<>(false, "An error occurred", null);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
