package com.diviso.graeshoppe.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.diviso.graeshoppe.client.product.model.UOMDTO;
import com.diviso.graeshoppe.client.report.model.AuxItem;
import com.diviso.graeshoppe.client.report.model.ComboItem;
import com.diviso.graeshoppe.client.report.model.OrderMaster;
import com.diviso.graeshoppe.service.dto.PdfDTO;

public interface ReportQueryService {

	
	public OrderMaster findOrderMasterByOrderId(String orderId);
	
	public Page<AuxItem> findAuxItemByOrderLineId(Long orderLineId, Pageable pageable);
	
	public Page<ComboItem> findComboItemByOrderLineId(Long orderLineId,Pageable pageable);
	
	public Page<com.diviso.graeshoppe.client.report.model.OrderLine> findOrderLineByOrderMasterId(Long orderMasterId,
			Pageable pageable);
	
	public ResponseEntity<PdfDTO> getAllCategories(String idpcode); 

	public ResponseEntity<PdfDTO> getAllProducts(String idpcode);
}
