package br.com.idp.quotationmanagement.service;

import java.net.URI;
import java.time.DateTimeException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
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
public class StockQuoteService {

	@Autowired
	private StockRepository stockRepository;

	@Autowired
	private QuoteRepository quoteRepository;

	@Autowired
	private WebClientStockManagerAdapter webClientStockManagerAdapter;

	private List<StockDtoClient> cachedStocks = new ArrayList<>();

	@Cacheable(value = "listedStocks")
	public List<StockDto> listStockQuotes(String stockId) {
		if (stockId == null) {
			List<Stock> stocks = stockRepository.findAll();
			return StockDto.convert(stocks);
		} else {
			Stock stock = stockRepository.findByStockId(stockId);
			if (stock == null) {
				return Collections.emptyList();
			} else {
				return StockDto.convertOne(stock);
			}
		}
	}

	@CacheEvict(value = "listedStocks", allEntries = true)
	public ResponseEntity<StockDto> registerStockQuotes(StockForm stockForm, UriComponentsBuilder uriBuilder) {

		try {
			if(cachedStocks.isEmpty()) {
				populateCacheFromStockManager();
			}
			
				int comparing = compareStockToRegisterWithCached(stockForm, cachedStocks);
				if (comparing == 0) {
					if (stockRepository.findByStockId(stockForm.getStockId()) == null) {
						Stock stock = stockForm.convert(stockForm, quoteRepository);
						stockRepository.save(stock);
						
						URI uri = uriBuilder.path("/stockquotes/{id}").buildAndExpand(stock.getId()).toUri();
						return ResponseEntity.created(uri).body(new StockDto(stock));
					} else {
						Stock stock = stockForm.convertExistent(stockForm, quoteRepository, stockRepository);
						stockRepository.save(stock);
						URI uri = uriBuilder.path("/stockquotes/{id}").buildAndExpand(stock.getId()).toUri();
						return ResponseEntity.created(uri).body(new StockDto(stock));
					}
				}else {
					throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Stock not registered.");
				}
		} catch (ConstraintViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		catch (DateTimeException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
		}
		
}

	public int compareStockToRegisterWithCached(StockForm stockForm, List<StockDtoClient> cachedStocks) {
		int comparing = 0;		
		for (StockDtoClient stockDtoClient : cachedStocks) {
			comparing = stockDtoClient.getId().compareToIgnoreCase(stockForm.getStockId());	
			if(comparing == 0) {
					return 0;
				}	
			}
		return comparing;
		
		}

	public void populateCacheFromStockManager() {
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
