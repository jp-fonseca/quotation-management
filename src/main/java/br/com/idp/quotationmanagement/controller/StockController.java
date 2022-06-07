package br.com.idp.quotationmanagement.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.idp.quotationmanagement.controller.form.StockForm;
import br.com.idp.quotationmanagement.dto.StockDto;
import br.com.idp.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stocks")
public class StockController {

	@Autowired
	private StockService stockService;

	
	@GetMapping
	public List<StockDto> list(@RequestParam(required = false) String stockId) {
		List<StockDto> listStocks = stockService.listStocks(stockId);
		if (listStocks.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND);
		} else {
				return listStocks;
		}
	}

	@PostMapping
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockForm stockForm, UriComponentsBuilder uriBuilder) {
		return stockService.registerStocks(stockForm, uriBuilder);
	}
	
	
	
}
