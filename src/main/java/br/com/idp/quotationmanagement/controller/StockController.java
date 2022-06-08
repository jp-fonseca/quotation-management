package br.com.idp.quotationmanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idp.quotationmanagement.dto.client.StockDtoClient;
import br.com.idp.quotationmanagement.service.StockService;
import br.com.idp.quotationmanagement.service.client.form.StockClientForm;

@RestController
@RequestMapping("/stock")
public class StockController {

	@Autowired
	private StockService stockService;
	
	@PostMapping
	public ResponseEntity<StockDtoClient> register(@RequestBody @Valid StockClientForm stockClientForm, UriComponentsBuilder uriBuilder) {
		return stockService.registerStock(stockClientForm);
	}
}
