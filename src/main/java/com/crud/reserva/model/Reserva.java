package com.crud.reserva.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

import org.springframework.lang.NonNull;

@Entity
public class Reserva {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@NotEmpty(message = "Cliente no puede ser vacio")
	@Column(name = "CLIENTE", length = 15)
	private String cliente;

	@Column(name = "NOCHES")
	private Integer numNoches;

	@NonNull
	@Column(name = "FEC_ENTRADA")
	private LocalDate entrada;

	@NonNull
	@Column(name = "FEC_SALIDA")
	private LocalDate salida;

	@Column(name = "CANCELABLE")
	private boolean cancelable;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Integer getNumNoches() {
		return numNoches;
	}

	public void setNumNoches(Integer numNoches) {
		this.numNoches = numNoches;
	}

	public LocalDate getEntrada() {
		return entrada;
	}

	public void setEntrada(LocalDate entrada) {
		this.entrada = entrada;
	}

	public LocalDate getSalida() {
		return salida;
	}

	public void setSalida(LocalDate salida) {
		this.salida = salida;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public Reserva() {
	}

	public Reserva(String cliente, Integer numNoches, LocalDate entrada, LocalDate salida,
			boolean cancelable) {
		super();
		this.cliente = cliente;
		this.numNoches = numNoches;
		this.entrada = entrada;
		this.salida = salida;
		this.cancelable = cancelable;
	}
	
	public Reserva(Long id, String cliente, Integer numNoches, LocalDate entrada, LocalDate salida,
			boolean cancelable) {
		super();
		this.id = id;
		this.cliente = cliente;
		this.numNoches = numNoches;
		this.entrada = entrada;
		this.salida = salida;
		this.cancelable = cancelable;
	}

	@Override
	public String toString() {
		return "Reserva [id=" + id + ", cliente=" + cliente + ", numNoches=" + numNoches + ", entrada=" + entrada
				+ ", salida=" + salida + ", cancelable=" + cancelable + "]";
	}

}
