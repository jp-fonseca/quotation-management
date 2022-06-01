package br.com.idp.quotationmanagement.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Quote {

	private LocalDate date;
	private BigDecimal value;
	
	public Quote(LocalDate date, BigDecimal value) {
		this.date = date;
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}
	
	
	
}
