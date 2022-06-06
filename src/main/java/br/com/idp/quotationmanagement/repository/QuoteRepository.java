package br.com.idp.quotationmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.idp.quotationmanagement.model.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Long>{
	
}
