package br.com.idp.quotationmanagement.service.client;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.client.form.StockClientForm;

@Component
public class WebClientStockManagerPost {


	public void createStockAtServer(StockClientForm stockClientForm) {
		
		StockDtoClient stock = new StockDtoClient();
		stock.setId(stockClientForm.getId());
		stock.setDescription(stockClientForm.getDescription());
		
		StockDtoClient monoStock = WebClient.create(BaseUriConstant.BASE_URI)
				.post()
				.uri("/stock")
				.bodyValue(stock)
				.retrieve()
				.bodyToMono(StockDtoClient.class)
				.block();
		
		System.out.println(monoStock);
		 
	}
}
