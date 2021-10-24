package com.crud.reserva.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.reserva.model.Reserva;
import com.crud.reserva.repository.ReservaRepository;

@Service
@Transactional
public class ReservaServiceImpl implements ReservaService {

	@Autowired
	ReservaRepository reservaRepository;

	@Override
	public List<Reserva> getAll() {
		return (List<Reserva>) reservaRepository.findAll();
	}

	@Override
	public Reserva get(Long id) {
		Optional<Reserva> reserva = reservaRepository.findById(id);
		if (reserva.isPresent())
			return reserva.get();
		return null;
	}

	@Override
	public void delete(Long id) {
		reservaRepository.deleteById(id);
	}
	
	@Override
	public void deleteAll() {
		reservaRepository.deleteAll();
	}

	@Override
	public Reserva save(Reserva reserva) {
		return reservaRepository.save(reserva);
	}

}
