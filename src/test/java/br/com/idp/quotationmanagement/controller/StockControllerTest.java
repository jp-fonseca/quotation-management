package br.com.idp.quotationmanagement.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.com.idp.quotationmanagement.service.client.form.StockClientForm;
import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class StockControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	public void testCreateStockAtServer() {
		
		StockClientForm stockClientForm = new StockClientForm("gogl3", "google stock test");

		webTestClient.post()
				.uri("/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(stockClientForm),StockClientForm.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.id").isEqualTo("gogl3");
	}
}
