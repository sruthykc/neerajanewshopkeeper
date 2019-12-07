package com.diviso.graeshoppe.service.impl;

import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.client.sale.api.SaleResourceApi;
import com.diviso.graeshoppe.client.sale.api.TicketLineResourceApi;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.service.SaleCommandService;

@Service
public class SaleCommandServiceImpl implements SaleCommandService{
	
	@Autowired
	private SaleResourceApi saleResourceApi;
	
	@Autowired
	TicketLineResourceApi ticketLineResourceApi;

	@Override
	public ResponseEntity<SaleDTO> createSale(SaleDTO saleDTO) {	
		saleDTO.date(Instant.now());
		return saleResourceApi.createSaleUsingPOST(saleDTO);
		
	}

	@Override
	public ResponseEntity<SaleDTO> updateSale(SaleDTO saleDTO) {
		return this.saleResourceApi.updateSaleUsingPUT(saleDTO);
	}

	@Override
	public void deleteSale(Long id) {
		ticketLineResourceApi.findAllTicketLinesBySaleIdUsingGET(id).getBody().forEach(ticket -> {
			ticketLineResourceApi.deleteTicketLineUsingDELETE(ticket.getId());
		});
		saleResourceApi.deleteSaleUsingDELETE(id);
		
	}

}
