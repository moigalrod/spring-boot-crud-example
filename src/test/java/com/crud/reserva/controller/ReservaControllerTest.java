package com.crud.reserva.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.crud.reserva.model.Reserva;
import com.crud.reserva.repository.ReservaRepository;
import com.crud.reserva.service.ReservaService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(controllers = ReservaController.class)
@ActiveProfiles("test")
public class ReservaControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ReservaService reservaService;

	@MockBean
	private ReservaRepository reservaRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private List<Reserva> reservaList;

	@BeforeEach
	void before() {
		this.reservaList = new ArrayList<>();
		this.reservaList.add(new Reserva(1L, "Moises", 2, LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 10), true));
		this.reservaList.add(new Reserva(2L, "Manuel", 5, LocalDate.of(2021, 2, 10), LocalDate.of(2022, 2, 15), true));
		this.reservaList.add(new Reserva(3L, "Maria", 3, LocalDate.of(2018, 5, 10), LocalDate.of(2018, 5, 13), false));
	}

	@Test
	void getAllReservas() throws Exception {
		given(reservaService.getAll()).willReturn(reservaList);
		this.mockMvc.perform(get("/reservas")).andExpect(status().isOk())
				.andExpect(jsonPath("$.size()", is(reservaList.size())));
	}

	@Test
	void getReservaById() throws Exception {
		Long reservaId = 1L;
		Reserva reserva = reservaList.get(0);

		given(reservaService.get(reservaId)).willReturn(reserva);
		this.mockMvc.perform(get("/reservas/{id}", reservaId)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(reserva.getId().intValue())))
				.andExpect(jsonPath("$.cliente", is(reserva.getCliente())));
	}

	@Test
	void getReturn404NoExistReservaId() throws Exception {
		Long reservaId = 1L;
		String msg = "Reserva with id %d not exist";

		given(reservaService.get(reservaId)).willReturn(null);
		this.mockMvc.perform(get("/reservas/{id}", reservaId)).andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is(String.format(msg, reservaId))));
	}

	@Test
	void createReserva() throws Exception {
		LocalDate entrada = LocalDate.of(2018, 5, 10);
		LocalDate salida = LocalDate.of(2018, 5, 13);

		String entradaString = entrada.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String salidaString = salida.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		Reserva reserva = new Reserva("Manolo", 3, entrada, salida, false);

		given(reservaService.save(any(Reserva.class))).willReturn(reserva);

		this.mockMvc
				.perform(post("/reservas").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reserva)))
				.andExpect(status().isCreated()).andExpect(jsonPath("$.cliente", is(reserva.getCliente())))
				.andExpect(jsonPath("$.numNoches", is(reserva.getNumNoches())))
				.andExpect(jsonPath("$.entrada", is(entradaString))).andExpect(jsonPath("$.salida", is(salidaString)))
				.andExpect(jsonPath("$.cancelable", is(reserva.isCancelable())));
	}

	@Test
	void createReturn400EmptyCliente() throws Exception {
		LocalDate entrada = LocalDate.of(2018, 5, 10);
		LocalDate salida = LocalDate.of(2018, 5, 13);
		Reserva reserva = new Reserva("", 3, entrada, salida, false);

		this.mockMvc
				.perform(post("/reservas").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reserva)))
				.andExpect(status().isBadRequest());
	}

	@Test
	void updateReserva() throws Exception {
		Long reservaId = 1L;
		int numNoches = 100;
		Reserva reserva = reservaList.get(0);
		given(reservaService.get(reservaId)).willReturn(reserva);
		given(reservaService.save(any(Reserva.class))).willAnswer((invocation) -> invocation.getArgument(0));

		reserva.setNumNoches(numNoches);
		reserva.setEntrada(LocalDate.of(2006, 5, 10));
		reserva.setSalida(LocalDate.of(2006, 5, 10));
		reserva.setCancelable(false);
		String entradaString = reserva.getEntrada().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		String salidaString = reserva.getSalida().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

		this.mockMvc
				.perform(put("/reservas").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reserva)))
				.andExpect(status().isOk()).andExpect(jsonPath("$.cliente", is(reserva.getCliente())))
				.andExpect(jsonPath("$.numNoches", is(numNoches))).andExpect(jsonPath("$.entrada", is(entradaString)))
				.andExpect(jsonPath("$.salida", is(salidaString)))
				.andExpect(jsonPath("$.cancelable", is(reserva.isCancelable())));

	}

	@Test
	void updateReturn404NoExistReservaId() throws Exception {
		Long reservaId = 1L;
		String msg = "Reserva with id %d not exist";
		Reserva reserva = reservaList.get(0);

		given(reservaService.get(reservaId)).willReturn(null);

		this.mockMvc
				.perform(put("/reservas").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(reserva)))
				.andExpect(status().isNotFound()).andExpect(jsonPath("$.message", is(String.format(msg, reservaId))));

	}

	@Test
	void deleteReservaById() throws Exception {
		Long reservaId = 1L;
		Reserva reserva = reservaList.get(0);
		given(reservaService.get(reservaId)).willReturn(reserva);

		this.mockMvc.perform(delete("/reservas/{id}", reserva.getId())).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(reserva.getId().intValue())));

	}

	@Test
	void deleteReturn404NoExistReservaId() throws Exception {
		Long reservaId = 1L;
		given(reservaService.get(reservaId)).willReturn(null);

		this.mockMvc.perform(delete("/reservas/{id}", reservaId)).andExpect(status().isNotFound());

	}

	@Test
	void deleteAllReservas() throws Exception {
		doNothing().when(reservaService).deleteAll();

		this.mockMvc.perform(delete("/reservas")).andExpect(status().isOk());

	}

}
