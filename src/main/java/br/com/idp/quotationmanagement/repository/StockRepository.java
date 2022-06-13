package br.com.idp.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.idp.quotationmanagement.model.Stock;

public interface StockRepository extends JpaRepository<Stock, String>{

	Stock findByStockId(String stockId);
	
}
