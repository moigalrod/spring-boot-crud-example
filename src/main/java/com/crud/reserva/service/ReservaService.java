package com.crud.reserva.service;

import java.util.List;

import com.crud.reserva.model.Reserva;

public interface ReservaService {

	public List<Reserva> getAll();

	public Reserva get(Long id);

	public void delete(Long id);
	
	public void deleteAll();

	public Reserva save(Reserva reserva);

}
