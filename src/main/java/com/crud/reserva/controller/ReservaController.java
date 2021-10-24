package com.crud.reserva.controller;

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
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import com.crud.reserva.exception.ReservaNoEncontradaException;
import com.crud.reserva.model.Reserva;
import com.crud.reserva.service.ReservaService;

@RestController
@RequestMapping(value = "reservas")
public class ReservaController {

	@Autowired
	ReservaService reservaService;

	@GetMapping
	public List<Reserva> getAllReservas() {
		return reservaService.getAll();
	}

	@GetMapping(value = "/{id}")
	public ResponseEntity<Reserva> getReservaById(@PathVariable("id") Long id) {
		Reserva res = reservaService.get(id);
		if (res == null) {
			 throw new ReservaNoEncontradaException(id);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Reserva> saveReserva(@RequestBody @Valid Reserva reserva) {
		Reserva res = reservaService.save(reserva);
		return new ResponseEntity<>(res, HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<Reserva> updateReserva(@RequestBody Reserva reserva) {
		Reserva res = reservaService.get(reserva.getId());
		if (res == null) {
			throw new ReservaNoEncontradaException(reserva.getId());
		}
		return new ResponseEntity<>(reservaService.save(reserva), HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Reserva> deleteReservaById(@PathVariable("id") Long id) {
		Reserva res = reservaService.get(id);
		if (res != null) {
			reservaService.delete(id);
		} else {
			return new ResponseEntity<>(res, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(res, HttpStatus.OK);
	}
	
	@DeleteMapping
	public ResponseEntity<String> deleteAllReservas() {
		reservaService.deleteAll();
		return new ResponseEntity<String>(HttpStatus.OK);
	}

}
