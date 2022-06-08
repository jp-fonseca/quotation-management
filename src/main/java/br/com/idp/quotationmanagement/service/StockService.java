package br.com.idp.quotationmanagement.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.adapter.WebClientStockManagerAdapter;
import br.com.idp.quotationmanagement.service.client.form.StockClientForm;

@Service
public class StockService {

	
	@Autowired
	private WebClientStockManagerAdapter webClientStockManagerAdapter;

	public ResponseEntity<StockDtoClient> registerStock(@Valid StockClientForm stockClientForm) {
		return webClientStockManagerAdapter.createStockAtServer(stockClientForm);
	}
}
