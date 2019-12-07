package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.sale.model.SaleDTO;

public interface SaleCommandService {

	public ResponseEntity<SaleDTO> createSale(SaleDTO saleDTO);
	
	public ResponseEntity<SaleDTO> updateSale(SaleDTO saleDTO);
	
	public void deleteSale(Long id);
}
