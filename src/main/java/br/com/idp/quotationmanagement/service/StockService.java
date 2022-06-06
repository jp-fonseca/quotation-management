package br.com.idp.quotationmanagement.service;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idp.quotationmanagement.controller.form.StockForm;
import br.com.idp.quotationmanagement.dto.StockDto;
import br.com.idp.quotationmanagement.model.Stock;
import br.com.idp.quotationmanagement.repository.QuoteRepository;
import br.com.idp.quotationmanagement.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	public List<StockDto> listStocks(String stockId) {
		if(stockId == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
			}else {
				List<Stock> stocks = stockRepository.findByStockId(stockId);
				return StockDto.convert(stocks);
			}
	}

	public ResponseEntity<StockDto> registerStocks(StockForm stockForm, UriComponentsBuilder uriBuilder){
		Stock stock = stockForm.convert(stockForm, quoteRepository);
		stockRepository.save(stock);
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockDto(stock));
	}
	
}
