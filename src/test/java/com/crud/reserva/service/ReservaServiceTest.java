package com.crud.reserva.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.crud.reserva.model.Reserva;
import com.crud.reserva.repository.ReservaRepository;

@ExtendWith(MockitoExtension.class)
public class ReservaServiceTest {

	@InjectMocks
	private ReservaServiceImpl reservaService;

	@Mock
	private ReservaRepository reservaRepository;

	@Test
	void getReservaById() {
		LocalDate entrada = LocalDate.of(2018, 5, 10);
		LocalDate salida = LocalDate.of(2018, 5, 13);
		Reserva reserva = new Reserva("Manolo", 3, entrada, salida, false);
		Long reservaId = 1L;

		given(reservaRepository.findById(reservaId)).willReturn(Optional.of(reserva));

		Reserva getreserva = reservaService.get(reservaId);

		assertThat(getreserva).isNotNull();

	}

	@Test
	void saveReserva() {
		LocalDate entrada = LocalDate.of(2018, 5, 10);
		LocalDate salida = LocalDate.of(2018, 5, 13);
		Reserva reserva = new Reserva("Manolo", 3, entrada, salida, false);

		given(reservaRepository.save(reserva)).willAnswer(invocation -> invocation.getArgument(0));

		Reserva reservasave = reservaService.save(reserva);

		assertThat(reservasave).isNotNull();
		verify(reservaRepository).save(any(Reserva.class));

	}

	@Test
	void deleteReserva() {
		Long reservaId = 1L;

		reservaService.delete(reservaId);
		verify(reservaRepository).deleteById(reservaId);
	}

	@Test
	void updateReserva() {
		LocalDate entrada = LocalDate.of(2018, 5, 10);
		LocalDate salida = LocalDate.of(2018, 5, 13);
		Reserva reserva = new Reserva("Manolo", 3, entrada, salida, false);

		given(reservaRepository.save(reserva)).willReturn(reserva);

		Reserva reservaUpdate = reservaService.save(reserva);

		assertThat(reservaUpdate).isNotNull();

		verify(reservaRepository).save(any(Reserva.class));
	}

}
