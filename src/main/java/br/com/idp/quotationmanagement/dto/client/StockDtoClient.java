package br.com.idp.quotationmanagement.dto.client;

import br.com.idp.quotationmanagement.service.client.form.StockClientForm;

public class StockDtoClient {

	private String id;
	private String description;
	
	public StockDtoClient() {
		
	}
	
	public StockDtoClient(StockClientForm stockClientForm) {
		this.id = stockClientForm.getId();
		this.description = stockClientForm.getDescription();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "StockDtoClient [id=" + id + ", description=" + description + "]";
	}

	
}
