package com.diviso.graeshoppe.service;

import org.springframework.http.ResponseEntity;

import com.diviso.graeshoppe.client.product.model.ProductDTO;

public interface ProductCommandService {
	
	public ResponseEntity<ProductDTO> createProduct(ProductDTO productDTO);

	public ResponseEntity<ProductDTO> updateProduct(ProductDTO productDTO);

    public void deleteProduct(Long id);

}
