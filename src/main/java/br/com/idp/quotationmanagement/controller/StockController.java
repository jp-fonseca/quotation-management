package br.com.idp.quotationmanagement.controller;

import java.net.URI;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idp.quotationmanagement.controller.form.StockForm;
import br.com.idp.quotationmanagement.dto.StockDto;
import br.com.idp.quotationmanagement.model.Stock;
import br.com.idp.quotationmanagement.repository.QuoteRepository;
import br.com.idp.quotationmanagement.repository.StockRepository;

@RestController
public class StockController {

	@Autowired
	private StockRepository stockRepository;
	
	@Autowired
	private QuoteRepository quoteRepository;

	@GetMapping("/stocks")
	@Cacheable(value = "listedStocks")
	public List<StockDto> Stocks(String stockId){
		if(stockId == null) {
		List<Stock> stocks = stockRepository.findAll();
		return StockDto.convert(stocks);
		}else {
			List<Stock> stocks = (List<Stock>)stockRepository.findByStockId(stockId);
			return StockDto.convert(stocks);
		}
	}
	
	@PostMapping("/stocks")
	@Transactional
	@CacheEvict(value = "listedStocks", allEntries = true)
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockForm stockForm, UriComponentsBuilder uriBuilder){
		Stock stock = stockForm.convert(stockForm, quoteRepository);
		stockRepository.save(stock);
		
		URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
		return ResponseEntity.created(uri).body(new StockDto(stock));
	}
	
	

}
