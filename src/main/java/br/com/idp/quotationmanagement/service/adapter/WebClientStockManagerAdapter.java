package br.com.idp.quotationmanagement.service.adapter;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.client.form.StockClientForm;
import reactor.core.publisher.Flux;

@Component
public class WebClientStockManagerAdapter {

	public List<StockDtoClient> listDtoFromClient() {
		System.out.println("ENTRANDO NO 8080 E GERANDO LISTA DE STOCKS");
		
		List<StockDtoClient> stocks = new ArrayList<StockDtoClient>();
		
		Flux<StockDtoClient> fluxStock = WebClient.create(BaseUriConstant.BASE_URI).get().uri("/stock").retrieve()
				.bodyToFlux(StockDtoClient.class);

		fluxStock.subscribe(s -> stocks.add(s));
		fluxStock.blockLast();

		return stocks;
	}

	public void createStockAtServer(StockClientForm stockClientForm) {

		StockDtoClient stock = new StockDtoClient();
		stock.setId(stockClientForm.getId());
		stock.setDescription(stockClientForm.getDescription());

		WebClient.create(BaseUriConstant.BASE_URI).post().uri("/stock").bodyValue(stock)
				.retrieve().bodyToMono(StockDtoClient.class).block();
	}
}
