package br.com.idp.quotationmanagement.service.adapter;

import org.springframework.beans.factory.annotation.Value;

public class BaseUriConstant {

	@Value("${stock.manager.url}")
	private String stockManagerURL;
	
	public String BASE_URI = stockManagerURL;
	
}
