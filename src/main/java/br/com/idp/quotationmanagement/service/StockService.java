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

	private List<StockDtoClient> cachedStocks = new ArrayList<>();

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

	@CacheEvict(value = "listedStocks", allEntries = true)
	public ResponseEntity<StockDto> registerStocks(StockForm stockForm, UriComponentsBuilder uriBuilder) {

		if(cachedStocks.isEmpty()) {
			populateCacheFromStockManager();
		}
		
			int comparing = compareStockToRegisterWithCached(stockForm, cachedStocks);
			System.out.println(comparing);
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
	return ResponseEntity.badRequest().build();
}

	private int compareStockToRegisterWithCached(StockForm stockForm, List<StockDtoClient> cachedStocks) {
		int comparing = 0;		
		for (StockDtoClient stockDtoClient : cachedStocks) {
			comparing = stockDtoClient.getId().compareToIgnoreCase(stockForm.getStockId());
				
			if(comparing == 0) {
					return 0;
				}	
			}
		return comparing;
		
		}

	private void populateCacheFromStockManager() {
			this.cachedStocks = webClientStockManagerAdapter.listDtoFromClient();
	}

	public void setCachedStocks(List<StockDtoClient> cachedStocks2) {
		this.cachedStocks = cachedStocks2;
	}
	
	public List<StockDtoClient> getCachedStocks() {
		return cachedStocks;
	}
	
	public void clearCache() {
		this.cachedStocks.clear();
	}

}
