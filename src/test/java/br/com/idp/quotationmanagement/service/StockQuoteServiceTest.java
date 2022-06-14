package br.com.idp.quotationmanagement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.idp.quotationmanagement.controller.StockCacheController;
import br.com.idp.quotationmanagement.controller.form.StockForm;
import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.adapter.WebClientStockManagerAdapter;

@SpringBootTest
class StockQuoteServiceTest {

	@Autowired
	private StockQuoteService stockQuoteService;
	
	@Autowired
	private WebClientStockManagerAdapter webClientStockManagerAdapter;
	
	@Test
	void mustPopulateCacheWithSotckManagerStocks() {
		stockQuoteService.populateCacheFromStockManager();
		List<StockDtoClient> stockCacheCheck = webClientStockManagerAdapter.listDtoFromClient();
		assertEquals(stockQuoteService.getCachedStocks().get(0).getId(), stockCacheCheck.get(0).getId());
	}
	
	@Test
	void mustClearCache() {
		stockQuoteService.populateCacheFromStockManager();
		stockQuoteService.clearCache();
		assertEquals(stockQuoteService.getCachedStocks(), Collections.EMPTY_LIST);
	}

	@Test
	void mustReturnZeroWichMeansTheStockExistsInCache() {
		StockForm stockForm = new StockForm();
		stockForm.setStockId("Petr4");
		Map<String,String> quotes = new HashMap<String,String>();
		quotes.put("2022-12-01", "150.0");
		stockForm.setQuotes(quotes);
		stockQuoteService.populateCacheFromStockManager();
		Integer zero = 0;
		assertEquals(stockQuoteService.compareStockToRegisterWithCached(stockForm, stockQuoteService.getCachedStocks()), zero);
	}
	
	@Test
	void mustNotReturnZeroWichMeansTheStockDoesntExistsInCache() {
		StockForm stockForm = new StockForm();
		stockForm.setStockId("ThisStockDoesntExists");
		Map<String,String> quotes = new HashMap<String,String>();
		quotes.put("2022-12-01", "150.0");
		stockForm.setQuotes(quotes);
		stockQuoteService.populateCacheFromStockManager();
		Integer zero = 0;
		assertNotEquals(stockQuoteService.compareStockToRegisterWithCached(stockForm, stockQuoteService.getCachedStocks()), zero);
	}
	
}
