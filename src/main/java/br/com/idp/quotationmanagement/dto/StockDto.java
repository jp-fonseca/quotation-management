package br.com.idp.quotationmanagement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import br.com.idp.quotationmanagement.model.Stock;

public class StockDto {

	private String id;
	private String stockId;
	private Map<LocalDate, Double> quotes = new HashMap<>();
	
	public StockDto() {
	}
	
	public StockDto (Stock stock) {
		this.id = stock.getId();
		this.stockId = stock.getStockId();
		stock.getQuotes().forEach(q -> this.quotes.put(q.getDate(), q.getValue()));;
	}

	public String getId() {
		return id;
	}

	public String getStockId() {
		return stockId;
	}

	public Map<LocalDate, Double> getQuotes() {
		return quotes;
	}
	
	public static List<StockDto> convert(List<Stock> stocks) {
		return stocks.stream().map(StockDto::new).collect(Collectors.toList());
	}

	public static List<StockDto> convertOne(Stock stock) {
		List<StockDto> list = new ArrayList<StockDto>();
		StockDto stockDto = new StockDto(stock);
		list.add(stockDto);
		return list;
	}
	
	
}
