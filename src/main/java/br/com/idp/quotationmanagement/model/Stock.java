package br.com.idp.quotationmanagement.model;

import java.util.List;

public class Stock {

	private Long id;
	private String stockId;
	private List<Quote> quotes;
	
	public Stock(String stockId, List<Quote> quotes) {
		this.stockId = stockId;
		this.quotes = quotes;
	}

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}
	
	
	
}
