package br.com.idp.quotationmanagement.dto;

import java.time.LocalDate;

import br.com.idp.quotationmanagement.model.Quote;

public class QuoteDto {
	
	private Long id;
	private Double value;
	private LocalDate date;
	
	public QuoteDto(Quote quote) {
		this.id = quote.getId();
		this.value = quote.getValue();
		this.date = quote.getDate();
	}

	public Double getValue() {
		return value;
	}

	public LocalDate getDate() {
		return date;
	}
	
	public Long getId() {
		return id;
	}
	
}
