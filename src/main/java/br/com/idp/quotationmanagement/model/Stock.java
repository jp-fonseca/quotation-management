package br.com.idp.quotationmanagement.model;

import java.util.List;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Stock {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	private String stockName;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "stock")
	private List<Quote> quotes;
	
	public Long getId() {
		return id;
	}
	
	public String getStockName() {
		return stockName;
	}

	public void setStockId(String stockName) {
		this.stockName = stockName;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Stock other = (Stock) obj;
		return Objects.equals(id, other.id);
	}
	
	
	
}
