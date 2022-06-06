package br.com.idp.quotationmanagement.service.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import reactor.core.publisher.Flux;

@Component
public class WebClientStockManagerGet {


	public List<StockDtoClient> listDtoFromClient() {
		
		List<StockDtoClient> stocks = new ArrayList<StockDtoClient>();
		
		Flux<StockDtoClient> fluxStock = WebClient.create(BaseUriConstant.BASE_URI)
				.get()
				.uri("/stock")
				.retrieve()
				.bodyToFlux(StockDtoClient.class);
		
		fluxStock.subscribe(s -> stocks.add(s));
		fluxStock.blockLast();
		
		return stocks;
	}
}
