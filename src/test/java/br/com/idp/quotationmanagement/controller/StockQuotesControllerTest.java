package br.com.idp.quotationmanagement.controller;

import java.util.HashMap;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.idp.quotationmanagement.controller.form.StockForm;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StockQuotesControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void mustListAllStocksWithQuotes() {
		webTestClient.get()
		.uri("/stockquotes")
		.exchange()
		.expectStatus().isOk()
		.expectBody().returnResult();
	}
	
	@Test
	void givenValidStockId_whenGetStockId_mustReturnStockQuotes() {
		String string = "petr4";
		
		webTestClient.get()
		.uri("/stockquotes?stockId=" + string)
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.consumeWith(stock ->
		Assertions.assertThat(stock.getResponseBody()).isNotNull());
	}
	
	@Test
	void givenInvalidStockId_whenGetStockId_mustReturnNotFound() {
		String string = "NotRegisteredStockId";
		
		webTestClient.get()
		.uri("/stockquotes?stockId=" + string)
		.exchange()
		.expectStatus().isNotFound();
	}
	
	@Test
	void givenUnregisteredStock_whenPost_mustReturnBadRequest() {	
		StockForm stockForm = new StockForm();
		stockForm.setStockId("UnregisteredStock");
		Map<String,String> quotes = new HashMap<String,String>();
		quotes.put("2022-12-01", "150.0");
		stockForm.setQuotes(quotes);
		webTestClient.post()
		.uri("/stockquotes")
		.body(Mono.just(stockForm),StockForm.class)
		.exchange()
		.expectStatus()
		.isBadRequest();
	}
	
	@Test
	void givenRegisteredStock_whenPost_mustReturnCreated() {	
		
		StockForm stockForm = new StockForm();
		stockForm.setStockId("petr4");
		Map<String,String> quotes = new HashMap<String,String>();
		quotes.put("2022-12-01", "150.0");
		stockForm.setQuotes(quotes);
		
		webTestClient.post()		
		.uri("/stockquotes")
		.contentType(MediaType.APPLICATION_JSON)
		.accept(MediaType.APPLICATION_JSON)
		.body(Mono.just(stockForm),StockForm.class)
		.exchange()
		.expectStatus()
		.isCreated()
		.expectHeader().contentType(MediaType.APPLICATION_JSON)
		.expectBody()
		.jsonPath("$.stockId").isNotEmpty()
		.jsonPath("$.stockId").isEqualTo("petr4")
		;
	}	
}
