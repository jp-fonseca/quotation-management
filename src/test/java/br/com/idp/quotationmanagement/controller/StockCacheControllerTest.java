package br.com.idp.quotationmanagement.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StockCacheControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void mustDeleteCache() {
		webTestClient.delete()
		.uri("/stockcache")
		.exchange()
		.expectStatus().isOk();
	}

}
