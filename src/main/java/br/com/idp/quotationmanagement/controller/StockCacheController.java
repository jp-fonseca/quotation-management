package br.com.idp.quotationmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.idp.quotationmanagement.service.StockService;

@RestController
@RequestMapping("/stockcache")
public class StockCacheController {
	
	@Autowired
	private StockService stockservice;
	
	@DeleteMapping
	public void stockCache(){
		stockservice.clearCache();
		ResponseEntity.noContent().build();
	}
	
}
