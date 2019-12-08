package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;

public interface SaleCommandService {

	public ResponseEntity<SaleDTO> createSale(SaleDTO saleDTO);
	
	public ResponseEntity<SaleDTO> updateSale(SaleDTO saleDTO);
	
	public void deleteSale(Long id);
	
	public ResponseEntity<TicketLineDTO> createTickerLine(TicketLineDTO ticketLineDTO);
	
	public ResponseEntity<TicketLineDTO> updateTicketLine(TicketLineDTO ticketLineDTO);
	
	public void deleteTicketline(Long id);
}
