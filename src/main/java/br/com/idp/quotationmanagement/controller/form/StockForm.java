package br.com.idp.quotationmanagement.controller.form;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import br.com.idp.quotationmanagement.model.Quote;
import br.com.idp.quotationmanagement.model.Stock;
import br.com.idp.quotationmanagement.repository.QuoteRepository;

public class StockForm {

	@NotNull(message = "StockId can't be null.")
	@NotEmpty(message = "StockId can't be empty.")
	private String stockId;

	@NotNull
	@NotEmpty
	private Map<String, String> quotes;

	public String getStockId() {
		return stockId;
	}

	public void setStockId(String stockId) {
		this.stockId = stockId;
	}

	public Map<String, String> getQuotes() {
		return quotes;
	}

	public void setQuotes(Map<String, String> quotes) {
		this.quotes = quotes;
	}

	public Stock convert(StockForm stockForm, QuoteRepository quoteRepository) {
		Stock stock = new Stock();
		stock.setStockId(stockForm.getStockId());
		List<Quote> list = convertToQuoteList(stock, quoteRepository);
		stock.setQuotes(list);
		return stock;
	}

	private List<Quote> convertToQuoteList(Stock stock, QuoteRepository quoteRepository) {
		List<Quote> quoteList = new ArrayList<>();
		
		for(Map.Entry<String, String> q : this.quotes.entrySet()) {
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			LocalDate date = LocalDate.parse(q.getKey(), dtf);
			Double value = Double.parseDouble(q.getValue());
			Quote uniqueQuote = new Quote(stock, date, value);
			quoteList.add(uniqueQuote);
			quoteRepository.save(uniqueQuote);
		}
		
		return quoteList;
	}
	
}
