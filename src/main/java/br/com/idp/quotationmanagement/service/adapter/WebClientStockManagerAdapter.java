package br.com.idp.quotationmanagement.service.adapter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.client.form.NotificationForm;
import br.com.idp.quotationmanagement.service.client.form.StockClientForm;
import reactor.core.publisher.Flux;

@Service
public class WebClientStockManagerAdapter {

	
	public List<StockDtoClient> listDtoFromClient() {
				
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
	
	@PostConstruct
	public void registerItselfForNotification() {
		System.out.println("O METODO DE REGISTRO NO NOTIFICATION FOI INICIALIZADO!");
		NotificationForm nf = new NotificationForm();
		nf.setHost("localhost");
		nf.setPort(8081);
		
		ResponseEntity<Void> responseEntity = WebClient.create(BaseUriConstant.BASE_URI).
		post().
		uri("/notification")
		.bodyValue(nf).retrieve().toBodilessEntity().block();
		
		HttpStatus statusCode = responseEntity.getStatusCode();
		System.out.println(statusCode);
	}
}
