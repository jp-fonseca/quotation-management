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
import br.com.idp.quotationmanagement.service.StockQuoteService;

@RestController
@RequestMapping("/stockquotes")
public class StockQuotesController {

	@Autowired
	private StockQuoteService stockQuoteService;

	
	@GetMapping
	public ResponseEntity<List<StockDto>> list(@RequestParam(required = false) String stockId) {
		List<StockDto> listStocks = stockQuoteService.listStockQuotes(stockId);
		if (listStocks.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Quotes not found");
		} else {
				return ResponseEntity.ok(listStocks);
		}
	}

	@PostMapping
	public ResponseEntity<StockDto> register(@RequestBody @Valid StockForm stockForm, UriComponentsBuilder uriBuilder) {
		return stockQuoteService.registerStockQuotes(stockForm, uriBuilder);
	}
	
	
	
}
