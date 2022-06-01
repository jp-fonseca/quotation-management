package br.com.idp.quotationmanagement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Test {

	@RequestMapping("/")
	@ResponseBody
	public String hello() {
		return "Hello World";
	}
	
}
