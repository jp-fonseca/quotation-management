package br.com.idp.quotationmanagement.service.client.form;

public class StockClientForm {

	private String id;
	private String description;
	
	public StockClientForm(String stockId, String description) {
		this.id = stockId;
		this.description = description;
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
	
	
}
