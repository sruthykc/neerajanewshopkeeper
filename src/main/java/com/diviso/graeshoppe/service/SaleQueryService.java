package com.diviso.graeshoppe.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.sale.model.Sale;
import com.diviso.graeshoppe.client.sale.model.TicketLine;
import com.diviso.graeshoppe.client.sale.model.SaleDTO;
import com.diviso.graeshoppe.client.sale.model.TicketLineDTO;

public interface SaleQueryService {

	public Page<Sale> findSales(String storeId, Pageable pageable);

	//public List<TicketLine> findAllTicketLinesBySaleId(Long saleId);
	public ResponseEntity<SaleDTO> findSaleById( Long id);
	public ResponseEntity<List<TicketLineDTO>> findAllTicketlines(Integer page, Integer size, ArrayList<String> sort) ;
	public ResponseEntity<TicketLineDTO> findOneTicketLines( Long id);

	public List<TicketLine> findAllTicketLinesBySaleId(Long saleId);

	

}
