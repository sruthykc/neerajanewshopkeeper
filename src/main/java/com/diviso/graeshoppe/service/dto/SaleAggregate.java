package com.diviso.graeshoppe.service.dto;

import java.util.List;

import com.diviso.graeshoppe.client.customer.model.CustomerDTO;
import com.diviso.graeshoppe.client.sale.model.Sale;
import com.diviso.graeshoppe.client.sale.model.TicketLine;

public class SaleAggregate {
	
	private Sale sale;
	private List<TicketLine> ticketLines;
	private CustomerDTO customer;
	public Sale getSale() {
		return sale;
	}
	public void setSale(Sale sale) {
		this.sale = sale;
	}
	public List<TicketLine> getTicketLines() {
		return ticketLines;
	}
	public void setTicketLines(List<TicketLine> ticketLines) {
		this.ticketLines = ticketLines;
	}
	public CustomerDTO getCustomer() {
		return customer;
	}
	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

}
