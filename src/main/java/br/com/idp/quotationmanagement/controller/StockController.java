package br.com.idp.quotationmanagement.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.idp.quotationmanagement.dto.StockDto;
import br.com.idp.quotationmanagement.model.Stock;
import br.com.idp.quotationmanagement.repository.StockRepository;

@RestController
public class StockController {

	@Autowired
	private StockRepository stockRepository;
	

	@GetMapping("/stocks")
	public List<StockDto> Stocks(String stockName){
		if(stockName == null) {
		List<Stock> stocks = stockRepository.findAll();
		return StockDto.convert(stocks);
		}else {
			List<Stock> stocks = stockRepository.findByStockName(stockName);
			return StockDto.convert(stocks);
		}
		
	
	}

}
