package com.ahmete.socialmedia.auth.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(UserAlreadyExistsException.class)
	public ResponseEntity<ErrorResponse> handleUserAlreadyExistsException(
			UserAlreadyExistsException ex,
			HttpServletRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI(),
				null
		);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ErrorResponse> handleBadCredentialsException(
			BadCredentialsException ex,
			HttpServletRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.UNAUTHORIZED.value(),
				HttpStatus.UNAUTHORIZED.getReasonPhrase(),
				"Kullanıcı adı veya şifre hatalı",
				request.getRequestURI(),
				null
		);
		
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
			MethodArgumentNotValidException ex,
			HttpServletRequest request
	) {
		List<String> validationErrors = ex.getBindingResult()
		                                  .getAllErrors()
		                                  .stream()
		                                  .map(error -> {
			                                  if (error instanceof FieldError fieldError) {
				                                  return fieldError.getField() + ": " + fieldError.getDefaultMessage();
			                                  }
			                                  return error.getDefaultMessage();
		                                  })
		                                  .toList();
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.BAD_REQUEST.value(),
				HttpStatus.BAD_REQUEST.getReasonPhrase(),
				"Validation hatası oluştu",
				request.getRequestURI(),
				validationErrors
		);
		
		return ResponseEntity.badRequest().body(errorResponse);
	}
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGenericException(
			Exception ex,
			HttpServletRequest request
	) {
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(),
				HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
				ex.getMessage(),
				request.getRequestURI(),
				null
		);
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
	}
}