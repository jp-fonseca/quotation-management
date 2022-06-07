package br.com.idp.quotationmanagement.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idp.quotationmanagement.controller.form.StockForm;
import br.com.idp.quotationmanagement.dto.StockDto;
import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.model.Stock;
import br.com.idp.quotationmanagement.repository.QuoteRepository;
import br.com.idp.quotationmanagement.repository.StockRepository;
import br.com.idp.quotationmanagement.service.adapter.WebClientStockManagerAdapter;

@Service
@Transactional
public class StockService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private WebClientStockManagerAdapter webClientStockManagerAdapter;

	
	@Cacheable(value = "listedStocks")
	public List<StockDto> listStocks(String stockId) {
		if (stockId == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
		} else {
			Stock stock = stockRepository.findByStockId(stockId);
			if (stock == null) {
				return new ArrayList<StockDto>();
			} else {
				return StockDto.convertOne(stock);
			}
		}
	}


	@CacheEvict(value = "registeredStocks", allEntries = true)
	public ResponseEntity<StockDto> registerStocks(StockForm stockForm, UriComponentsBuilder uriBuilder) {

		List<StockDtoClient> stockCheck = extracted();

		for (StockDtoClient stockDtoClient : stockCheck) {
			int comparing = stockDtoClient.getId().compareToIgnoreCase(stockForm.getStockId());
			
			if (comparing == 0) {
				if (stockRepository.findByStockId(stockForm.getStockId()) == null) {
					Stock stock = stockForm.convert(stockForm, quoteRepository);
					stockRepository.save(stock);
					
					URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
					return ResponseEntity.created(uri).body(new StockDto(stock));
				} else {
					Stock stock = stockForm.convertExistent(stockForm, quoteRepository, stockRepository);
					stockRepository.save(stock);
					URI uri = uriBuilder.path("/stocks/{id}").buildAndExpand(stock.getId()).toUri();
					return ResponseEntity.created(uri).body(new StockDto(stock));
				}
			}
		}
		//webClientStockManagerAdapter.createStockAtServer(new StockClientForm(stockForm.getStockId(), "test "+ stockForm.getStockId().subSequence(0, 4)));
		
		 return ResponseEntity.badRequest().build();
	}

	@Cacheable(value = "registeredStocks")
	private List<StockDtoClient> extracted() {
		List<StockDtoClient> stockCheck = webClientStockManagerAdapter.listDtoFromClient();
		return stockCheck;
	}

}
