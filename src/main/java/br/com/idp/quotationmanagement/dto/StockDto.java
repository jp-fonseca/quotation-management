package br.com.idp.quotationmanagement.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import br.com.idp.quotationmanagement.model.Stock;

public class StockDto {

	private Long id;
	private String stockName;
	private List<QuoteDto> quotes;
	
	public StockDto (Stock stock) {
		this.id = stock.getId();
		this.stockName = stock.getStockName();
		this.quotes = new ArrayList<>();
		this.quotes.addAll(stock.getQuotes().stream().map(QuoteDto::new).collect(Collectors.toList()));
	}

	public Long getId() {
		return id;
	}

	public String getStockName() {
		return stockName;
	}

	public List<QuoteDto> getQuotes() {
		return quotes;
	}
	
	public static List<StockDto> convert(List<Stock> stocks) {
		return stocks.stream().map(StockDto::new).collect(Collectors.toList());
	}
	
	
}
