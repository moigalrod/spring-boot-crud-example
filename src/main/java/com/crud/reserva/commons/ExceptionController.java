package com.crud.reserva.commons;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.crud.reserva.exception.ReservaNoEncontradaException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(value = { Exception.class })
	public ResponseEntity<Object> handleException(Exception e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(value = { IllegalArgumentException.class })
	public ResponseEntity<Object> handleException(IllegalArgumentException e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
	public ResponseEntity<Object> handleException(InvalidDataAccessApiUsageException e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { ReservaNoEncontradaException.class })
	public ResponseEntity<Object> handleException(ReservaNoEncontradaException e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { DataIntegrityViolationException.class })
	public ResponseEntity<Object> handleException(DataIntegrityViolationException e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { MethodArgumentNotValidException.class })
	public ResponseEntity<Object> handleException(MethodArgumentNotValidException e) {
		return new ResponseEntity<Object>(this.generarResponseException(e.getMessage()), HttpStatus.BAD_REQUEST);
	}

	private Map<String, Object> generarResponseException(String msg) {
		Map<String, Object> response = new LinkedHashMap<>();
		response.put("timestamp", LocalDateTime.now());
		response.put("message", msg);
		return response;
	}
}
