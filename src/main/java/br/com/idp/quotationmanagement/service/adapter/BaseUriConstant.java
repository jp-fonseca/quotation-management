package br.com.idp.quotationmanagement.service.adapter;

import org.springframework.beans.factory.annotation.Value;

public class BaseUriConstant {

	@Value("${stock.manager.url}")
	public static String stockManagerURL;
	
	public static final String BASE_URI = stockManagerURL;
	
}
