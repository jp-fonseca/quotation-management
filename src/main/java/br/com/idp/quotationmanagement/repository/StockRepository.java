package br.com.idp.quotationmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.idp.quotationmanagement.model.Stock;

public interface StockRepository extends JpaRepository<Stock, UUID>{

	List<Stock> findByStockId(String stockId);
	
}
