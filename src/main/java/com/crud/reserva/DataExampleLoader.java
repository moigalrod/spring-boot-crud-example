package com.crud.reserva;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import com.crud.reserva.model.Reserva;
import com.crud.reserva.repository.ReservaRepository;

@Component
public class DataExampleLoader implements ApplicationRunner  {

	@Autowired
	private ReservaRepository reservaRepository;
	
	public DataExampleLoader(ReservaRepository repository) {
        this.reservaRepository = repository;
    }
	
	public void run(ApplicationArguments args) {
		System.out.println("Creando datos de ejemplo....");
		reservaRepository.save( new Reserva( "Moisés",  2, LocalDate.of(2020, 1, 8), LocalDate.of(2020, 1, 10),true));
		reservaRepository.save( new Reserva( "Manuel",  5, LocalDate.of(2021, 2, 10), LocalDate.of(2022, 2, 15),true));
		reservaRepository.save( new Reserva( "María",  3, LocalDate.of(2018, 5, 10), LocalDate.of(2018, 5, 13),false));
	}

}
