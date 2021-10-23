package com.crud.reserva.exception;

public class ReservaNoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ReservaNoEncontradaException(Long reservaId) {
		super(String.format("Reserva with id %d not exist", reservaId));
	}

}
